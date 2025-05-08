package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.StatutChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.TypeChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ChambreDAO;
import fr.bts.sio.resasync.model.dao.interfaces.StatutChambreDAO;
import fr.bts.sio.resasync.model.dao.interfaces.TypeChambreDAO;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.entity.StatutChambre;
import fr.bts.sio.resasync.util.Methods;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChambreController {

    ChambreDAO chambreDAO = new ChambreDAOImpl();
    TypeChambreDAO typeChambreDAO = new TypeChambreDAOImpl();
    StatutChambreDAO statutChambre = new StatutChambreDAOImpl();

    @FXML
    private Hyperlink lienDashboard;

    @FXML
    public void allerADashboard() {
        chargerVue("Dashboard.fxml");
    }

    @FXML
    public void allerAClient() {
        chargerVue("Client.fxml");
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

    // Tableau des chambres
    @FXML
    private TableView<Chambre> tableChambres;
    @FXML
    private TableColumn<Chambre, Integer> colIdChambre;
    @FXML
    private TableColumn<Chambre, Integer> colNumChambre;
    @FXML
    private TableColumn<Chambre, String> colTypeChambre;
    @FXML
    private TableColumn<Chambre, String> colStatut;

    // Partie Ajouter
    @FXML
    private TextField fieldNumChambre;
    @FXML
    private ComboBox<String> comboTypeChambre;
    @FXML
    private Label ajouterChambreErreur;

    // Partie Modifier/Supprimer
    @FXML
    private TextField fieldNumChambreModif;
    @FXML
    private ComboBox<String> comboTypeChambreModif;
    @FXML
    private Button btnModifierChambre;
    @FXML
    private Button btnSupprimerChambre;
    @FXML
    private Label labelAucuneSelection;

    @FXML
    public void initialize() {
        // Initialisation des colonnes du tableau
        colNumChambre.setCellValueFactory(new PropertyValueFactory<>("numChambre"));
        colTypeChambre.setCellValueFactory(new PropertyValueFactory<>("typeChambreLibelle"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutChambreLibelle"));

        // Configuration des ComboBox
        List<String> typeOptions = new ArrayList<>();
        typeOptions.add("Lit double");
        typeOptions.add("2 lits simples");
        typeOptions.add("Lit simple et 2 lits superposés");

        // Configuration ComboBox partie Ajouter
        comboTypeChambre.setItems(FXCollections.observableArrayList(typeOptions));
        comboTypeChambre.getSelectionModel().select("Lit double"); // Sélection par défaut

        // Configuration ComboBox partie Modifier
        comboTypeChambreModif.setItems(FXCollections.observableArrayList(typeOptions));

        // Listener pour la sélection d'une ligne dans le tableau
        tableChambres.getSelectionModel().selectedItemProperty().addListener((obs, ancienneValeur, nouvelleValeur) -> {
            if (nouvelleValeur != null) {
                // Remplir les champs de modification avec les données sélectionnées
                fieldNumChambreModif.setText(String.valueOf(nouvelleValeur.getNumChambre()));
                comboTypeChambreModif.getSelectionModel().select(nouvelleValeur.getTypeChambreLibelle());

                // Activer les contrôles de modification
                fieldNumChambreModif.setDisable(false);
                comboTypeChambreModif.setDisable(false);
                btnModifierChambre.setDisable(false);
                btnSupprimerChambre.setDisable(false);
                labelAucuneSelection.setVisible(false);
            } else {
                // Désactiver les contrôles de modification si aucune sélection
                fieldNumChambreModif.setDisable(true);
                comboTypeChambreModif.setDisable(true);
                btnModifierChambre.setDisable(true);
                btnSupprimerChambre.setDisable(true);
                labelAucuneSelection.setVisible(true);
            }
        });

        // Charger les données et initialiser les contrôles
        afficherChambres();

        // Désactiver les contrôles de modification au démarrage
        fieldNumChambreModif.setDisable(true);
        comboTypeChambreModif.setDisable(true);
        btnModifierChambre.setDisable(true);
        btnSupprimerChambre.setDisable(true);
        labelAucuneSelection.setVisible(true);
    }

    public void afficherChambres() {
        ArrayList<Chambre> chambres = chambreDAO.findAll();
        tableChambres.setItems(FXCollections.observableArrayList(chambres));
    }

    @FXML
    public void gererAjouterChambre() {
        try {
            // Récupération des valeurs du formulaire d'ajout
            int numeroChambre = Integer.parseInt(fieldNumChambre.getText());
            int typeIndex = comboTypeChambre.getSelectionModel().getSelectedIndex() + 1;

            // Vérification pour éviter les doublons avec la méthode optimisée
            boolean numeroExistant = chambreDAO.findByNumeroChambre(numeroChambre);

            if (numeroExistant) {
                ajouterChambreErreur.setText("Le numéro de chambre existe déjà.");
                return;
            }

            // Création de la nouvelle chambre
            Chambre nouvelleChambre = new Chambre(0, numeroChambre, typeIndex, 1); // Statut disponible par défaut
            chambreDAO.ajouterChambre(nouvelleChambre);

            // Rafraîchir la table et réinitialiser le formulaire
            afficherChambres();
            fieldNumChambre.clear();
            comboTypeChambre.getSelectionModel().select("Lit double");
            ajouterChambreErreur.setText(""); // Effacer les messages d'erreur précédents

            System.out.println("Chambre ajoutée avec succès !");
        } catch (NumberFormatException e) {
            ajouterChambreErreur.setText("Numéro de chambre invalide.");
        }
    }

    @FXML
    public void gererModifierChambre() {
        Chambre chambreSelectionnee = tableChambres.getSelectionModel().getSelectedItem();

        if (chambreSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune chambre sélectionnée");
            alert.setContentText("Veuillez sélectionner une chambre à modifier.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modification");
        alert.setHeaderText("Voulez-vous enregistrer la modification ?");
        alert.setContentText("Cela modifiera la chambre sélectionnée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int nouveauNumero = Integer.parseInt(fieldNumChambreModif.getText());
                    int nouveauType = comboTypeChambreModif.getSelectionModel().getSelectedIndex() + 1;

                    // Vérification que le numéro n'est pas déjà pris par une autre chambre
                    // UNIQUEMENT si le numéro a changé
                    if (nouveauNumero != chambreSelectionnee.getNumChambre()) {
                        List<Chambre> toutesChambres = chambreDAO.findAll();
                        boolean numeroExistant = toutesChambres.stream()
                                .anyMatch(c -> c.getNumChambre() == nouveauNumero && c.getIdChambre() != chambreSelectionnee.getIdChambre());

                        if (numeroExistant) {
                            Alert erreur = new Alert(Alert.AlertType.ERROR);
                            erreur.setTitle("Erreur");
                            erreur.setHeaderText("Numéro déjà utilisé");
                            erreur.setContentText("Ce numéro est déjà attribué à une autre chambre.");
                            erreur.showAndWait();
                            return;
                        }
                    }

                    // Création de l'objet chambre modifiée
                    Chambre chambreModifiee = new Chambre(
                            chambreSelectionnee.getIdChambre(),
                            nouveauNumero,
                            nouveauType,
                            chambreSelectionnee.getIdStatutChambre()
                    );

                    // Mise à jour dans la base de données
                    chambreDAO.update(chambreModifiee);

                    // Rafraîchir l'affichage
                    afficherChambres();

                    // Réinitialiser la sélection
                    tableChambres.getSelectionModel().clearSelection();
                    fieldNumChambreModif.clear();
                    comboTypeChambreModif.getSelectionModel().clearSelection();

                    // Désactiver les contrôles de modification
                    fieldNumChambreModif.setDisable(true);
                    comboTypeChambreModif.setDisable(true);
                    btnModifierChambre.setDisable(true);
                    btnSupprimerChambre.setDisable(true);
                    labelAucuneSelection.setVisible(true);

                    System.out.println("Chambre modifiée avec succès !");
                } catch (NumberFormatException e) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR);
                    erreur.setTitle("Erreur");
                    erreur.setHeaderText("Format invalide");
                    erreur.setContentText("Le numéro de chambre doit être un nombre entier.");
                    erreur.showAndWait();
                }
            }
        });
    }

    @FXML
    public void gererSupprimerChambre() {
        Chambre chambreSelectionnee = tableChambres.getSelectionModel().getSelectedItem();

        if (chambreSelectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Aucune chambre sélectionnée");
            alert.setContentText("Veuillez sélectionner une chambre dans le tableau.");
            alert.showAndWait();
            return;
        }

        int idChambre = chambreSelectionnee.getIdChambre();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer la chambre ?");
        alert.setContentText("Chambre n° " + chambreSelectionnee.getNumChambre());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Supprimer la chambre
                chambreDAO.supprimerChambreById(idChambre);

                // Rafraîchir l'affichage
                afficherChambres();

                // Réinitialiser la sélection
                tableChambres.getSelectionModel().clearSelection();
                fieldNumChambreModif.clear();
                comboTypeChambreModif.getSelectionModel().clearSelection();

                // Désactiver les contrôles de modification
                fieldNumChambreModif.setDisable(true);
                comboTypeChambreModif.setDisable(true);
                btnModifierChambre.setDisable(true);
                btnSupprimerChambre.setDisable(true);
                labelAucuneSelection.setVisible(true);

                System.out.println("Chambre supprimée : ID " + idChambre);
            }
        });
    }
}