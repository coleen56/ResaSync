package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.util.Methods;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigurationController {
    @FXML
    private Hyperlink lienReservations;
    @FXML private Hyperlink lienChambres;
    @FXML private Hyperlink lienDashboard;
    @FXML private Hyperlink lienConfiguration;

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
    }

    @FXML
    public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    @FXML
    public void allerAClient() {
        chargerVue("Client.fxml");
    }

    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    private void chargerVue(String fichierFxml) {
        try {
            // Obtenir la fenêtre actuelle (Stage) via un des hyperliens
            Stage stage = (Stage) lienReservations.getScene().getWindow();

            // Utiliser la méthode utilitaire pour charger la nouvelle vue
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
            // Afficher une trace en cas d'erreur
            e.printStackTrace();
        }
    }

    @FXML
    private Button TVAvalidateButton;
    @FXML
    private Button TVAmodifButton;
    @FXML
    private TextField TVAvalue;
}
