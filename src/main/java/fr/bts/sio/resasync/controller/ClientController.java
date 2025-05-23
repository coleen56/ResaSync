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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur JavaFX pour la gestion des clients particuliers et entreprises dans l'application ResAsync.
 * Permet d'afficher, ajouter, modifier et supprimer les clients et entreprises,
 * ainsi que de gérer la navigation entre les différentes vues de l'application.
 *
 * @author Flogte
 */
public class ClientController {

    // --------- Navigation / changement de page ---------

    @FXML
    private Hyperlink lienDashboard;

    /**
     * Navigue vers la vue Dashboard.
     */
    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    /**
     * Navigue vers la vue Chambre.
     */
    @FXML
    public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    /**
     * Navigue vers la vue Réservation.
     */
    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
    }

    /**
     * Navigue vers la vue Configuration.
     */
    @FXML
    public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    /**
     * Déconnecte l'utilisateur avec confirmation.
     */
    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml"));
    }

    /**
     * Charge une nouvelle vue FXML dans la fenêtre courante.
     * @param fichierFxml Le fichier FXML à charger.
     */
    private void chargerVue(String fichierFxml) {
        try {
            Stage stage = (Stage) lienDashboard.getScene().getWindow();
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------- Attributs DAO ---------

    private ClientDAO clientDAO = new ClientDAOImpl();
    private EntrepriseDAO entrepriseDAO = new EntrepriseDAOImpl();
    private AdresseFacturationDAO adresseDAO = new AdresseFacturationDAOImpl();

    // --------- Attributs JavaFX pour les clients particuliers ---------

    // Tableau des clients particuliers
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, String> colNomClientParticulier;
    @FXML private TableColumn<Client, String> colPrenomClientParticulier;
    @FXML private TableColumn<Client, String> colTelClientParticulier;
    @FXML private TableColumn<Client, String> colDateNaissClientParticulier;

    // Détails client particulier (GridPane)
    @FXML private Label labelNomClientParticulierDetail ;
    @FXML private Label labelPrenomClientParticulierDetail ;
    @FXML private Label labelTelClientParticulierDetail ;
    @FXML private Label labelEmailClientParticulierDetail ;
    @FXML private Label labelAdresseClientParticulierDetail ;
    @FXML private Label labelDateNaissanceClientParticulierDetail;
    @FXML private Button btnModifierClientParticulier;
    @FXML private Button btnSupprimerClientParticulier;
    @FXML private TextField fieldNomClientParticulier;
    @FXML private TextField fieldPrenomClientParticulier;
    @FXML private TextField fieldTelClientParticulier;
    @FXML private TextField fieldEmailClientParticulier;
    @FXML private DatePicker datePickerDateNaissanceClientParticulier;
    @FXML private TextField fieldNumeroClientParticulier;
    @FXML private TextField fieldVoieClientParticulier;
    @FXML private TextField fieldCodePostalClientParticulier;
    @FXML private TextField fieldVilleClientParticulier;
    @FXML private TextField fieldPaysClientParticulier;

    // --------- Attributs JavaFX pour les entreprises ---------

    @FXML private TableView <Entreprise> tableEntreprises;
    @FXML private TableColumn colRaisonSocialeEntreprise;
    @FXML private TableColumn colSiretEntreprise;
    @FXML private TableColumn colTelEntreprise;
    @FXML private TableColumn colMailEntreprise;

    // Détails entreprise (GridPane)
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

    @FXML private Button btnModifierEntreprise;
    @FXML private Button btnSupprimerEntreprise;

    /**
     * Initialise le contrôleur, configure les éléments graphiques et charge les données au lancement de la vue.
     */
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

        // Listener pour la sélection d'une ligne dans le tableau des clients particuliers
        tableClients.getSelectionModel().selectedItemProperty().addListener((obsClient , ancienneValeur, nouvelleValeur) -> {
            if (nouvelleValeur != null) {
                labelNomClientParticulierDetail.setText(nouvelleValeur.getNom());
                labelPrenomClientParticulierDetail.setText(nouvelleValeur.getPrenom());
                labelTelClientParticulierDetail.setText(nouvelleValeur.getTel());
                labelEmailClientParticulierDetail.setText(nouvelleValeur.getEmail());
                labelDateNaissanceClientParticulierDetail.setText(nouvelleValeur.getDateNaissance().toString());
                labelAdresseClientParticulierDetail.setText(nouvelleValeur.getAdresseFacturation().toString());

                fieldNomClientParticulier.setText(nouvelleValeur.getNom());
                fieldPrenomClientParticulier.setText(nouvelleValeur.getPrenom());
                fieldTelClientParticulier.setText(nouvelleValeur.getTel());
                fieldEmailClientParticulier.setText(nouvelleValeur.getEmail());
                datePickerDateNaissanceClientParticulier.setValue(nouvelleValeur.getDateNaissance());
                fieldNumeroClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getNumero());
                fieldVoieClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getVoie());
                fieldCodePostalClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getCodePostal());
                fieldVilleClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getVille());
                fieldPaysClientParticulier.setText(nouvelleValeur.getAdresseFacturation().getPays());

                btnModifierClientParticulier.setDisable(false);
                btnSupprimerClientParticulier.setDisable(false);
            } else {
                btnModifierClientParticulier.setDisable(true);
                btnSupprimerClientParticulier.setDisable(true);
            }
        });

        // Listener pour la sélection d'une ligne dans le tableau des entreprises
        tableEntreprises.getSelectionModel().selectedItemProperty().addListener((obsEntreprise, ancienneValeurEntreprise, nouvelleValeurEntreprise) -> {
            if (nouvelleValeurEntreprise != null) {
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

                btnModifierEntreprise.setDisable(false);
                btnSupprimerEntreprise.setDisable(false);
            } else {
                fieldRaisonSocialeEntreprise.setDisable(true);
                fieldSiretEntreprise.setDisable(true);
                fieldTelEntreprise.setDisable(true);
                fieldEmailEntreprise.setDisable(true);

                btnModifierEntreprise.setDisable(true);
                btnSupprimerEntreprise.setDisable(true);
            }
        });
    }

    /**
     * Charge la liste des clients particuliers et l'affiche dans le tableau.
     */
    public void afficherClientsParticuliers() {
        ArrayList<Client> clients = (ArrayList<Client>) clientDAO.findAll();
        tableClients.setItems(FXCollections.observableArrayList(clients));
    }

    /**
     * Gère l'ajout d'un nouveau client particulier à partir des champs du formulaire.
     * Affiche un message d'erreur si le mail ou le téléphone existe déjà.
     */
    public void gererAjouterClientParticulier () {
        System.out.println("gererEnregistrerParticulier");
        try {
            // Récupération des valeurs du formulaire d'ajout
            String nomClient = fieldNomClientParticulier.getText();
            String prenomClient = fieldPrenomClientParticulier.getText();
            String telClient = fieldTelClientParticulier.getText();
            String emailClient = fieldEmailClientParticulier.getText();
            LocalDate dateNaissanceClient = datePickerDateNaissanceClientParticulier.getValue();
            String numAdresseFacturationClient = fieldNumeroClientParticulier.getText();
            String voieAdresseFacturationClient = fieldVoieClientParticulier.getText();
            String codePostalAdresseFacturationClient = fieldCodePostalClientParticulier.getText();
            String villeAdresseFacturationClient = fieldVilleClientParticulier.getText();
            String paysAdresseFacturationClient = fieldPaysClientParticulier.getText();

            // Vérification que le mail ainsi que le numéro de téléphone n'existent pas déjà
            boolean telExiste = clientDAO.telExiste(telClient);
            boolean mailExiste = clientDAO.mailExiste(emailClient);

            if (telExiste) {
                System.out.println("Email déjà utilisé");
                return;
            }

            if (mailExiste) {
                System.out.println("Numéro de téléphone déjà utilisé");
                return;
            }

            // Création de l'adresse du client
            AdresseFacturation adresseFacturationClient = new AdresseFacturation(0, numAdresseFacturationClient, voieAdresseFacturationClient, codePostalAdresseFacturationClient, villeAdresseFacturationClient, paysAdresseFacturationClient);
            adresseDAO.save(adresseFacturationClient);
            int idAdresseFacturation = adresseFacturationClient.getIdAdresseFacturation();
            Client nouveauClient = new Client(0, nomClient, prenomClient, telClient, emailClient, dateNaissanceClient, adresseFacturationClient);
            clientDAO.save(nouveauClient);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gère la modification d'un client particulier sélectionné dans le tableau après confirmation.
     */
    public void gererModifierClientParticulier () {
        System.out.println("gérer modifier Particulier");
        Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();

        if (clientSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucun client sélectionné");
            alert.setContentText("Veuillez sélectionner un client à modifier.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modification");
        alert.setHeaderText("Voulez-vous enregistrer la modification ?");
        alert.setContentText("Cela modifiera le client sélectionné.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Récupération des valeurs modifiées depuis les champs
                    String nouveauNom = fieldNomClientParticulier.getText();
                    String nouveauPrenom = fieldPrenomClientParticulier.getText();
                    String nouveauTel = fieldTelClientParticulier.getText();
                    String nouvelEmail = fieldEmailClientParticulier.getText();
                    LocalDate nouvelleDateNaissance = datePickerDateNaissanceClientParticulier.getValue();

                    String nouveauNumero = fieldNumeroClientParticulier.getText();
                    String nouvelleVoie = fieldVoieClientParticulier.getText();
                    String nouveauCodePostal = fieldCodePostalClientParticulier.getText();
                    String nouvelleVille = fieldVilleClientParticulier.getText();
                    String nouveauPays = fieldPaysClientParticulier.getText();

                    // Modification de l'adresse d'abord
                    AdresseFacturation adresse = clientSelectionne.getAdresseFacturation();
                    adresse.setNumero(nouveauNumero);
                    adresse.setVoie(nouvelleVoie);
                    adresse.setCodePostal(nouveauCodePostal);
                    adresse.setVille(nouvelleVille);
                    adresse.setPays(nouveauPays);

                    adresseDAO.update(adresse);

                    // Modification du client
                    clientSelectionne.setNom(nouveauNom);
                    clientSelectionne.setPrenom(nouveauPrenom);
                    clientSelectionne.setTel(nouveauTel);
                    clientSelectionne.setEmail(nouvelEmail);
                    clientSelectionne.setDateNaissance(nouvelleDateNaissance);

                    clientDAO.update(clientSelectionne);

                    afficherClientsParticuliers(); // Rafraîchir la table

                    // Réinitialiser l’interface
                    effacerChampsClient();

                    System.out.println("Client modifié avec succès !");
                } catch (Exception e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle("Erreur");
                    erreur.setHeaderText("Erreur lors de la modification");
                    erreur.setContentText("Vérifiez les champs saisis.");
                    erreur.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Efface les champs du formulaire client particulier.
     */
    private void effacerChampsClient() {
        fieldNomClientParticulier.clear();
        fieldPrenomClientParticulier.clear();
        fieldTelClientParticulier.clear();
        fieldEmailClientParticulier.clear();
        datePickerDateNaissanceClientParticulier.setValue(null);

        fieldNumeroClientParticulier.clear();
        fieldVoieClientParticulier.clear();
        fieldCodePostalClientParticulier.clear();
        fieldVilleClientParticulier.clear();
        fieldPaysClientParticulier.clear();
    }

    /**
     * Gère la suppression d'un client particulier sélectionné après confirmation.
     */
    public void gererSupprimerClientParticulier() {
        System.out.println("gérer Supprimer Particulier");
        Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();

        if (clientSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucun client sélectionné");
            alert.setContentText("Veuillez sélectionner un client à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Suppression");
        confirmation.setHeaderText("Confirmer la suppression");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Supprimer d'abord le client
                    clientDAO.delete(clientSelectionne);

                    // Supprimer ensuite son adresse si nécessaire
                    adresseDAO.delete(clientSelectionne.getAdresseFacturation());

                    afficherClientsParticuliers(); // Rafraîchir la table
                    effacerChampsClient(); // Vider les champs
                    tableClients.getSelectionModel().clearSelection(); // Nettoyer la sélection

                    System.out.println("Client supprimé avec succès !");
                } catch (Exception e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle("Erreur");
                    erreur.setHeaderText("Erreur lors de la suppression");
                    erreur.setContentText("Impossible de supprimer ce client.");
                    erreur.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }

    // --------- Méthodes Entreprise ---------

    /**
     * Charge la liste des entreprises et l'affiche dans le tableau.
     */
    public void afficherEntreprises() {
        ArrayList<Entreprise> entreprises = entrepriseDAO.findAll();
        tableEntreprises.setItems(FXCollections.observableArrayList(entreprises));
    }

    /**
     * Gère l'ajout d'une nouvelle entreprise à partir des champs du formulaire.
     * Affiche un message d'erreur si le SIRET, le téléphone ou le mail existe déjà.
     */
    public void gererAjouterEntreprise () {
        System.out.println("gererEnregistrerEntreprise");
        try {
            // Récupération des valeurs du formulaire d'ajout
            String raisonSocialeEntreprise = fieldRaisonSocialeEntreprise.getText();
            String numSiretEntreprise = fieldSiretEntreprise.getText();
            String numTelEntreprise = fieldTelEntreprise.getText();
            String emailEntreprise = fieldEmailEntreprise.getText();
            String numAdresseFacturationEntreprise = fieldNumeroEntreprise.getText();
            String voieAdresseFacturationEntreprise = fieldVoieEntreprise.getText();
            String codePostalAdresseFacturationEntreprise = fieldCodePostalEntreprise.getText();
            String villeAdresseFacturationEntreprise = fieldVilleEntreprise.getText();
            String paysAdresseFacturationEntreprise = fieldPaysEntreprise.getText();

            // Vérification que le mail, le SIRET ou le numéro de téléphone n'existent pas déjà
            boolean telExiste = entrepriseDAO.telExiste(numTelEntreprise);
            boolean numSiretExiste = entrepriseDAO.numSiretExiste(numSiretEntreprise);
            boolean mailExiste = entrepriseDAO.mailExiste(emailEntreprise);

            if (mailExiste) {
                System.out.println("Numéro de téléphone déjà utilisé");
                return;
            }

            if (numSiretExiste) {
                System.out.println("Siret déjà utilisé");
                return;
            }

            if (mailExiste) {
                System.out.println("Adresse email déjà utilisé");
                return;
            }

            // Création de l'adresse de l'entreprise
            AdresseFacturation adresseFacturationEntrprise = new AdresseFacturation(0, numAdresseFacturationEntreprise, voieAdresseFacturationEntreprise, codePostalAdresseFacturationEntreprise, villeAdresseFacturationEntreprise, paysAdresseFacturationEntreprise);
            adresseDAO.save(adresseFacturationEntrprise);
            int idAdresseFacturation = adresseFacturationEntrprise.getIdAdresseFacturation();
            Entreprise nouvelleEntreprise = new Entreprise(0, raisonSocialeEntreprise, numTelEntreprise, numSiretEntreprise, emailEntreprise, adresseFacturationEntrprise);
            entrepriseDAO.save(nouvelleEntreprise);
            afficherEntreprises();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gère la modification d'une entreprise sélectionnée dans le tableau après confirmation.
     */
    public void gererModifierEntreprise() {
        System.out.println("gérer modifier Entreprise");
        Entreprise entrepriseSelectionnee = tableEntreprises.getSelectionModel().getSelectedItem();

        if (entrepriseSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune entreprise sélectionnée");
            alert.setContentText("Veuillez sélectionner une entreprise à modifier.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modification");
        alert.setHeaderText("Voulez-vous enregistrer la modification ?");
        alert.setContentText("Cela modifiera l'entreprise sélectionnée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Récupération des valeurs modifiées depuis les champs
                    String nouvelleRaisonSociale = fieldRaisonSocialeEntreprise.getText();
                    String nouveauTel = fieldTelEntreprise.getText();
                    String nouvelEmail = fieldEmailEntreprise.getText();
                    String nouveauNumSiret = fieldSiretEntreprise.getText();

                    // Modification de l'entreprise
                    entrepriseSelectionnee.setRaisonSociale(nouvelleRaisonSociale);
                    entrepriseSelectionnee.setTel(nouveauTel);
                    entrepriseSelectionnee.setEmail(nouvelEmail);
                    entrepriseSelectionnee.setNumSiret(nouveauNumSiret);

                    entrepriseDAO.update(entrepriseSelectionnee);

                    afficherEntreprises(); // Rafraîchir la table des entreprises

                    // Réinitialiser l’interface
                    effacerChampsEntreprise();

                    System.out.println("Entreprise modifiée avec succès !");
                } catch (Exception e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle("Erreur");
                    erreur.setHeaderText("Erreur lors de la modification");
                    erreur.setContentText("Vérifiez les champs saisis.");
                    erreur.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Efface les champs du formulaire entreprise.
     */
    private void effacerChampsEntreprise() {
        fieldRaisonSocialeEntreprise.clear();
        fieldTelEntreprise.clear();
        fieldEmailEntreprise.clear();
        fieldSiretEntreprise.clear();

        fieldNumeroEntreprise.clear();
        fieldVoieEntreprise.clear();
        fieldCodePostalEntreprise.clear();
        fieldVilleEntreprise.clear();
        fieldPaysEntreprise.clear();
    }

    /**
     * Gère la suppression d'une entreprise sélectionnée après confirmation.
     */
    public void gererSupprimerEntreprise() {
        System.out.println("gererSupprimerEntreprise");
        Entreprise entrepriseSelectionnee = tableEntreprises.getSelectionModel().getSelectedItem();

        if (entrepriseSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune entreprise sélectionnée");
            alert.setContentText("Veuillez sélectionner une entreprise à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Suppression");
        confirmation.setHeaderText("Confirmer la suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer cette entreprise ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    entrepriseDAO.delete(entrepriseSelectionnee); // Suppression dans la base
                    afficherEntreprises(); // Rafraîchir la table
                    tableEntreprises.getSelectionModel().clearSelection();
                    effacerChampsEntreprise(); // Réinitialiser les champs
                    System.out.println("Entreprise supprimée avec succès !");
                } catch (Exception e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle("Erreur");
                    erreur.setHeaderText("Erreur lors de la suppression");
                    erreur.setContentText("Impossible de supprimer l'entreprise sélectionnée.");
                    erreur.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }
}