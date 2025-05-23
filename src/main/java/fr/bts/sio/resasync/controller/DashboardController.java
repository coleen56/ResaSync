package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.FacturationDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.ReservationDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ReservationDAO;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.util.Methods;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Contrôleur du tableau de bord principal.
 * Gère la navigation entre les différentes vues, la déconnexion,
 * et le calcul des statistiques de taux d'occupation.
 */
public class DashboardController {
    // DAO pour la gestion des réservations
    private ReservationDAOImpl resaDAO;
    // DAO pour la gestion de la facturation
    private FacturationDAOImpl factDAO;

    @FXML private Hyperlink lienReservations;
    @FXML private Hyperlink lienChambres;
    @FXML private Hyperlink lienClients;
    @FXML private Hyperlink lienConfiguration;

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;

    /**
     * Méthode appelée automatiquement à l'initialisation du contrôleur.
     * Initialise les DAO, active la configuration pour les admins,
     * et déclenche le calcul initial des statistiques.
     */
    @FXML
    public void initialize() {
        resaDAO = new ReservationDAOImpl();
        factDAO = new FacturationDAOImpl();

        // Active le lien de configuration uniquement pour les administrateurs
        if (Session.getInstance().isAdmin()) {
            lienConfiguration.setDisable(false);
        }

        // Calcule les statistiques de taux de remplissage à l'ouverture du dashboard
        calculerTauxRemplissage(null, null);
    }

    /**
     * Navigue vers la vue des réservations.
     */
    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
    }

    /**
     * Navigue vers la vue des chambres.
     */
    @FXML
    public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    /**
     * Navigue vers la vue des clients.
     */
    @FXML
    public void allerAClient() {
        chargerVue("Client.fxml");
    }

    /**
     * Navigue vers la vue de configuration (réservée aux admins).
     */
    @FXML
    public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    /**
     * Déconnecte l'utilisateur avec une confirmation,
     * puis retourne à la page de login.
     */
    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml"));
    }

    /**
     * Charge une vue à partir de son fichier FXML.
     * @param fichierFxml Nom du fichier FXML à charger.
     */
    private void chargerVue(String fichierFxml) {
        try {
            // Récupère la fenêtre courante (Stage) et charge la nouvelle vue
            Stage stage = (Stage) lienReservations.getScene().getWindow();
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcule et affiche les statistiques de taux de remplissage et de revenus sur une période donnée.
     * Si aucune date n'est fournie, la période par défaut est le dernier mois.
     * @param startDate Date de début de la période (peut être null)
     * @param endDate Date de fin de la période (peut être null)
     */
    private void calculerTauxRemplissage (LocalDate startDate, LocalDate endDate) {
        if(startDate == null && endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusMonths(1);
        } else if (startDate == null) {
            startDate = LocalDate.now();
        }
        System.out.println("lancement chargement stats");
        // TODO : statistiques de taux d'occupation des chambres + revenus générés sur une période donnée
    }
}