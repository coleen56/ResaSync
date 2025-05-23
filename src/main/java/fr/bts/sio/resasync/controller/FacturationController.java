
package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ClientDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.ComprendDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.OptionReservationDAOImpl;
import fr.bts.sio.resasync.model.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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




    // Action sur le bouton valider la facturation
    @FXML
    private void validerFacturation() {
        // TODO: Implémenter la validation et sauvegarde de la facturation
    }

    // Action sur le bouton enregistrer brouillon (à rattacher dans FXML si besoin)
    @FXML
    private void enregistrerBrouillon() {
        // TODO: Implémenter la sauvegarde du brouillon
    }

    // Eventuellement d'autres méthodes utiles selon besoin

}
