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

/**
 * Contrôleur pour la gestion de la configuration de l'application.
 * Permet de consulter et modifier les constantes de configuration (TVA, taxe de séjour, prix, etc.).
 * Gère également la navigation entre les différentes vues de l'application.
 */
public class ConfigurationController {

    @FXML
    private Hyperlink lienReservations;

    // Champs concernant la TVA
    @FXML
    private Button TVAvalidateButton;
    @FXML
    private Button TVAmodifButton;
    @FXML
    private TextField TVAvalue;

    // Champs concernant le pourcentage supplémentaire par personne
    @FXML
    private Button PersSuppModifButton;
    @FXML
    private Button PersSuppValidateButton;
    @FXML
    private TextField PersSuppValue;

    // Champs concernant la taxe de séjour
    @FXML
    private Button TSmodifButton;
    @FXML
    private Button TSvalidateButton;
    @FXML
    private TextField TSvalue;

    // Champs concernant le prix unitaire des chambres
    @FXML
    private Button PrixChModifButton;
    @FXML
    private Button PrixChValidateButton;
    @FXML
    private TextField PrixChValue;

    /**
     * Navigue vers le dashboard.
     */
    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    /**
     * Navigue vers la vue des réservations.
     */
    @FXML
    public void allerAReservation() {
        chargerVue("Reservation.fxml");
    }

    /**
     * Navigue vers la vue des chambres.
     */
    @FXML
    public void allerAChambre() {
        chargerVue("Chambre.fxml");
    }

    /**
     * Navigue vers la vue des clients.
     */
    @FXML
    public void allerAClient() {
        chargerVue("Client.fxml");
    }

    /**
     * Déconnecte l'utilisateur avec confirmation, puis retourne à la page de connexion.
     */
    @FXML
    public void Deconnexion() {
        Methods.deconnexionAvecConfirmation(() -> chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    /**
     * Initialise le contrôleur :
     * Affiche par défaut dans les champs les valeurs actuelles des constantes (stockées dans config.json).
     */
    @FXML
    public void initialize() {
        chargerValeurConstante();
    }

    /**
     * Charge une vue à partir de son fichier FXML.
     * @param fichierFxml nom du fichier FXML à charger
     */
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

    /**
     * Charge et affiche dans les champs de saisie les valeurs actuelles des constantes (TVA, prix, taxe, etc.).
     */
    private void chargerValeurConstante() {
        TVAvalue.setText((ConfigManager.getConstanteByLibelle("TVA")).getValeur());
        PrixChValue.setText((ConfigManager.getConstanteByLibelle("prixBaseChambre")).getValeur());
        TSvalue.setText((ConfigManager.getConstanteByLibelle("taxeSejour")).getValeur());
        PersSuppValue.setText((ConfigManager.getConstanteByLibelle("pourcentagePersonneSupp")).getValeur());
    }

    /**
     * Permet de rendre éditable un champ de saisie et son bouton "valider" associé,
     * lors du clic sur le bouton "modifier" correspondant à une constante.
     * Le bouton "modifier" cliqué est ensuite désactivé.
     * @param e événement d'action (clic sur un bouton)
     */
    public void allowModif(ActionEvent e) {
        Button sourceBouton = (Button) e.getSource(); // on récupère le bouton sur lequel on a cliqué
        String buttonId = sourceBouton.getId(); // on récupère son id
        switch (buttonId) {
            case "TVAmodifButton" :
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
                break;
        }
        sourceBouton.setDisable(true); // on disable le bouton "modifier" qui a été cliqué
    }

    /**
     * Met à jour la valeur d'une constante de configuration lors de la validation par l'utilisateur.
     * Désactive le champ de saisie et le bouton "valider", réactive le bouton "modifier", puis met à jour la valeur dans le fichier JSON,
     * enregistre l'historique en base, et écrit dans les logs.
     * @param e événement d'action (clic sur un bouton "valider")
     * @throws IOException si une erreur survient lors de la mise à jour
     */
    public void updateVal(ActionEvent e) throws IOException {
        Button source = (Button) e.getSource();
        String libelle = source.getId();
        TextField textField = null;
        String constanteName = null;
        // On identifie quel bouton "valider" a été cliqué, et on agit sur le champ et la constante appropriés
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
        // Mise à jour de la constante si la nouvelle valeur n'est pas nulle
        if (textField.getText() != null) {
            ConfigManager.updateConstante(constanteName, textField.getText());
            System.out.println("Constante de configuration " + constanteName + " bien modifiée dans le fichier JSON");
            LocalDate today = LocalDate.now();
            Constantes constante = new Constantes(constanteName, textField.getText(), today, null);
            ConstantesDAO cstDao = new ConstantesDAOImpl();
            cstDao.updateLastConstante(constanteName);
            cstDao.save(constante);
            Methods.writeLogs("Modification " + constanteName, Session.getInstance().getLogin(), LocalDateTime.now(), true, "Insertion en base de donnée réussie; nouvelle valeur : " + constante.getValeur());
        } else {
            // TODO : ajouter un message d'erreur si la saisie n'est pas valide
        }
    }

    // Méthode de test/commentée pour ajouter une constante directement
//    public void ajouterConstante() throws IOException {
//        ConfigManager.updateConstante("TVA", "0.5");
//    }

}