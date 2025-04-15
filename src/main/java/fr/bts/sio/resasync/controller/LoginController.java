package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();

    @FXML
    private void initialize() { // lancement de la méthode d'authentification au clic sur le bouton
        loginButton.setOnAction(event -> authentifierUtilisateur());
    }

    private void authentifierUtilisateur() {
        String login = loginField.getText(); // récup de la saisie utilisateur dans les champs de saisie
        String pwd = passwordField.getText();
        String text = "";

        Utilisateur utilisateur = null;
        if (login != null && !login.isEmpty()) { // si champ login non nul : on cherche l'utilisateur correspondant en bdd
            utilisateur = utilisateurDAO.findByLogin(login);
        }

        if (utilisateur != null && BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Connexion réussie. Redirection ...");
            // Rediriger vers l'application principale (changer de scène par exemple)
        } else if (utilisateur == null){ // Afficher un message d'erreur dans la vue
            if(login == ""||pwd == "") { // au moins 1 champ vide
                System.out.println("Veuillez remplir tous les champs.");
            } else { // utilisateur non trouvé en bdd
                System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            }
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) { // mot de passe ne courrespond pas a celui stocké en bdd
            System.out.println("Mot de passe incorrect.");
        }

    }
}

