package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.FacturationDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.ReservationDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ReservationDAO;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.util.Methods; // Importer la classe utilitaire Methods
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DashboardController {
    private ReservationDAOImpl resaDAO;
    private FacturationDAOImpl factDAO;

    @FXML private Hyperlink lienReservations;
    @FXML private Hyperlink lienChambres;
    @FXML private Hyperlink lienClients;
    @FXML private Hyperlink lienConfiguration;

    // récupération des champs de saisie de date sur la vue
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;

    // méthode qui se lance automatiquement à l'appel du controller
    @FXML
    public void initialize() {
        resaDAO = new ReservationDAOImpl();
        factDAO = new FacturationDAOImpl();

        if (Session.getInstance().isAdmin()) {
            lienConfiguration.setDisable(false);
        }

        calculerTauxRemplissage(null, null);
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
    public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    private void chargerVue(String fichierFxml) {
        try {
            // Obtenir la fenêtre actuelle (Stage) via un des liens (par exemple, lienReservations)
            Stage stage = (Stage) lienReservations.getScene().getWindow();

            // Utiliser la méthode utilitaire pour charger la vue
            Methods.chargerVue(fichierFxml, stage); // Appel direct à la méthode utilitaire
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculerTauxRemplissage (LocalDate startDate, LocalDate endDate) {
        if(startDate == null && endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusMonths(1);
        } else if (startDate == null) {
            startDate = LocalDate.now();
        }
        System.out.println("lancement chargement stats");
        //todo : statistiques de taux d'occupation des chambres + revenus générés sur une période donnée
    }
}