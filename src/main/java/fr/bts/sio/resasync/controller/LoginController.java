package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private Session session;

    @FXML
    private TextField loginField; // récupération des champs de saisie du formulaire de connexion + bouton

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(); // instance de utilisateurdaoimpl

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> authentifierUtilisateur()); // lancement de la fonction d'initialisation au clic du bouton de connexion
    }

    @FXML
    private Label loginError;

    private void authentifierUtilisateur() {
        String login = loginField.getText();
        String pwd = passwordField.getText();
        String text ="";

        Utilisateur utilisateur = null;
        if (login != null && !login.isEmpty()) {
            utilisateur = utilisateurDAO.findByLogin(login);
        }

        if (utilisateur != null && BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Connexion réussie. Redirection ...");
            text = "Connexion réussie. Redirection ...";
            session = new Session(login, utilisateur.getIdNiveau());
            System.out.println(session);
            // Rediriger vers l'application principale (changer de scène par exemple)
        } else if (utilisateur == null){
            System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            text = "Échec de l'authentification : l'utilisateur n'existe pas.";
            // Afficher un message d'erreur dans la vue
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Mot de passe incorrect.");
            text = "Mot de passe incorrect." ;
        }
        loginError.setText(text);
    }
}

