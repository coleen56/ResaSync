package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ClientDAOImpl;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.entity.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import fr.bts.sio.resasync.config.ConfigManager;

public class FacturationController {

    // Labels infos réservation
    @FXML private Label lblNumeroReservation;
    @FXML private Label lblNomClient;
    @FXML private Label lblNbChambres;
    @FXML private Label lblNbPersonnes;
    @FXML private Label lblPeriodeSejour;

    // Table options
    @FXML private TableView<?> tableOptions;
    @FXML private TableColumn<?, ?> colOptionDescription;
    @FXML private TableColumn<?, ?> colOptionQuantite;
    @FXML private TableColumn<?, ?> colOptionPrixUnitaire;
    @FXML private TableColumn<?, ?> colOptionMontant;

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

    //Réception de l'objet Rendez-vous pour la facturation
    public void initData(Reservation reservation) {
        this.reservation = reservation;

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
            System.out.println("Il y a un supplément ! NbrPersonnes = " + nbrPersonnes + ", NbrChambre = " + nbrChambre);
        }
        return prixTotalChambre + prixPersSupplementaires;
    }
    public double calculMontantOptions(Reservation reservation){
//TODO à compléter qund les options seront disponibles
        return 0;
    }

    public double calculMontantHT(Reservation reservation){
        double montantTotalHT = calculMontantHebergement(reservation) + calculMontantOptions(reservation);
        return montantTotalHT;
    }
    public double calculMontantTVA(Reservation reservation){
        double montantTVA = calculMontantTTC(reservation)-calculMontantHT(reservation);
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

        return taxeSejour;
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