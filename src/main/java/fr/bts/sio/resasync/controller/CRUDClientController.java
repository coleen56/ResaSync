package fr.bts.sio.resasync.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;

import java.util.Date;

public class CRUDClientController {
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtTel;
    @FXML private TextField txtEmail;
    @FXML private DatePicker txtDateNaiss;


    @FXML
    private void envoyerFormulaire() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String telephone = txtTel.getText();
        String email = txtEmail.getText();

        System.out.println("Voici le contenu du formulaire Client :");
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
        System.out.println("telephone: " + telephone);
        System.out.println("Email: " + email);
    }
}
