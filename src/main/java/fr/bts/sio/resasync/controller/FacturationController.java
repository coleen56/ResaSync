package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.*;
import fr.bts.sio.resasync.model.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import fr.bts.sio.resasync.config.ConfigManager;
import fr.bts.sio.resasync.config.ConfigManager;

public class FacturationController {

    // Labels infos réservation
    @FXML private Label lblNumeroReservation;
    @FXML private Label lblNomClient;
    @FXML private Label lblNbChambres;
    @FXML private Label lblNbPersonnes;
    @FXML private Label lblPeriodeSejour;

    // Table options
    @FXML private TableView<OptionTableau> tableOptions;
    @FXML private TableColumn<OptionTableau, String> colOptionDescription;
    @FXML private TableColumn<OptionTableau, Integer> colOptionQuantite;
    @FXML private TableColumn<OptionTableau, Double> colOptionPrixUnitaire;

    // Labels détails montant
    @FXML private Label lblMontantChambres;
    @FXML private Label lblMontantOptions;
    @FXML private Label lblTotalHT;
    @FXML private Label lblTVA;
    @FXML private Label lblTaxeSejour;
    @FXML private Label lblTotalTTC;

    // Méthode de paiement - ToggleGroup et RadioButtons
    @FXML private ToggleGroup methodePaiement;
    @FXML private RadioButton radioCarteBancaire;
    @FXML private RadioButton radioEspeces;
    @FXML private RadioButton radioVirement;
    @FXML private RadioButton radioFacturation;

    // Boutons d'action
    @FXML private Button btnEnregistrer;
    @FXML private Button btnValiderFacturation;

    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public Client getClient() {
        int idClient = reservation.getIdClient();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        return clientDAO.findById(idClient);
    }

    public List<Comprend> getOptions(Reservation reservation){
        ComprendDAOImpl comprendDAO = new ComprendDAOImpl();
        int idReservation = reservation.getIdReservation();
        List<Comprend> listOptions = comprendDAO.findOptionsByReservationId(idReservation);

        return  listOptions;
    }

    public void afficherOptions(Reservation reservation) {
        List<Comprend> listOptions = getOptions(reservation);
        OptionReservationDAOImpl optionDAO = new OptionReservationDAOImpl();

        ObservableList<OptionTableau> optionsAffichees = FXCollections.observableArrayList();

        for (Comprend oneOption : listOptions) {
            OptionReservation option = optionDAO.findById(oneOption.getIdOption());

            if (option != null) {
                String libelleOption = option.getLibelle();
                double prixOption = option.getPrixUnitaire();
                int quantiteOption = oneOption.getQuantite();

                OptionTableau opt = new OptionTableau(libelleOption, quantiteOption, prixOption);
                optionsAffichees.add(opt);
            } else {
                System.out.println("Option avec ID " + oneOption.getIdOption() + " non trouvée.");
            }
        }

        if (optionsAffichees.isEmpty()) {
            // Pas d'options valides à afficher -> afficher le message
            tableOptions.setItems(FXCollections.observableArrayList()); // vide
            tableOptions.setPlaceholder(new Label("Pas d'options à facturer"));
        }

        tableOptions.setItems(optionsAffichees);
    }

    //Réception de l'objet Rendez-vous pour la facturation
    public void initData(Reservation reservation) {
        this.reservation = reservation;

        // Configuration des colonnes de la table (à AJOUTER)
        colOptionDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colOptionQuantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());
        colOptionPrixUnitaire.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireProperty().asObject());

        afficherOptions(reservation);
        if (reservation != null) {
            //Détails facturation
            lblNumeroReservation.setText(String.valueOf(reservation.getIdReservation()));
            lblNbChambres.setText(String.valueOf(reservation.getNbrChambre()));
            lblNbPersonnes.setText(String.valueOf(reservation.getNbrPersonnes()));
            lblPeriodeSejour.setText(reservation.getDateDebut() + " au " + reservation.getDateFin());

            Client client = getClient();
            if (client != null) {
                lblNomClient.setText(client.getNom() + " " + client.getPrenom());
            } else {
                lblNomClient.setText("Client introuvable");
            }

            //Détails options

            //Détails montant
            lblMontantChambres.setText(String.format("%.2f €", calculMontantHebergement(reservation)));
            lblMontantOptions.setText(String.format("%.2f €", calculMontantOptions(reservation)));
            lblTaxeSejour.setText(String.format("%.2f €", calculTaxeSejour(reservation)));
            lblTotalHT.setText(String.format("%.2f €", calculMontantHT(reservation)));
            lblTVA.setText(String.format("%.2f €", calculMontantTVA(reservation)));
            lblTotalTTC.setText(String.format("%.2f €", calculMontantTTC(reservation)));
        } else {
            System.out.println("Réservation nulle dans initData");
        }
    }

    public double calculMontantHebergement(Reservation reservation) {
        double prixChambre = Double.parseDouble(ConfigManager.getConstanteByLibelle("prixBaseChambre").getValeur());
        int nbrPersonnes = reservation.getNbrPersonnes();
        int nbrChambre = reservation.getNbrChambre();

        double prixTotalChambre = prixChambre * nbrChambre;
        double prixPersSupplementaires = 0;  // Initialise hors du if

        if (nbrPersonnes > nbrChambre) {
            double pourcentagePersSupp = Double.parseDouble(ConfigManager.getConstanteByLibelle("pourcentagePersonneSupp").getValeur());
            int persSupplementaires = nbrPersonnes - nbrChambre;
            prixPersSupplementaires = persSupplementaires * prixChambre * pourcentagePersSupp;
            prixPersSupplementaires = persSupplementaires * prixChambre * pourcentagePersSupp;
        }
        return prixTotalChambre + prixPersSupplementaires;
    }
    public double calculMontantOptions(Reservation reservation) {
        List<Comprend> listOptions = getOptions(reservation);
        OptionReservationDAOImpl optionDAO = new OptionReservationDAOImpl();

        double montantTotalOptions = 0;

        for (Comprend oneOption : listOptions) {
            OptionReservation option = optionDAO.findById(oneOption.getIdOption());

            if (option != null) {
                double prixOption = option.getPrixUnitaire();
                int quantiteOption = oneOption.getQuantite();
                montantTotalOptions += prixOption * quantiteOption;
            } else {
                System.out.println("Option avec ID " + oneOption.getIdOption() + " non trouvée.");
            }
        }
        return montantTotalOptions;
    }

    public double calculMontantHT(Reservation reservation){
        double montantTotalHT = calculMontantHebergement(reservation) + calculMontantOptions(reservation);
        return montantTotalHT;
    }
    public double calculMontantTVA(Reservation reservation){
        double taxeSejour = Double.parseDouble(ConfigManager.getConstanteByLibelle("taxeSejour").getValeur());
        double montantTVA = calculMontantTTC(reservation)-calculMontantHT(reservation)-calculTaxeSejour(reservation);
        return montantTVA;
    }

    public double calculTaxeSejour(Reservation reservation){
        double taxeSejour = Double.parseDouble(ConfigManager.getConstanteByLibelle("taxeSejour").getValeur());
        int nbrPersonne = reservation.getNbrPersonnes();
        LocalDate dateDebutSejour = reservation.getDateDebut();
        LocalDate dateFinSejour = reservation.getDateFin();
        long nbrNuitTotal = ChronoUnit.DAYS.between(dateDebutSejour,dateFinSejour);
        // on caste le long
        int nbrNuitTotalInt = (int) nbrNuitTotal;
        double montantTotalTaxeSejour = taxeSejour * nbrPersonne * nbrNuitTotalInt;

        return montantTotalTaxeSejour;
    }

    public double calculMontantTTC(Reservation reservation){
        double TVA = Double.parseDouble(ConfigManager.getConstanteByLibelle("TVA").getValeur());
        double montantTotalTTC = calculMontantHT(reservation) * (1+TVA) + calculTaxeSejour(reservation);
        return montantTotalTTC;
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private double parseMontant(String montantLabel) {
        // Exemples autorisés : "123,45 €", "123.45", "123"
        String cleaned = montantLabel.replace("€", "").replace(",", ".").trim();
        if (cleaned.isEmpty()) return 0;
        return Double.parseDouble(cleaned);
    }

    // Action sur le bouton valider la facturation
    @FXML
    private void validerFacturation() {
        try {
            int idReservation = Integer.parseInt(lblNumeroReservation.getText());
            ReservationDAOImpl reservationDAO = new ReservationDAOImpl();
            Reservation reservation = reservationDAO.findById(idReservation);
            if(reservation == null) {
                showAlert("Erreur", "Impossible de retrouver la réservation.");
                return;
            }
            // Vérification de la présence d'une facture déjà existante
            if(reservation.getIdFacture() != 0) {
                showAlert("Erreur", "Cette réservation possède déjà une facture. Il est impossible de la refacturer.");
                return;
            }
            int idClient = reservation.getIdClient();
            double totalTTC = parseMontant(lblTotalTTC.getText());
            int idStatutFacture = 1;
            LocalDate dateFacture = LocalDate.now();

            Facturation facturation = new Facturation(
                    0,
                    totalTTC,
                    java.sql.Date.valueOf(dateFacture),
                    idStatutFacture,
                    idClient
            );

            FacturationDAOImpl facturationDAO = new FacturationDAOImpl();
            int idFactureGenere = facturationDAO.saveAndReturnId(facturation);

            // Mettre à jour la réservation avec l'id de la facture nouvellement créée
            if (idFactureGenere > 0) {
                reservationDAO.updateIdFacture(idReservation, idFactureGenere);
                showAlert("Succès", "Facturation créée et liée à la réservation !");
            } else {
                showAlert("Erreur", "La facturation a échoué.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la facturation :\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    // Action sur le bouton enregistrer brouillon (à rattacher dans FXML si besoin)
    @FXML
    private void enregistrerBrouillon() {
        // TODO: Implémenter la sauvegarde du brouillon
    }

    // Eventuellement d'autres méthodes utiles selon besoin

}