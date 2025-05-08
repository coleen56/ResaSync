package fr.bts.sio.resasync;

import fr.bts.sio.resasync.model.dao.implementations.NiveauDroitsDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.UtilisateurDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.NiveauDroitsDAO;
import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.NiveauDroits;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ResaSyncMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ResaSyncMain.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 857);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
////            NiveauDroits niveau = new NiveauDroits("administrateur");
//            Utilisateur user = new Utilisateur("admin", "admin", "ADMIN", "admin", 1);
////
//            UtilisateurDAO dao = new UtilisateurDAOImpl();
////            NiveauDroitsDAO daodroit = new NiveauDroitsDAOImpl();
//            dao.save(user);
            fr.bts.sio.resasync.config.ConfigManager.load("src/main/java/fr/bts/sio/resasync/storage/config.json");

            launch(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

