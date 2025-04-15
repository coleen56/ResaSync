package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.Main;
import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Utilisateur;
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

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginError;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl(); // Ou injecte-le si besoin

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            try {
                authentifierUtilisateur();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void authentifierUtilisateur() throws IOException {
        String login = loginField.getText();
        String pwd = passwordField.getText();
        //loginError.setText("");
        String logErrorInfo = "";

        Utilisateur utilisateur = null;
        if (login != null && !login.isEmpty()) {
            utilisateur = utilisateurDAO.findByLogin(login);
        }

        if (utilisateur != null && BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Connexion réussie. Redirection ...");
            logErrorInfo = "Connexion réussie. Redirection ...";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();

        } else if (utilisateur == null){
            System.out.println("Échec de l'authentification : l'utilisateur n'existe pas.");
            logErrorInfo = "Échec de l'authentification : l'utilisateur n'existe pas.";
        } else if (!BCrypt.checkpw(pwd, utilisateur.getPwd())) {
            System.out.println("Mot de passe incorrect.");
            logErrorInfo = "Mot de passe incorrect.";
        }
        loginError.setText(logErrorInfo);
    }
}

