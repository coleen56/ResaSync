package fr.bts.sio.resasync.controller;

import fr.bts.sio.resasync.model.dao.implementations.ChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.StatutChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.implementations.TypeChambreDAOImpl;
import fr.bts.sio.resasync.model.dao.interfaces.ChambreDAO;
import fr.bts.sio.resasync.model.dao.interfaces.StatutChambreDAO;
import fr.bts.sio.resasync.model.dao.interfaces.TypeChambreDAO;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.entity.Session;
import fr.bts.sio.resasync.model.entity.StatutChambre;
import fr.bts.sio.resasync.util.Methods; // Import de la classe utilitaire Methods
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

    @FXML private Hyperlink lienDashboard; // Lien pour retourner au Dashboard

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
        Methods.deconnexionAvecConfirmation(() ->chargerVue("Login.fxml")); // Appel direct à la méthode utilitaire
    }

    private void chargerVue(String fichierFxml) {
        try {
            // Obtenir la fenêtre actuelle (Stage) via l'un des liens (par exemple, lienDashboard)
            Stage stage = (Stage) lienDashboard.getScene().getWindow();

            // Utiliser la méthode utilitaire pour charger la nouvelle vue
            Methods.chargerVue(fichierFxml, stage); // Appel de la méthode utilitaire centralisée
        } catch (Exception e) {
            // Afficher une trace en cas d'erreur
            e.printStackTrace();
        }
    }

    @FXML private TextField fieldNumChambre;
    @FXML private ComboBox comboTypeChambre;



    public void gererAjouterChambre(){
        //Parse le retour de fieldNumChambre
        int numero = Integer.parseInt(fieldNumChambre.getText());;
        boolean existe = chambreDAO.findByNumeroChambre(numero);

        if (existe) {
            System.out.println("La chambre avec ce numéro existe déjà.");
        } else {
            System.out.println("La chambre n'existe pas.");
        }
        //TODO Vérifier que le numéro de la chambre n'existe pas déjà -> Créer un findByNumeroChambre
        //Permet de retourner un chiffre entre 0 et 2
        int type = (comboTypeChambre.getSelectionModel().getSelectedIndex())+1;

        Chambre chambre = new Chambre(numero,type,1);
        chambreDAO.ajouterChambre(chambre);
        System.out.println("Chambre Ajoutée :");
        System.out.println("NumChambre: " + chambre.getNumChambre());
        System.out.println("IdTypeChambre: " + chambre.getIdTypeChambre());
        System.out.println("IdStatutChambre: " + chambre.getIdStatutChambre());
    }

    @FXML private TableView<Chambre> tableChambres;
    @FXML TableColumn<Chambre, Integer> colNumChambre;
    @FXML TableColumn<Chambre, Integer> colTypeChambre;
    @FXML TableColumn<Chambre, Integer> colStatut;
    @FXML
    public void initialize() {
    colNumChambre.setCellValueFactory(new PropertyValueFactory<>("NumChambre"));
    colTypeChambre.setCellValueFactory(new PropertyValueFactory<>("TypeChambreLibelle"));
    colStatut.setCellValueFactory(new PropertyValueFactory<>("StatutChambreLibelle"));

    afficherChambres();
    }

    public void afficherChambres(){
        ArrayList<Chambre> chambres = chambreDAO.findAll();
        tableChambres.setItems(FXCollections.observableArrayList(chambres));


    }


}