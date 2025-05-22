package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ReservationDAOImpl;
import fr.bts.sio.resasync.model.entity.Reservation;
import fr.bts.sio.resasync.util.Methods;

import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.stream.Collectors;


public class ReservationController {

    private ReservationDAOImpl reservationDAO;

    @FXML private Hyperlink lienDashboard;

    @FXML public void allerADashboard() { chargerVue("Dashboard.fxml"); }

    @FXML public void allerAClient() {
        chargerVue("Client.fxml");
    }

    @FXML public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    @FXML public void allerAConfiguration() {
        chargerVue("Configuration.fxml");
    }

    @FXML public void Deconnexion() { Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); }


    private void chargerVue(String fichierFxml) {
        try {
            Stage stage = (Stage) lienDashboard.getScene().getWindow();
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Onglets du tableau des réservations
    @FXML private TableView<Reservation> tableViewToutesReservations;
    @FXML private TableView<Reservation> tableViewReservationsEnCours;
    @FXML private TableView<Reservation> tableViewReservationsTerminees;
    @FXML private TableView<Reservation> tableViewReservationsAnnulees;
    @FXML private TableView<Reservation> tableViewReservationsConfirmees;


    // Onglet Toutes les reservations
    @FXML private TableColumn<Reservation, LocalDate> colidReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservation;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebut;
    @FXML private TableColumn<Reservation, LocalDate> colDateFin;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnes;
    @FXML private TableColumn<Reservation, Integer> colNbrChambres;
    @FXML private TableColumn<Reservation, Integer> colIdEntreprise;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatut;
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
    @FXML private TableColumn<Reservation, Integer> colIdEntrepriseEnCours;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutEnCours;
    @FXML private TableColumn<Reservation, Integer> colIdClientEnCours;
    @FXML private TableColumn<Reservation, Integer> colIdFactureEnCours;


    // Onglet reservations "Terminee"
    @FXML private TableColumn<Reservation, Integer> colidReservationTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutTerminee;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinTerminee;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesTerminee;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresTerminee;
    @FXML private TableColumn<Reservation, Integer> colIdEntrepriseTerminee;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutTerminee;
    @FXML private TableColumn<Reservation, Integer> colIdClientTerminee;
    @FXML private TableColumn<Reservation, Integer> colIdFactureTerminee;


    // Onglet reservations "Annulee"
    @FXML private TableColumn<Reservation, Integer> colidReservationAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutAnnulee;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinAnnulee;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesAnnulee;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresAnnulee;
    @FXML private TableColumn<Reservation, Integer> colIdEntrepriseAnnulee;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutAnnulee;
    @FXML private TableColumn<Reservation, Integer> colIdClientAnnulee;
    @FXML private TableColumn<Reservation, Integer> colIdFactureAnnulee;


    // Onglet reservations "Confirmée"
    @FXML private TableColumn<Reservation, Integer> colidReservationConfirmee;
    @FXML private TableColumn<Reservation, LocalDate> colDateReservationConfirmee;
    @FXML private TableColumn<Reservation, LocalDate> colDateDebutConfirmee;
    @FXML private TableColumn<Reservation, LocalDate> colDateFinConfirmee;
    @FXML private TableColumn<Reservation, Integer> colNbrPersonnesConfirmee;
    @FXML private TableColumn<Reservation, Integer> colNbrChambresConfirmee;
    @FXML private TableColumn<Reservation, Integer> colIdEntrepriseConfirmee;
    @FXML private TableColumn<Reservation, Integer> colLibelleStatutConfirmee;
    @FXML private TableColumn<Reservation, Integer> colIdClientConfirmee;
    @FXML private TableColumn<Reservation, Integer> colIdFactureConfirmee;


    // Champs du formulaire ajout d'une reservation
    @FXML private javafx.scene.control.DatePicker dateReservationPicker;
    @FXML private javafx.scene.control.DatePicker dateDebutPicker;
    @FXML private javafx.scene.control.DatePicker dateFinPicker;
    @FXML private javafx.scene.control.TextField nbrPersonnesField;
    @FXML private javafx.scene.control.TextField nbrChambresField;
    @FXML private javafx.scene.control.TextField idEntrepriseField;
    @FXML private javafx.scene.control.TextField idStatutResaField;
    @FXML private javafx.scene.control.TextField idClientField;
    @FXML private javafx.scene.control.TextField idFactureField;
    @FXML private javafx.scene.control.Label ajouterReservationErreur;


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
    @FXML private javafx.scene.control.TextField idFactureFieldModif;


    // Onglet Détails
    @FXML private Label labelDateReservationDetail;
    @FXML private Label labelDateDebutDetail;
    @FXML private Label labelDateFinDetail;
    @FXML private Label labelNbrPersonnesDetail;
    @FXML private Label labelNbrChambresDetail;
    @FXML private Label labelStatutDetail;
    @FXML private Label labelClientDetail;
    @FXML private Label labelEntrepriseDetail;
    @FXML private Label labelFactureDetail;


    // Onglet supprimer une réservation
    @FXML private Label labelConfirmationSuppression;


    //-----------------------------------Méthodes pour charger les éléments à l'ouverture de la page--------------------
    @FXML
    public void initialize() {
        reservationDAO = new ReservationDAOImpl();

        labelConfirmationSuppression.setVisible(false);
        boutonDetailsFactuResa.disableProperty().bind(
                tableViewToutesReservations.getSelectionModel().selectedItemProperty().isNull()
        );

        configurerColonnesToutes();
        configurerColonnesEnCours();
        configurerColonnesTerminees();
        configurerColonnesAnnulees();
        configurerColonnesConfirmees();

        chargerToutesReservations();
        chargerReservationsEnCours();
        chargerReservationsTerminees();
        chargerReservationsAnnulees();
        chargerReservationsConfirmees();

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

        tableViewReservationsConfirmees.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

        tableViewReservationsConfirmees.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
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
        colIdEntreprise.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colLibelleStatut.setCellValueFactory(new PropertyValueFactory<>("libelleStatut"));
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFacture.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }


    private void configurerColonnesEnCours() {
        colIdReservationEnCours.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationEnCours.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutEnCours.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinEnCours.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesEnCours.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresEnCours.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colIdEntrepriseEnCours.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colLibelleStatutEnCours.setCellValueFactory(new PropertyValueFactory<>("idStatutResa"));
        colIdClientEnCours.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFactureEnCours.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }


    private void configurerColonnesTerminees() {
        colidReservationTerminee.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationTerminee.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutTerminee.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinTerminee.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesTerminee.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresTerminee.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colIdEntrepriseTerminee.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colLibelleStatutTerminee.setCellValueFactory(new PropertyValueFactory<>("idStatutResa"));
        colIdClientTerminee.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFactureTerminee.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }


    private void configurerColonnesAnnulees() {
        colidReservationAnnulee.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinAnnulee.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesAnnulee.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresAnnulee.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colIdEntrepriseAnnulee.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colLibelleStatutAnnulee.setCellValueFactory(new PropertyValueFactory<>("idStatutResa"));
        colIdClientAnnulee.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFactureAnnulee.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }


    private void configurerColonnesConfirmees() {
        colidReservationConfirmee.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colDateReservationConfirmee.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        colDateDebutConfirmee.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFinConfirmee.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colNbrPersonnesConfirmee.setCellValueFactory(new PropertyValueFactory<>("nbrPersonnes"));
        colNbrChambresConfirmee.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
        colIdEntrepriseConfirmee.setCellValueFactory(new PropertyValueFactory<>("idEntreprise"));
        colLibelleStatutConfirmee.setCellValueFactory(new PropertyValueFactory<>("idStatutResa"));
        colIdClientConfirmee.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdFactureConfirmee.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
    }



    //----------------------------------------Méthodes pour afficher les réservations-----------------------------------
    private void chargerToutesReservations() {
        List<Reservation> reservations = reservationDAO.findAll();
        tableViewToutesReservations.setItems(FXCollections.observableArrayList(reservations));    }


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


    private void chargerReservationsConfirmees() {
        List<Reservation> confirmees = reservationDAO.findAll().stream()
                .filter(r -> r.getIdStatutResa() == 5)
                .collect(Collectors.toList());
        tableViewReservationsConfirmees.setItems(FXCollections.observableArrayList(confirmees));
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
                    nbrPersonnesField.getText().isBlank() || nbrChambresField.getText().isBlank() ||
                    idEntrepriseField.getText().isBlank() || idStatutResaField.getText().isBlank() ||
                    idClientField.getText().isBlank() || idFactureField.getText().isBlank()) {

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
            int nbrChambres = Integer.parseInt(nbrChambresField.getText().trim());
            int idEntreprise = Integer.parseInt(idEntrepriseField.getText().trim());
            int idStatutResa = Integer.parseInt(idStatutResaField.getText().trim());
            int idClient = Integer.parseInt(idClientField.getText().trim());
            int idFacture = Integer.parseInt(idFactureField.getText().trim());

            // Création de l'objet réservation avec id fictif (0)
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
                    idFacture
            );

            // Enregistrement
            reservationDAO.save(reservation);

            // Message de succès
            ajouterReservationErreur.setText("Réservation créée avec succès !");
            ajouterReservationErreur.setVisible(true);

            // Ajout dynamique dans les vues
            tableViewToutesReservations.getItems().add(reservation);
            switch (idStatutResa) {
                case 1 -> tableViewReservationsEnCours.getItems().add(reservation);
                case 2 -> tableViewReservationsAnnulees.getItems().add(reservation);
                case 3 -> tableViewReservationsTerminees.getItems().add(reservation);
                case 5 -> tableViewReservationsConfirmees.getItems().add(reservation);
            }

            System.out.println("Réservation numéro " + reservation.getIdReservation() + " créée");

            // Réinitialisation du formulaire
            dateReservationPicker.setValue(null);
            dateDebutPicker.setValue(null);
            dateFinPicker.setValue(null);
            nbrPersonnesField.clear();
            nbrChambresField.clear();
            idEntrepriseField.clear();
            idStatutResaField.clear();
            idClientField.clear();
            idFactureField.clear();

        } catch (NumberFormatException e) {
            ajouterReservationErreur.setText("Veuillez saisir uniquement des nombres valides.");
            ajouterReservationErreur.setVisible(true);
        } catch (Exception e) {
            ajouterReservationErreur.setText("Impossible de créer cette réservation !");
            ajouterReservationErreur.setVisible(true);
            e.printStackTrace();
        }
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
            selected.setIdFacture(Integer.parseInt(idFactureFieldModif.getText().trim()));

            reservationDAO.update(selected);
            System.out.println("Réservation numéro " + selected.getIdReservation() + " modifiée");
            labelAucuneSelection.setText("Réservation modifiée avec succès !");
            labelAucuneSelection.setVisible(true);

            // rechargement de la liste
            chargerToutesReservations();
            chargerReservationsEnCours();
            chargerReservationsAnnulees();
            chargerReservationsTerminees();
            chargerReservationsConfirmees();

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
        if (tableViewReservationsConfirmees.getSelectionModel().getSelectedItem() != null)
            return tableViewReservationsConfirmees;
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
        idFactureFieldModif.setText(String.valueOf(selected.getIdFacture()));
    }



    //-----------------------------------------Méthode pour afficher les détails--------------------------------------------
    private void afficherDetailsReservation(Reservation reservation) {
        if (reservation == null) return;

        labelDateReservationDetail.setText(reservation.getDateReservation().toString());
        labelDateDebutDetail.setText(reservation.getDateDebut().toString());
        labelDateFinDetail.setText(reservation.getDateFin().toString());
        labelNbrPersonnesDetail.setText(String.valueOf(reservation.getNbrPersonnes()));
        labelNbrChambresDetail.setText(String.valueOf(reservation.getNbrChambre()));
        labelStatutDetail.setText(String.valueOf(reservation.getIdStatutResa()));
        labelClientDetail.setText(String.valueOf(reservation.getIdClient()));
        labelEntrepriseDetail.setText(String.valueOf(reservation.getIdEntreprise()));
        labelFactureDetail.setText(String.valueOf(reservation.getIdFacture()));
    }


    //-----------------------------------------Méthode supprimer une réservation--------------------------------------------
    @FXML
    private void supprimerReservation() {
        Reservation selected = tableViewToutesReservations.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsEnCours.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsTerminees.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsAnnulees.getSelectionModel().getSelectedItem();
        if (selected == null) selected = tableViewReservationsConfirmees.getSelectionModel().getSelectedItem();

        if (selected != null && selected.getIdReservation() > 0) {
            reservationDAO.delete(selected.getIdReservation());
            labelConfirmationSuppression.setText("La réservation numéro " + selected.getIdReservation() + " a été supprimée avec succès !");
            labelConfirmationSuppression.setVisible(true);

            // Mise à jour des vues
            tableViewToutesReservations.getItems().remove(selected);
            tableViewReservationsEnCours.getItems().remove(selected);
            tableViewReservationsAnnulees.getItems().remove(selected);
            tableViewReservationsTerminees.getItems().remove(selected);
            tableViewReservationsConfirmees.getItems().remove(selected);
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
}