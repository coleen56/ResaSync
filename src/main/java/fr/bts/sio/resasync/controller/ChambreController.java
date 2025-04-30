package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.util.Methods; // Import de la classe utilitaire Methods
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class ChambreController {

    @FXML private Hyperlink lienDashboard; // Lien pour retourner au Dashboard
    @FXML private Hyperlink lienAjouterChambre; // Lien pour ajouter une chambre
    @FXML private Hyperlink lienGestionReservations; // Lien pour gérer les réservations

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML
    public void allerAClient() {
        chargerVue("Client.fxml");
    }

    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
    }

    @FXML
    public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    private void chargerVue(String fichierFxml) {
        try {
            // Obtenir la fenêtre actuelle (Stage) via l'un des liens (par exemple, lienDashboard)
            Stage stage = (Stage) lienDashboard.getScene().getWindow();

            // Utiliser la méthode utilitaire pour charger la nouvelle vue
            Methods.chargerVue(fichierFxml, stage); // Appel de la méthode utilitaire centralisée
        } catch (Exception e) {
            // Afficher une trace en cas d'erreur
            e.printStackTrace();
        }
    }
}