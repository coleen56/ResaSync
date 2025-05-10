package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ClientDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.dao.implementations.EntrepriseDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.dao.implementations.AdresseFacturationDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.AdresseFacturationDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.util.Methods;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientController {

    // Changer de page

    @FXML
    private Hyperlink lienDashboard;

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML
    public void allerAChambre() {
        chargerVue("Chambre.fxml");
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

    //


    private ClientDAO clientDAO = new ClientDAOImpl();
    private EntrepriseDAO entrepriseDAO = new EntrepriseDAOImpl();
    private AdresseFacturationDAO adresseDAO = new AdresseFacturationDAOImpl();

    // Tableau des clients
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, String> colNomClientParticulier;
    @FXML private TableColumn<Client, String> colPrenomClientParticulier;
    @FXML private TableColumn<Client, String> colTelClientParticulier;
    @FXML private TableColumn<Client, String> colDateNaissClientParticulier;

    // Informations personnelles Client --> GridPane
    @FXML private Label labelNomClientParticulierDetail ;
    @FXML private Label labelPrenomClientParticulierDetail ;
    @FXML private Label labelTelClientParticulierDetail ;
    @FXML private Label labelEmailClientParticulierDetail ;
    @FXML private Label labelAdresseClientParticulierDetail ;
    @FXML private Button btnModifierClientParticulier;
    @FXML private Button btnSupprimerClientParticulier;
    @FXML private TextField fieldNomClientParticulier;
    @FXML private TextField fieldPrenomClientParticulier;
    @FXML private TextField fieldTelClientParticulier;
    @FXML private TextField fieldEmailClientParticulier;
    @FXML private TextField fieldNumeroClientParticulier;
    @FXML private TextField fieldVoieClientParticulier;
    @FXML private TextField fieldCodePostalClientParticulier;
    @FXML private TextField fieldVilleClientParticulier;
    @FXML private TextField fieldPaysClientParticulier;
    @FXML private ComboBox comboAdresseClientParticulier;

    // Tableau des entreprises
    @FXML private TableView <Entreprise> tableEntreprises;
    @FXML private TableColumn colRaisonSocialeEntreprise;
    @FXML private TableColumn colSiretEntreprise;
    @FXML private TableColumn colTelEntreprise;
    @FXML private TableColumn colMailEntreprise;

    // Informations personnelles Client --> GridPane
    @FXML private Label labelRaisonSocialeDetail;
    @FXML private Label labelSiretDetail;
    @FXML private Label labelTelEntrepriseDetail;
    @FXML private Label labelEmailEntrepriseDetail;
    @FXML private Label labelAdresseEntrepriseDetail;
    @FXML private TextField fieldRaisonSocialeEntreprise;
    @FXML private TextField fieldSiretEntreprise;
    @FXML private TextField fieldTelEntreprise;
    @FXML private TextField fieldEmailEntreprise;
    @FXML private TextField fieldNumeroEntreprise;
    @FXML private TextField fieldVoieEntreprise;
    @FXML private TextField fieldCodePostalEntreprise;
    @FXML private TextField fieldVilleEntreprise;
    @FXML private TextField fieldPaysEntreprise;

    @FXML private ComboBox comboAdresseEntreprise;
    @FXML private Button btnModifierEntreprise;
    @FXML private Button btnSupprimerEntreprise;


    public void initialize() {
        // Initialiser les colonnes du tableau Clients Particulier
        colNomClientParticulier.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenomClientParticulier.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelClientParticulier.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colDateNaissClientParticulier.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        // Initialiser les colonnes du tableau Entreprise
        colRaisonSocialeEntreprise.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colSiretEntreprise.setCellValueFactory(new PropertyValueFactory<>("numSiret"));
        colTelEntreprise.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colMailEntreprise.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Charger les données
        afficherClientsParticuliers();
        afficherEntreprises();

        // Listener pour la sélection d'une ligne dans le tableau des clients
        tableClients.getSelectionModel().selectedItemProperty().addListener((obsClient , ancienneValeur, nouvelleValeur) -> {
            if (nouvelleValeur != null) {
                labelNomClientParticulierDetail.setText(nouvelleValeur.getNom());
                labelPrenomClientParticulierDetail.setText(nouvelleValeur.getPrenom());
                labelTelClientParticulierDetail.setText(nouvelleValeur.getTel());
                labelEmailClientParticulierDetail.setText(nouvelleValeur.getEmail());
                labelAdresseClientParticulierDetail.setText(nouvelleValeur.getAdresseFacturation().toString());

                fieldNomClientParticulier.setText(nouvelleValeur.getNom());
                fieldPrenomClientParticulier.setText(nouvelleValeur.getPrenom());
                fieldTelClientParticulier.setText(nouvelleValeur.getTel());
                fieldEmailClientParticulier.setText(nouvelleValeur.getEmail());
                fieldNumeroClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getNumero());
                fieldVoieClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getVoie());
                fieldCodePostalClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getCodePostal());
                fieldVilleClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getVille());
                fieldPaysClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getPays());
                comboAdresseClientParticulier.getSelectionModel().select(nouvelleValeur.getAdresseFacturation());

                btnModifierClientParticulier.setDisable(false);
                btnSupprimerClientParticulier.setDisable(false);
            } else {
                fieldNomClientParticulier.setDisable(true);
                fieldPrenomClientParticulier.setDisable(true);
                fieldTelClientParticulier.setDisable(true);
                fieldEmailClientParticulier.setDisable(true);
                comboAdresseClientParticulier.setDisable(true);
                btnModifierClientParticulier.setDisable(true);
                btnSupprimerClientParticulier.setDisable(true);
            }
        });

        // Listener pour la sélection d'une ligne dans le tableau des entreprises
        tableEntreprises.getSelectionModel().selectedItemProperty().addListener((obsEntreprise, ancienneValeurEntreprise, nouvelleValeurEntreprise) -> {
            if (nouvelleValeurEntreprise != null) {
                System.out.println(nouvelleValeurEntreprise);
                labelRaisonSocialeDetail.setText(nouvelleValeurEntreprise.getRaisonSociale());
                labelSiretDetail.setText(nouvelleValeurEntreprise.getNumSiret());
                labelTelEntrepriseDetail.setText(nouvelleValeurEntreprise.getTel());
                labelEmailEntrepriseDetail.setText(nouvelleValeurEntreprise.getEmail());
                labelAdresseEntrepriseDetail.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().toString());

                fieldRaisonSocialeEntreprise.setText(nouvelleValeurEntreprise.getRaisonSociale());
                fieldSiretEntreprise.setText(nouvelleValeurEntreprise.getNumSiret());
                fieldTelEntreprise.setText(nouvelleValeurEntreprise.getTel());
                fieldEmailEntreprise.setText(nouvelleValeurEntreprise.getEmail());
                fieldNumeroEntreprise.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().getNumero());
                fieldVoieEntreprise.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().getVoie());
                fieldCodePostalEntreprise.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().getCodePostal());
                fieldVilleEntreprise.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().getVille());
                fieldPaysEntreprise.setText(nouvelleValeurEntreprise.getAdresseFacturationEntreprise().getPays());
                comboAdresseEntreprise.getSelectionModel().select(nouvelleValeurEntreprise.getAdresseFacturationEntreprise());

                btnModifierEntreprise.setDisable(false);
                btnSupprimerEntreprise.setDisable(false);
            } else {
                fieldRaisonSocialeEntreprise.setDisable(true);
                fieldSiretEntreprise.setDisable(true);
                fieldTelEntreprise.setDisable(true);
                fieldEmailEntreprise.setDisable(true);
                comboAdresseEntreprise.setDisable(true);
                btnModifierEntreprise.setDisable(true);
                btnSupprimerEntreprise.setDisable(true);
            }
        });
    }
    public void afficherClientsParticuliers() {
        // Appel à l'instance de ClientDAO pour récupérer tous les clients
        ArrayList<Client> clients = (ArrayList<Client>) clientDAO.findAll();
        // Remplir la TableView avec les clients récupérés
        tableClients.setItems(FXCollections.observableArrayList(clients));

    }

    public void gererAjouterClientParticulier () {
        System.out.println("gererEnregistrerParticulier");

    }

    public void gererModifierClientParticulier () {
        System.out.println("gérer modifier Particulier");

    }

    public void gererSupprimerClientParticulier () {
        System.out.println("gérer Supprimer Particulier");
    }

    public void ouvrirDialogueNouvelleAdresse (){
        System.out.println("ouvrirDialogueNouvelleAdresse");

    }



    // Méthodes Entreprise

    public void afficherEntreprises() {
        // Appel à l'instance de ClientDAO pour récupérer tous les clients
        ArrayList<Entreprise> entreprises = entrepriseDAO.findAll();
        // Remplir la TableView avec les clients récupérés
        tableEntreprises.setItems(FXCollections.observableArrayList(entreprises));

    }

    public void gererAjouterEntreprise () {
        System.out.println("gererEnregistrerEntreprise");
    }

    public void gererModifierEntreprise () {
        System.out.println("gererModifierEntreprise");
    }

    public void gererSupprimerEntreprise () {
        System.out.println("gererSupprimerEntreprise");
    }

    public void ouvrirDialogueNouvelleAdresseEntreprise (){
        System.out.println("ouvrirDialogueNouvelleAdresseEntreprise");
    }



}