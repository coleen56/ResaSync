package fr.bts.sio.resasync.util;

import fr.bts.sio.resasync.model.entity.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class Methods {

    // Conversion java.util.Date en java.sql.Date
    public static java.sql.Date javaDateToSqlDate(Date date) {
        return (new java.sql.Date(date.getTime()));
    }

    // Conversion LocalDate en java.util.Date
    public static Date convertLocalDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Charge et remplace la vue actuelle par un nouveau fichier FXML.
     *
     * @param fichierFxml Le chemin relatif du fichier FXML (ex. "Reservation.fxml").
     * @param stage       La fenêtre (Stage) dans laquelle charger la vue.
     */
    public static void chargerVue(String fichierFxml, Stage stage) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(Methods.class.getResource("/fr/bts/sio/resasync/" + fichierFxml));
            Parent root = loader.load();

            // Mettre à jour la scène avec la nouvelle vue
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Gestion des erreurs avec un message d'alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement de la vue");
            alert.setHeaderText("Impossible de charger la vue spécifiée.");
            alert.setContentText("Fichier FXML : " + fichierFxml);
            alert.showAndWait();
            e.printStackTrace(); // Pour le débogage
        }
    }

    public static void deconnexionAvecConfirmation(Runnable chargeurVueLogin) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de déconnexion");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter.");
        alert.setContentText("Voulez-vous vraiment continuer ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Session.setLogin("");
                Session.setNiveau(0);
                chargeurVueLogin.run();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur de déconnexion");
                errorAlert.setHeaderText("Une erreur s'est produite lors de la déconnexion.");
                errorAlert.setContentText("Détails : " + e.getMessage());
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }
}