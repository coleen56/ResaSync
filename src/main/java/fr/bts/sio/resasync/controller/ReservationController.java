package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ReservationDAOImpl;
import fr.bts.sio.resasync.model.entity.Reservation;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.util.Methods;


import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;


public class ReservationController {
    @FXML private Hyperlink lienDashboard; // Lien pour retourner au Dashboard

    @FXML private TableView<Reservation> reservationTableView;

    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebut;
    @FXML private TableColumn<Reservation, LocalDate> colDateFin;
    @FXML private TableColumn<Reservation, String> colNbPersonnes;
    @FXML private TableColumn<Reservation, Integer> colIdNbChambres;
    @FXML private TableColumn<Reservation, String> colStatutResa;
    @FXML private TableColumn<Reservation, String> colNomPrenomClient;
    @FXML private TableColumn<Reservation, Integer> colIdFacture;
    @FXML private TableColumn<Reservation, Integer> colIdResp;

    private ReservationDAOImpl reservationDAO;

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
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



    // Méthode pour charger une vue en utilisant la classe utilitaire
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

//-----------------------------------Méthode pour charger les éléments à l'ouverture de la page------------------------

    @FXML
    public void initialize() {
        reservationDAO = new ReservationDAOImpl();
        configurerColonnesTableView();
        chargerToutesReservations();
    }


    private void configurerColonnesTableView() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservation.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbPersonnes.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colIdNbChambres.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colStatutResa.setCellValueFactory(new PropertyValueFactory<>("statutReservation"));
        colNomPrenomClient.setCellValueFactory(new PropertyValueFactory<>("nomPrenomClient"));
        colIdFacture.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
        colIdResp.setCellValueFactory(new PropertyValueFactory<>("idResp"));
    }


    private void chargerToutesReservations() {
        List<Reservation> reservations = reservationDAO.findAll();
        reservationTableView.getItems().setAll(reservations);
    }

//---------------------------------------------------------------------------------------------------------------------


}