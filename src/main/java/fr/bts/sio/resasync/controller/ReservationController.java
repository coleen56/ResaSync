package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.*;
import fr.bts.sio.resasync.model.entity.Reservation;
import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.dao.interfaces.StatutReservationDAO;
import fr.bts.sio.resasync.model.entity.*;

import fr.bts.sio.resasync.util.Methods;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;


public class ReservationController {

    private ReservationDAOImpl reservationDAO;
    private RelieDAOImpl relieDAO;

    @FXML private Hyperlink lienDashboard;

    @FXML public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML public void allerAClient() {
        chargerVue("Client.fxml");
    }

    @FXML public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    @FXML public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    @FXML public void Deconnexion() {
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

    //déclaration des arraylist contenant les chambres dispos en "globales" pour pourvoir les partager entre les méthodes
    ArrayList<Chambre> listeChambreDouble = new ArrayList<>();
    ArrayList<Chambre> listeChambreDeuxSimple = new ArrayList<>();
    ArrayList<Chambre> listeChambreSimpleSuperpose = new ArrayList<>();


    // Onglets du tableau des réservations
    @FXML private TableView<Reservation> tableViewToutesReservations;
    @FXML private TableView<Reservation> tableViewReservationsEnCours;
    @FXML private TableView<Reservation> tableViewReservationsTerminees;
    @FXML private TableView<Reservation> tableViewReservationsAnnulees;


    // Onglet Toutes les reservations
    @FXML private TableColumn<Reservation, LocalDate> colidReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebut;
    @FXML private TableColumn<Reservation, LocalDate> colDateFin;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnes;
    @FXML private TableColumn<Reservation, Integer> colNbrChambres;
    @FXML private TableColumn<Reservation, Integer> colRaisonSociale;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatut;
    @FXML private TableColumn<Reservation, Integer> colNomClient;
    @FXML private TableColumn<Reservation, Integer> colPrenomClient;
    @FXML private TableColumn<Reservation, Integer> colIdClient;
    @FXML private TableColumn<Reservation, Integer> colIdFacture;
    @FXML private Button boutonDetailsFactuResa;


    // Onglet reservations "En cours"
    @FXML private TableColumn<Reservation, Integer> colIdReservationEnCours;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationEnCours;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutEnCours;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinEnCours;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesEnCours;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresEnCours;
    @FXML private TableColumn<Reservation, Integer> colRaisonSocialeEnCours;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutEnCours;
    @FXML private TableColumn<Reservation, Integer> colNomClientEnCours;
    @FXML private TableColumn<Reservation, Integer> colPrenomClientEnCours;


    // Onglet reservations "Terminee"
    @FXML private TableColumn<Reservation, Integer> colidReservationTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinTerminee;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesTerminee;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresTerminee;
    @FXML private TableColumn<Reservation, Integer> colRaisonSocialeTerminee;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutTerminee;
    @FXML private TableColumn<Reservation, Integer> colNomClientTerminee;
    @FXML private TableColumn<Reservation, Integer> colPrenomClientTerminee;


    // Onglet reservations "Annulee"
    @FXML private TableColumn<Reservation, Integer> colidReservationAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinAnnulee;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesAnnulee;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresAnnulee;
    @FXML private TableColumn<Reservation, Integer> colRaisonSocialeAnnulee;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutAnnulee;
    @FXML private TableColumn<Reservation, Integer> colNomClientAnnulee;
    @FXML private TableColumn<Reservation, Integer> colPrenomClientAnnulee;


    // Champs du formulaire ajout d'une reservation
    @FXML private javafx.scene.control.DatePicker dateDebutPicker;
    @FXML private javafx.scene.control.DatePicker dateFinPicker;
    @FXML private javafx.scene.control.TextField nbrPersonnesField;
    @FXML private javafx.scene.control.Label ajouterReservationErreur;
    @FXML private DatePicker dateReservationPicker;
    @FXML private ComboBox<Entreprise> comboEntreprise;
    @FXML private ComboBox<Client> comboClient;
    @FXML private ComboBox<StatutReservation> comboStatutResa;
    @FXML private ComboBox<String>  comboOptionLibelle;
    @FXML private ComboBox<Integer>  comboOptionQuantite;
    @FXML private ComboBox<Integer>  comboOptionNbJours;
    @FXML private Label labelNbJoursInfo;
    @FXML
    private TableView<OptionSelectionnee> tableOptions;

    @FXML
    private TableColumn<OptionSelectionnee, String> colLibelleOption;

    @FXML
    private TableColumn<OptionSelectionnee, Integer> colQuantiteOption;

    @FXML
    private TableColumn<OptionSelectionnee, Integer> colNbJoursOption;

    @FXML
    private TableColumn<OptionSelectionnee, Void> colActionOption; // Pour un bouton "Supprimer"



    // Champs du formulaire modification d'une reservation
    @FXML private Label modifierReservationMessage;
    @FXML private javafx.scene.control.Label labelAucuneSelection;
    @FXML private javafx.scene.control.DatePicker dateReservationPickerModif;
    @FXML private javafx.scene.control.DatePicker dateDebutPickerModif;
    @FXML private javafx.scene.control.DatePicker dateFinPickerModif;
    @FXML private javafx.scene.control.TextField nbrPersonnesFieldModif;
    @FXML private javafx.scene.control.TextField nbrChambresFieldModif;
    @FXML private javafx.scene.control.TextField idEntrepriseFieldModif;
    @FXML private javafx.scene.control.TextField idStatutResaFieldModif;
    @FXML private javafx.scene.control.TextField idClientFieldModif;


    // Onglet Détails
    @FXML private Label labelDateReservationDetail;
    @FXML private Label labelDateDebutDetail;
    @FXML private Label labelDateFinDetail;
    @FXML private Label labelNbrPersonnesDetail;
    @FXML private Label labelNbrChambresDetail;
    @FXML private Label labelStatutDetail;
    @FXML private Label labelClientDetail;
    @FXML private Label labelEntrepriseDetail;


    // Onglet supprimer une réservation
    @FXML private Label labelConfirmationSuppression;

    // choicebox qui permettent de choisir le nombre de chambres pour chaque type
    @FXML private ChoiceBox<Integer> nombreDouble;
    @FXML private ChoiceBox<Integer> nombreDeuxSimples;
    @FXML private ChoiceBox<Integer> nombreSimpleSuperpose;

    //-----------------------------------Méthodes pour charger les éléments à l'ouverture de la page--------------------
    @FXML
    public void initialize() {
        // ajout d'un event listener sur les champs de saisie de date de résa pour ajouter le nombre de chambres dispo
        ChangeListener<LocalDate> dateListener = (obs, oldValue, newValue) -> {
            if (dateFinPicker.getValue() != null && dateDebutPicker.getValue() != null) {
                getChambreDispo(dateDebutPicker.getValue(), dateFinPicker.getValue());
            }
        };
        dateDebutPicker.valueProperty().addListener(dateListener);
        dateFinPicker.valueProperty().addListener(dateListener);

        reservationDAO = new ReservationDAOImpl();
        chargerLibelleOptions();
        dateDebutPicker.valueProperty().addListener((obs, oldDate, newDate) -> updateComboBoxNbJours());
        dateFinPicker.valueProperty().addListener((obs, oldDate, newDate) -> updateComboBoxNbJours());
        labelConfirmationSuppression.setVisible(false);
//        labelNbJoursInfo.setText("Veuillez sélectionner des dates de séjour.");
        comboOptionNbJours.setDisable(true);
        boutonDetailsFactuResa.disableProperty().bind(
                tableViewToutesReservations.getSelectionModel().selectedItemProperty().isNull()
        );
        colLibelleOption.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        colQuantiteOption.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colNbJoursOption.setCellValueFactory(new PropertyValueFactory<>("nbJours"));
// Colonne de bouton supprimer (à faire avec un cellFactory)
        tableOptions.setItems(optionsAjoutees);
        ajouterColonneSupprimer();
        configurerColonnesToutes();
        configurerColonnesEnCours();
        configurerColonnesTerminees();
        configurerColonnesAnnulees();

        chargerToutesReservations();
        chargerReservationsEnCours();
        chargerReservationsTerminees();
        chargerReservationsAnnulees();
        chargerEntreprises();
        chargerClients();
        chargerStatutReservation();

        // Remplir le champ date avec la date du jour
        dateReservationPicker.setValue(LocalDate.now());

        tableViewToutesReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormulaireModification(newSelection);
            }
        });

        tableViewReservationsEnCours.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormulaireModification(newSelection);
            }
        });

        tableViewReservationsTerminees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormulaireModification(newSelection);
            }
        });

        tableViewReservationsAnnulees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                remplirFormulaireModification(newSelection);
            }
        });

        tableViewToutesReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            afficherDetailsReservation(newVal);
        });

        tableViewReservationsEnCours.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            afficherDetailsReservation(newVal);
        });

        tableViewReservationsTerminees.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            afficherDetailsReservation(newVal);
        });

        tableViewReservationsAnnulees.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            afficherDetailsReservation(newVal);
        });
    }


    private void configurerColonnesToutes() {
        colidReservation.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservation.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnes.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambres.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colRaisonSociale.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colLibelleStatut.setCellValueFactory(new PropertyValueFactory<>("libelleStatut"));
        colNomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colPrenomClient.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
    }


    private void configurerColonnesEnCours() {
        colIdReservationEnCours.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationEnCours.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutEnCours.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinEnCours.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesEnCours.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresEnCours.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colRaisonSocialeEnCours.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colLibelleStatutEnCours.setCellValueFactory(new PropertyValueFactory<>("libelleStatut"));
        colNomClientEnCours.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colPrenomClientEnCours.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
    }


    private void configurerColonnesTerminees() {
        colidReservationTerminee.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationTerminee.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutTerminee.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinTerminee.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesTerminee.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresTerminee.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colRaisonSocialeTerminee.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colLibelleStatutTerminee.setCellValueFactory(new PropertyValueFactory<>("libelleStatut"));
        colNomClientTerminee.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colPrenomClientTerminee.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
    }


    private void configurerColonnesAnnulees() {
        colidReservationAnnulee.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesAnnulee.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresAnnulee.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colRaisonSocialeAnnulee.setCellValueFactory(new PropertyValueFactory<>("raisonSociale"));
        colLibelleStatutAnnulee.setCellValueFactory(new PropertyValueFactory<>("libelleStatut"));
        colNomClientAnnulee.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colPrenomClientAnnulee.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
    }



    //----------------------------------------Méthodes pour afficher les réservations-----------------------------------
    private void chargerToutesReservations() {
        List<Reservation> reservations = reservationDAO.findAll();
        tableViewToutesReservations.setItems(FXCollections.observableArrayList(reservations));
    }


    private void chargerReservationsEnCours() {
        List<Reservation> toutes = reservationDAO.findAll();
        List<Reservation> enCours = toutes.stream()
                .filter(r -> r.getIdStatutResa() == 1)
                .collect(Collectors.toList());
        tableViewReservationsEnCours.setItems(FXCollections.observableArrayList(enCours));
    }


    private void chargerReservationsTerminees() {
        List<Reservation> terminees = reservationDAO.findAll().stream()
                .filter(r -> r.getIdStatutResa() == 3)
                .collect(Collectors.toList());
        tableViewReservationsTerminees.setItems(FXCollections.observableArrayList(terminees));
    }


    private void chargerReservationsAnnulees() {
        List<Reservation> annulees = reservationDAO.findAll().stream()
                .filter(r -> r.getIdStatutResa() == 2)
                .collect(Collectors.toList());
        tableViewReservationsAnnulees.setItems(FXCollections.observableArrayList(annulees));
    }



    //----------------------------------------Méthode pour ajouter une réservation------------------------------------------
    @FXML
    private void ajouterReservation() {
        try {
            // Nettoyer le message précédent
            ajouterReservationErreur.setText("");
            ajouterReservationErreur.setVisible(false);

            // Récupération des valeurs depuis les champs
            LocalDate dateReservation = dateReservationPicker.getValue();
            LocalDate dateDebut = dateDebutPicker.getValue();
            LocalDate dateFin = dateFinPicker.getValue();

            if (dateReservation == null || dateDebut == null || dateFin == null ||
                    nbrPersonnesField.getText().isBlank() ||
                    comboEntreprise.getValue() == null || comboClient.getValue() == null) {

                ajouterReservationErreur.setText("Veuillez compléter tous les champs pour créer une réservation.");
                ajouterReservationErreur.setVisible(true);
                return;
            }

            if (dateDebut.isBefore(dateReservation) || dateFin.isBefore(dateDebut)) {
                ajouterReservationErreur.setText("Dates incohérentes.");
                ajouterReservationErreur.setVisible(true);
                return;
            }

            // Conversion des champs numériques
            int nbrPersonnes = Integer.parseInt(nbrPersonnesField.getText().trim());
            int nbrChambres = nombreDeuxSimples.getValue() + nombreDouble.getValue() + nombreSimpleSuperpose.getValue();
            int idFacture = Integer.parseInt(nbrPersonnesField.getText().trim());

            // Entreprise
            Entreprise entrepriseSelectionnee = comboEntreprise.getValue();
            int idEntreprise = entrepriseSelectionnee.getIdEntreprise();

            // Client
            Client clientSelectionne = comboClient.getValue();
            int idClient = clientSelectionne.getIdClient();

            // Statut réservation : forcer "En cours"
            StatutReservation statutEnCours = null;
            for (StatutReservation s : comboStatutResa.getItems()) {
                if ("En cours".equalsIgnoreCase(s.getLibelle())) {
                    statutEnCours = s;
                    break;
                }
            }

            if (statutEnCours == null) {
                ajouterReservationErreur.setText("Le statut 'En cours' est introuvable dans la liste.");
                ajouterReservationErreur.setVisible(true);
                return;
            }

            int idStatutResa = statutEnCours.getIdStatutResa();

            // Création de l'objet réservation
            Reservation reservation = new Reservation(
                    0,
                    dateReservation,
                    dateDebut,
                    dateFin,
                    nbrPersonnes,
                    nbrChambres,
                    idEntreprise,
                    idStatutResa,
                    idClient,
                    null
            );

            // Enregistrement de la réservation + recup de la clé auto générée
            int idReservation = reservationDAO.save(reservation);

            relieDAO = new RelieDAOImpl();
            // ajout d'une ligne en table "relie" pour lier les chambres à la réservation
            if(nombreDouble.getValue() != 0) {
                for(int i = 0; i < nombreDouble.getValue(); i++) {
                    Chambre chambre = listeChambreDouble.get(i);
                    relieDAO.save(new Relie(chambre.getIdChambre(), idReservation));
                }
            }
            //pour chaque type on insère les n premiers éléments de la liste des chambres dispos,
            // n étant le nombre choisi de chambre à associer à la réservation
            if(nombreDeuxSimples.getValue() != 0) {
                for(int i = 0; i < nombreDeuxSimples.getValue(); i++) {
                    Chambre chambre = listeChambreDeuxSimple.get(i);
                    relieDAO.save(new Relie(chambre.getIdChambre(), idReservation));
                }
            }

            if(nombreSimpleSuperpose.getValue() != 0) {
                for(int i = 0; i < nombreSimpleSuperpose.getValue(); i++) {
                    Chambre chambre = listeChambreSimpleSuperpose.get(i);
                    relieDAO.save(new Relie(chambre.getIdChambre(), idReservation));
                }
            }

            // Ajout Des options
// Ajout des options sélectionnées à la réservation
            OptionReservationDAOImpl optionDAO = new OptionReservationDAOImpl();
            ComprendDAOImpl comprendDAO = new ComprendDAOImpl();

            for (OptionSelectionnee optSel : optionsAjoutees) {
                // 1. Retrouver l'OptionReservation correspondante via le libellé
                OptionReservation option = optionDAO.findByLibelle(optSel.getLibelle());
                if (option != null) {
                    // 2. Créer le lien Comprend (idReservation, idOption, quantite)
                    Comprend comprend = new Comprend(
                            reservation.getIdReservation(),
                            option.getIdOption(),
                            optSel.getQuantite()
                            // Tu peux ajouter optSel.getNbJours() ici si tu ajoutes ce champ à Comprend
                    );
                    // 3. Enregistrer dans la base
                    comprendDAO.save(comprend);
                }
            }
            // Mise à jour des tables
            chargerToutesReservations();
            chargerToutesReservations();

            // Message de succès
            ajouterReservationErreur.setText("Réservation créée avec succès !");
            ajouterReservationErreur.setVisible(true);

            System.out.println("Réservation numéro " + reservation.getIdReservation() + " créée");

            // Réinitialisation du formulaire
            dateReservationPicker.setValue(null);
            dateDebutPicker.setValue(null);
            dateFinPicker.setValue(null);
            nbrPersonnesField.clear();
            comboEntreprise.setValue(null);
            comboClient.setValue(null);
            comboStatutResa.setValue(statutEnCours); // Réafficher "En cours"
            optionsAjoutees.clear();


        } catch (NumberFormatException e) {
            ajouterReservationErreur.setText("Veuillez saisir uniquement des nombres valides.");
            ajouterReservationErreur.setVisible(true);
        } catch (Exception e) {
            ajouterReservationErreur.setText("Impossible de créer cette réservation !");
            ajouterReservationErreur.setVisible(true);
            e.printStackTrace();
        }
    }


    private void chargerEntreprises() {
        EntrepriseDAO entrepriseDAO = new EntrepriseDAOImpl();
        List<Entreprise> entreprises = entrepriseDAO.findAll();
        comboEntreprise.setItems(FXCollections.observableArrayList(entreprises));

        // Afficher la raison sociale uniquement
        comboEntreprise.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Entreprise item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getRaisonSociale());
            }
        });


        comboEntreprise.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Entreprise item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getRaisonSociale());
            }
        });
    }


    private void chargerClients() {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<Client> clients = clientDAO.findAll();
        comboClient.getItems().addAll(clients);

        comboClient.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return client != null ? client.getNom() + " " + client.getPrenom() + " " + client.getEmail() : "";
            }

            @Override
            public Client fromString(String string) {
                return null;
            }
        });
    }


    private void chargerStatutReservation() {
        StatutReservationDAO statutDAO = new StatutReservationDAOImpl();

        // Récupérer uniquement le statut "En cours"
        List<StatutReservation> statuts = statutDAO.findAll();
        StatutReservation statutEnCours = null;
        for (StatutReservation statut : statuts) {
            if ("En cours".equalsIgnoreCase(statut.getLibelle())) {
                statutEnCours = statut;
                break;
            }
        }

        if (statutEnCours != null) {
            comboStatutResa.getItems().clear();
            comboStatutResa.getItems().add(statutEnCours);
            comboStatutResa.setValue(statutEnCours);
        }
    }

    private void chargerLibelleOptions() {
        OptionReservationDAOImpl optionsDAO = new OptionReservationDAOImpl();
        List<OptionReservation> options = optionsDAO.optionReservationsAll();

        // Extraire uniquement les libellés
        List<String> libelles = options.stream()
                .map(OptionReservation::getLibelle)
                .collect(Collectors.toList());

        comboOptionLibelle.setItems(FXCollections.observableArrayList(libelles));
    }


    private void updateComboBoxNbJours() {
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();

        comboOptionNbJours.getItems().clear();

        if (dateDebut != null && dateFin != null && !dateFin.isBefore(dateDebut)) {
            int nbJours = (int) (java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin));
            if (nbJours > 0) {
                for (int i = 1; i <= nbJours; i++) {
                    comboOptionNbJours.getItems().add(i);
                }
                comboOptionNbJours.setValue(1);
                labelNbJoursInfo.setText("");
                comboOptionNbJours.setDisable(false);
            } else {
                labelNbJoursInfo.setText("La date de fin doit être après la date de début.");
                comboOptionNbJours.setDisable(true);
            }
        } else {
            labelNbJoursInfo.setText("Veuillez sélectionner des dates de séjour.");
            comboOptionNbJours.setDisable(true);
        }
    }
    private ObservableList<OptionSelectionnee> optionsAjoutees = FXCollections.observableArrayList();

    @FXML
    private void ajouterOptionALaListe() {
        String libelle = comboOptionLibelle.getValue();
        Integer quantite = comboOptionQuantite.getValue();
        Integer nbJours = comboOptionNbJours.getValue();

        if(libelle != null && quantite != null && nbJours != null && quantite > 0) {
            optionsAjoutees.add(new OptionSelectionnee(libelle, quantite));
            // (Facultatif) Réinitialiser les champs
        }
    }

    private void ajouterColonneSupprimer() {
        colActionOption.setCellFactory(param -> new TableCell<OptionSelectionnee, Void>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(event -> {
                    OptionSelectionnee option = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(option);
                });
                btn.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-font-size: 10px;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }
    //----------------------------------------Méthodes pour modifier une réservation------------------------------------------
    @FXML
    private void modifierReservation() {
        TableView<Reservation> tableActive = getTableSelectionnee();
        Reservation selected = tableActive != null ? tableActive.getSelectionModel().getSelectedItem() : null;

        if (selected == null || selected.getIdReservation() <= 0) {
            labelAucuneSelection.setText("Veuillez sélectionner une réservation dans la liste.");
            return;
        }

        try {
            selected.setDateReservation(dateReservationPickerModif.getValue());
            selected.setDateDebut(dateDebutPickerModif.getValue());
            selected.setDateFin(dateFinPickerModif.getValue());
            selected.setNbrPersonnes(Integer.parseInt(nbrPersonnesFieldModif.getText().trim()));
            selected.setNbrChambre(Integer.parseInt(nbrChambresFieldModif.getText().trim()));
            selected.setIdEntreprise(Integer.parseInt(idEntrepriseFieldModif.getText().trim()));
            selected.setIdStatutResa(Integer.parseInt(idStatutResaFieldModif.getText().trim()));
            selected.setIdClient(Integer.parseInt(idClientFieldModif.getText().trim()));

            reservationDAO.update(selected);
            System.out.println("Réservation numéro " + selected.getIdReservation() + " modifiée");
            labelAucuneSelection.setText("Réservation modifiée avec succès !");
            labelAucuneSelection.setVisible(true);

            // rechargement de la liste
            chargerToutesReservations();
            chargerReservationsEnCours();
            chargerReservationsAnnulees();
            chargerReservationsTerminees();

        } catch (Exception e) {
            labelAucuneSelection.setText("Erreur lors de la modification.");
            e.printStackTrace();
        }
    }


    private TableView<Reservation> getTableSelectionnee() {
        if (tableViewToutesReservations.getSelectionModel().getSelectedItem() != null)
            return tableViewToutesReservations;
        if (tableViewReservationsEnCours.getSelectionModel().getSelectedItem() != null)
            return tableViewReservationsEnCours;
        if (tableViewReservationsTerminees.getSelectionModel().getSelectedItem() != null)
            return tableViewReservationsTerminees;
        if (tableViewReservationsAnnulees.getSelectionModel().getSelectedItem() != null)
            return tableViewReservationsAnnulees;

        return null;
    }


    private void remplirFormulaireModification(Reservation selected) {
        if (selected == null) return;

        // Cache le label d'erreur
        labelAucuneSelection.setVisible(false);

        // Efface le message d'erreur
        labelAucuneSelection.setText("");

        // Remplit le formulaire
        dateReservationPickerModif.setValue(selected.getDateReservation());
        dateDebutPickerModif.setValue(selected.getDateDebut());
        dateFinPickerModif.setValue(selected.getDateFin());
        nbrPersonnesFieldModif.setText(String.valueOf(selected.getNbrPersonnes()));
        nbrChambresFieldModif.setText(String.valueOf(selected.getNbrChambre()));
        idEntrepriseFieldModif.setText(String.valueOf(selected.getIdEntreprise()));
        idStatutResaFieldModif.setText(String.valueOf(selected.getIdStatutResa()));
        idClientFieldModif.setText(String.valueOf(selected.getIdClient()));

    }



    //-----------------------------------------Méthode pour afficher les détails--------------------------------------------
    private void afficherDetailsReservation(Reservation reservation) {
        if (reservation == null) return;
        reservationDAO = new ReservationDAOImpl();

        labelDateReservationDetail.setText(reservation.getDateReservation().toString());
        labelDateDebutDetail.setText(reservation.getDateDebut().toString());
        labelDateFinDetail.setText(reservation.getDateFin().toString());
        labelNbrPersonnesDetail.setText(String.valueOf(reservation.getNbrPersonnes()));
        labelNbrChambresDetail.setText(String.valueOf(reservationDAO.getNumChambresByReservation(reservation)));
        labelStatutDetail.setText(String.valueOf(reservation.getIdStatutResa()));
        labelClientDetail.setText(String.valueOf(reservation.getIdClient()));
        labelEntrepriseDetail.setText(String.valueOf(reservation.getIdEntreprise()));

    }



    //-----------------------------------------Méthode supprimer une réservation--------------------------------------------
    @FXML
    private void supprimerReservation() {
        relieDAO = new RelieDAOImpl();
        Reservation selected = tableViewToutesReservations.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsEnCours.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsTerminees.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsAnnulees.getSelectionModel().getSelectedItem();

        if (selected != null && selected.getIdReservation() > 0) {
            relieDAO.deleteAllFromIdReservation(selected.getIdReservation());
            reservationDAO.delete(selected.getIdReservation());

            labelConfirmationSuppression.setText("La réservation numéro " + selected.getIdReservation() + " a été supprimée avec succès !");
            labelConfirmationSuppression.setVisible(true);

            // Mise à jour des vues
            tableViewToutesReservations.getItems().remove(selected);
            tableViewReservationsEnCours.getItems().remove(selected);
            tableViewReservationsAnnulees.getItems().remove(selected);
            tableViewReservationsTerminees.getItems().remove(selected);
        } else {
            labelConfirmationSuppression.setText("Aucune réservation sélectionnée.");
        }
    }


    //-------------------------------------------Pour envoyer sur modale Facturation---------------------------------------
    @FXML
    private void ouvrirFacturationModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/bts/sio/resasync/FacturationModal.fxml"));
            Parent root = loader.load();

            FacturationController controller = loader.getController();
            //Récupération du Rendez-vous selectionné pour le passer à la modale facturation
            Reservation selectedReservation = tableViewToutesReservations.getSelectionModel().getSelectedItem();
            controller.initData(selectedReservation);
            Stage stage = new Stage();
            stage.setTitle("Facturation");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remplirChoiceBox(ChoiceBox<Integer> choiceBox, int valeurMax) {
        for (int i = 0; i <= valeurMax; i++) {
            choiceBox.getItems().add(i);
        }
        choiceBox.setValue(0);
    }

    public void getChambreDispo(LocalDate startDate, LocalDate endDate) {
        ChambreDAOImpl chambreDAO = new ChambreDAOImpl();
        // on récupère les chambres dispos en fonction du type et des dates
        listeChambreDouble = chambreDAO.getChambreDisponibles(startDate, endDate, 1);
        listeChambreDeuxSimple = chambreDAO.getChambreDisponibles(startDate, endDate, 2);
        listeChambreSimpleSuperpose = chambreDAO.getChambreDisponibles(startDate, endDate, 3);
        // comptage des éléments de chaque arraylist pour changer les valeurs des choicebox
        int compteurDouble = listeChambreDouble.size();
        int compteurDeuxSimple = listeChambreDeuxSimple.size();
        int compteurSimpleSuperpose = listeChambreSimpleSuperpose.size();
        //vidage des choicebox avant mise a jour ;
        nombreDouble.getItems().clear();
        nombreDeuxSimples.getItems().clear();
        nombreSimpleSuperpose.getItems().clear();

        // remplissage des choicebox
        remplirChoiceBox(nombreDouble, compteurDouble);
        remplirChoiceBox(nombreDeuxSimples, compteurDeuxSimple);
        remplirChoiceBox(nombreSimpleSuperpose, compteurSimpleSuperpose);
    }
}
