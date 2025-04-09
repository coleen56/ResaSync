package fr.bts.sio.resasync;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        System.out.println(Class.forName("org.h2.Driver"));
        String url = "jdbc:h2:./mactondb";
        try {
            // Charger le driver H2 (optionnel avec JDBC 4+)
            Class.forName("org.h2.Driver");
//
//            // Créer la connexion
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Connexion réussie à H2 !");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}