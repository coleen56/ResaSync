package fr.bts.sio.resasync.controller;

import com.lowagie.text.pdf.ColumnText;
import fr.bts.sio.resasync.model.dao.implementations.ClientDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.util.Methods;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ClientController {

    private ClientDAO clientDAO = new ClientDAOImpl();

    // Déclarez la variable TableView avec l'annotation @FXML pour la lier à l'élément dans le FXML
    @FXML
    private TableView<Client> tableClients;

    // Déclarez les autres éléments de l'interface
    @FXML
    private TextField fieldNomClientModif;
    @FXML
    private TextField fieldPrenomClientModif;
    @FXML
    private TextField fieldTelClientModif;
    @FXML
    private TextField fieldEmailClientModif;
    @FXML
    private ComboBox<Entreprise> comboEntrepriseClientModif;
    @FXML
    private ComboBox<AdresseFacturation> comboAdresseFacturationClientModif;
    @FXML
    private TableColumn<Client, String> colNomClient;
    @FXML
    private TableColumn<Client, String> colPrenomClient;
    @FXML
    private TableColumn<Client, String> colTelClient;
    @FXML
    private TableColumn<Client, String> colMailClient;
    @FXML
    private TableColumn<Client, String> colEntreprise;
    @FXML
    private TableColumn<Client, String> colAdresse;


    @FXML
    private Button btnModifierClient;
    @FXML
    private Button btnSupprimerClient;
    @FXML
    private Label labelAucuneSelection;

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

    @FXML
    public void initialize() {
        // Initialisez les ComboBox et autres éléments, puis affichez les clients
        afficherClients();
//        chargerEntreprises();
//        chargerAdresses();
    }

//    private void chargerEntreprises() {
//        List<Entreprise> entreprises = ... // via EntrepriseDAO
//        comboEntrepriseClientModif.setItems(FXCollections.observableArrayList(entreprises));
//    }
//
//    private void chargerAdresses() {
//        List<AdresseFacturation> adresses = ... // via AdresseFacturationDAO
//        comboAdresseFacturationClientModif.setItems(FXCollections.observableArrayList(adresses));
//    }
    public void afficherClients() {
        // Récupérer la liste des clients et la lier à la TableView
        List<Client> clients = clientDAO.findAll();
        tableClients.setItems(FXCollections.observableArrayList(clients));
        // Initialisation des colonnes du tableau
        colNomClient.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colPrenomClient.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        colTelClient.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colMailClient.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colEntreprise.setCellValueFactory(new PropertyValueFactory<>("Entreprise"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("AdresseFacturation"));
    }

    private Client getSelectedClient() {
        // Récupérer le client sélectionné dans la TableView
        return tableClients.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void gererAjouterClient() {
        String nom = fieldNomClientModif.getText();
        String prenom = fieldPrenomClientModif.getText();
        String tel = fieldTelClientModif.getText();
        String email = fieldEmailClientModif.getText();
        String dateNaissance = "2000-01-01"; // ou via un DatePicker
        Entreprise entreprise = comboEntrepriseClientModif.getValue();
        AdresseFacturation adresse = comboAdresseFacturationClientModif.getValue();

        if (entreprise != null && adresse != null) {
            Client nouveauClient = new Client(nom, prenom, tel, email, dateNaissance, entreprise, adresse);
            clientDAO.save(nouveauClient);
            afficherClients();
        } else {
            // afficher message erreur
        }
    }

    @FXML
    public void gererModifierClient() {
        Client selectedClient = getSelectedClient();
        if (selectedClient != null) {
            // Logique pour modifier le client sélectionné
            fieldNomClientModif.setText(selectedClient.getNom());
            fieldPrenomClientModif.setText(selectedClient.getPrenom());
            fieldTelClientModif.setText(selectedClient.getTel());
            fieldEmailClientModif.setText(selectedClient.getEmail());
            // etc.
        } else {
            labelAucuneSelection.setVisible(true);
        }
    }

    @FXML
    public void gererSupprimerClient() {
        Client selectedClient = getSelectedClient();
        if (selectedClient != null) {
            clientDAO.delete(selectedClient);
            afficherClients(); // rafraîchir la table
        } else {
            labelAucuneSelection.setVisible(true);
        }
    }
}
