package fr.bts.sio.resasync;

import fr.bts.sio.resasync.model.dao.implementations.ClientDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.entity.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        try {
            Client client = new Client("GERINTE", "Florian", "1234567890", "florian.gerinte@test.fr",
                    "1988-10-12");

            ClientDAO dao = new ClientDAOImpl();
            System.out.println(dao.findById(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}