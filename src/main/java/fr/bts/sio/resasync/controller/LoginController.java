package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import fr.bts.sio.resasync.util.Methods;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

/**
 * Contrôleur pour la gestion de la connexion utilisateur.
 * Gère la logique de vérification des identifiants, la création de la session,
 * l'affichage des messages d'erreur et la redirection vers la page principale.
 */
public class LoginController {

    // Stocke la session courante (optionnel ici, car session est singleton)
    private Session session;

    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label loginError;
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();

    /**
     * Méthode appelée à l'initialisation du contrôleur.
     * Associe le bouton à la méthode d'authentification.
     */
    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> authentifierUtilisateur());
    }

    /**
     * Vérifie les identifiants utilisateur, gère la session et affiche les messages.
     * Cette méthode est appelée lors du clic sur le bouton de connexion.
     */
    private void authentifierUtilisateur() {
        String login = loginField.getText();
        String pwd = passwordField.getText();
        String text = "";

        Utilisateur utilisateur = null;

        // Vérifie que le champ login n'est pas vide avant de rechercher l'utilisateur
        if (login != null && !login.isEmpty()) {
            utilisateur = utilisateurDAO.findByLogin(login);
        }

        // Si l'utilisateur existe et que le mot de passe est correct
        if (utilisateur != null && BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Connexion réussie. Redirection ...");
            text = "Connexion réussie. Redirection ...";
            // Création de la session utilisateur avec login et niveau
            Session.creerSession(login, utilisateur.getIdNiveau());
            System.out.println("Session après connexion : " + Session.getInstance());

            // Écrit la tentative de connexion réussie dans les logs
            Methods.writeLogs("connexion", Session.getInstance().getLogin(), Session.getInstance().getDateConnexion(), true, text);

            // Redirige vers la page principale de l'application
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Methods.chargerVue("Dashboard.fxml", stage);

        } else if (utilisateur == null) {
            // L'utilisateur n'existe pas
            System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            text = "Échec de l'authentification : l'utilisateur n'existe pas.";
            Methods.writeLogs("connexion", login, LocalDateTime.now(), false, text);
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            // Le mot de passe est incorrect
            System.out.println("Mot de passe incorrect.");
            text = "Mot de passe incorrect.";
            Methods.writeLogs("connexion", login, LocalDateTime.now(), false, text);
        }

        // Affiche le message d'erreur ou de succès dans la vue
        loginError.setText(text);
    }
}