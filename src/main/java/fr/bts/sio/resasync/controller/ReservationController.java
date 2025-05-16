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
    @FXML private Hyperlink lienDashboard;
    @FXML private TableView<Reservation> reservationTableView;

    @FXML private TableColumn<Reservation, Integer> colIdReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebut;
    @FXML private TableColumn<Reservation, LocalDate> colDateFin;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnes;
    @FXML private TableColumn<Reservation, Integer> colNbrChambres;
    @FXML private TableColumn<Reservation, Integer> colIdEntreprise;
    @FXML private TableColumn<Reservation, Integer> colIdStatutResa;
    @FXML private TableColumn<Reservation, Integer> colIdClient;
    @FXML private TableColumn<Reservation, Integer> colIdFacture;


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
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml"));
    }


    private void chargerVue(String fichierFxml) {
        try {
            Stage stage = (Stage) lienDashboard.getScene().getWindow();
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
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
        colIdReservation.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservation.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnes.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambres.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colIdEntreprise.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colIdStatutResa.setCellValueFactory(new PropertyValueFactory<>("idStatutResa"));
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFacture.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }


    private void chargerToutesReservations() {
        List<Reservation> reservations = reservationDAO.findAll();
        reservationTableView.getItems().setAll(reservations);
    }

//---------------------------------------------------------------------------------------------------------------------


}