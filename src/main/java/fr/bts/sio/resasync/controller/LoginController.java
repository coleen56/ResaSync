package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import fr.bts.sio.resasync.util.Methods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import fr.bts.sio.resasync.util.Methods;

import java.time.LocalDateTime;

import java.io.IOException;

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
            // creation de l'objet session avec les infos de connexion du l'utilisateur
            Session.creerSession(login, utilisateur.getIdNiveau());
            System.out.println("Session après connexion : " + Session.getInstance());

            Methods.writeLogs(Session.getInstance().getLogin(), Session.getInstance().getDateConnexion(), true, true, text); // écriture de la connexion dans le fichier de logs

            // Rediriger vers l'application principale (changer de scène par exemple)
            // Appel à la classe utilitaire pour charger la vue
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Methods.chargerVue("Dashboard.fxml", stage);


        } else if (utilisateur == null){
            System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            text = "Échec de l'authentification : l'utilisateur n'existe pas.";
            Methods.writeLogs(login, LocalDateTime.now(), true, false, text);
            // Afficher un message d'erreur dans la vue
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Mot de passe incorrect.");
            text = "Mot de passe incorrect." ;
            Methods.writeLogs(login, LocalDateTime.now(),true, false, text);
        }
        loginError.setText(text);
    }
}

