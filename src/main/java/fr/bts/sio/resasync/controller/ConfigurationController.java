package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.config.Config;
import fr.bts.sio.resasync.config.ConfigManager;
import fr.bts.sio.resasync.model.dao.implementations.ConstantesDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ConstantesDAO;
import fr.bts.sio.resasync.model.entity.Constantes;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.util.Methods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ConfigurationController {

    @FXML
    private Hyperlink lienReservations;
    // récup des champs concernant la TVA
    @FXML
    private Button TVAvalidateButton;
    @FXML
    private Button TVAmodifButton;
    @FXML
    private TextField TVAvalue;

    // récup des champs concernant le % supp par personne
    @FXML
    private Button PersSuppModifButton;
    @FXML
    private Button PersSuppValidateButton;
    @FXML
    private TextField PersSuppValue;

    // récup des champs concernant la taxe de séjour
    @FXML
    private Button TSmodifButton;
    @FXML
    private Button TSvalidateButton;
    @FXML
    private TextField TSvalue;

    // récup des champs concernant le prix unitaire des chambres
    @FXML
    private Button PrixChModifButton;
    @FXML
    private Button PrixChValidateButton;
    @FXML
    private TextField PrixChValue;

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
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
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    @FXML
    public void initialize() {
        chargerValeurConstante(); // a l'appel du controleur, on affiche par défaut dans les champs les valeurs actuelles des constantes (stockées dans config.json)
    }

    private void chargerVue(String fichierFxml) {
        try {
            // Obtenir la fenêtre actuelle (Stage) via un des hyperliens
            Stage stage = (Stage) lienReservations.getScene().getWindow();

            // Utiliser la méthode utilitaire pour charger la nouvelle vue
            Methods.chargerVue(fichierFxml, stage);
        } catch (Exception e) {
            // Afficher une trace en cas d'erreur
            e.printStackTrace();
        }
    }

    private void chargerValeurConstante() {
        TVAvalue.setText((ConfigManager.getConstanteByLibelle("TVA")).getValeur());
        PrixChValue.setText((ConfigManager.getConstanteByLibelle("prixBaseChambre")).getValeur());
        TSvalue.setText((ConfigManager.getConstanteByLibelle("taxeSejour")).getValeur());
        PersSuppValue.setText((ConfigManager.getConstanteByLibelle("pourcentagePersonneSupp")).getValeur());
    }

    public void allowModif(ActionEvent e) {
        Button sourceBouton = (Button) e.getSource(); // on récupère le bouton sur lequel on a cliqué
        String buttonId = sourceBouton.getId(); // on récupère son id
        switch (buttonId) {
            case "TVAmodifButton" : // en fonction du bouton "modifier" cliqué, on rend le champ de texte et bouton "valider" associés modifiables/cliquables
                TVAvalue.setDisable(false);
                TVAvalidateButton.setDisable(false);
            break;
            case "TSmodifButton" :
                TSvalue.setDisable(false);
                TSvalidateButton.setDisable(false);
            break;
            case "PersSuppModifButton" :
                PersSuppValue.setDisable(false);
                PersSuppValidateButton.setDisable(false);
            break;
            case "PrixChModifButton" :
                PrixChValue.setDisable(false);
                PrixChValidateButton.setDisable(false);
        }
        sourceBouton.setDisable(true); // on disable le bouton "modifier" qui a été cliqué
    }

    public void updateVal(ActionEvent e) throws IOException {
        Button source = (Button) e.getSource();
        String libelle = source.getId();
        TextField textField = null;
        String constanteName = null;
        switch (libelle) {
            case "TVAvalidateButton":
                textField = TVAvalue;
                constanteName = "TVA";
                TVAvalidateButton.setDisable(true);
                TVAmodifButton.setDisable(false);
                TVAvalue.setDisable(true);
            break;
            case "PersSuppValidateButton":
                textField = PersSuppValue;
                constanteName = "pourcentagePersonneSupp";
                PersSuppValidateButton.setDisable(true);
                PersSuppModifButton.setDisable(false);
                PersSuppValue.setDisable(true);
            break;
            case "PrixChValidateButton":
                textField = PrixChValue;
                constanteName = "prixBaseChambre";
                PrixChValidateButton.setDisable(true);
                PrixChModifButton.setDisable(false);
                PrixChValue.setDisable(true);
            break;
            case "TSvalidateButton":
                textField = TSvalue;
                constanteName = "taxeSejour";
                TSvalidateButton.setDisable(true);
                TSmodifButton.setDisable(false);
                TSvalue.setDisable(true);
            break;
        }
        if(textField.getText() != null) {
            ConfigManager.updateConstante(constanteName, textField.getText());
            System.out.println("Constante de configuration " + constanteName + " bien modifiée dans le fichier JSON");
            LocalDate today = LocalDate.now();
            Constantes constante = new Constantes(constanteName, textField.getText(), today, null);
            ConstantesDAO cstDao = new ConstantesDAOImpl();
            cstDao.updateLastConstante(constanteName);
            cstDao.save(constante);
            Methods.writeLogs("Modification " + constanteName, Session.getInstance().getLogin(), LocalDateTime.now(), true, "Insertion en base de donnée réussie; nouvelle valeur : " + constante.getValeur());
        } else {
            // todo : ajouter un message d'erreur si la saisie n'est pas valide
        }


    }

//    public void ajouterConstante() throws IOException {
//        ConfigManager.updateConstante("TVA", "0.5");
//    }


}
