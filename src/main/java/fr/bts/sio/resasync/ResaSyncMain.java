package fr.bts.sio.resasync;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static void main(String[] args) throws Exception {
        try {
//            Utilisateur user = new Utilisateur("coleen56", "1234", "CONTE", "Coleen", 0);
//
//            UtilisateurDAO dao = new UtilisateurDAOImpl();
//            dao.save(user);
            launch(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

