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

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(); // Ou injecte-le si besoin

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> authentifierUtilisateur());
    }

    private void authentifierUtilisateur() {
        String login = loginField.getText();
        String pwd = passwordField.getText();

        Utilisateur utilisateur = null;
        if (login != null && !login.isEmpty()) {
            utilisateur = utilisateurDAO.findByLogin(login);
        }

        if (utilisateur != null && BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Connexion réussie. Redirection ...");
            // Rediriger vers l'application principale (changer de scène par exemple)
        } else if (utilisateur == null){
            if(login == ""||pwd == "") {
                System.out.println("Veuillez remplir tous les champs.");
            } else {
                System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            }
            // Afficher un message d'erreur dans la vue
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Mot de passe incorrect.");
        }

    }
}

