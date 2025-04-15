package fr.bts.sio.resasync.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import fr.bts.sio.resasync.model.dao.implementations.ReservationDAOImpl;
import fr.bts.sio.resasync.model.entity.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.util.StringConverter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextField;

public class ReservationController {

    @FXML private TextField statutReservationField;
    @FXML private DatePicker dateReservationPicker;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private TextField nbrPersonnesField;
    @FXML private TextField nbrChambreField;
    @FXML private TextField idStatusResaField;
    @FXML private TextField idClientField;
    @FXML private TextField idFactureField;
    @FXML private TextField idRespField;
    @FXML private TextArea resultatTextArea;

    private ReservationDAOImpl reservationDAO;


    @FXML
    public void exporterReservationEnPdf() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Exporter en PDF");
        dialog.setHeaderText("Entrez l'ID de la réservation à exporter");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Reservation res = reservationDAO.findById(id);
                if (res == null) {
                    resultatTextArea.setText("Aucune réservation trouvée pour l’ID : " + id);
                    return;
                }

                // Création du PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("Reservation_" + id + ".pdf"));
                document.open();
                document.add(new Paragraph("Réservation n°" + res.getIdReservation()));
                document.add(new Paragraph("Statut : " + res.getStatusReservation()));
                document.add(new Paragraph("Date réservation : " + res.getDateReservation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                document.add(new Paragraph("Date début : " + res.getDateDebut().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                document.add(new Paragraph("Date fin : " + res.getDateFin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                document.add(new Paragraph("Nombre de personnes : " + res.getNbrPersonnes()));
                document.add(new Paragraph("Nombre de chambres : " + res.getNbrChambre()));
                document.add(new Paragraph("ID statut réservation : " + res.getIdStatusResa()));
                document.add(new Paragraph("ID client : " + res.getIdClient()));
                document.add(new Paragraph("ID facture : " + res.getIdFacture()));
                document.add(new Paragraph("ID responsable : " + res.getIdResp()));
                document.close();

                resultatTextArea.setText("PDF exporté : Reservation_" + id + ".pdf");

            } catch (Exception e) {
                resultatTextArea.setText("Erreur export PDF : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }


    // Initialisation du DAO
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:h2:./src/main/java/fr/bts/sio/resasync/storage/resa", "resasync", "resasync"
            );
            reservationDAO = new ReservationDAOImpl(conn);
        } catch (SQLException e) {
            resultatTextArea.setText("Erreur connexion DB : " + e.getMessage());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        configureDatePicker(dateReservationPicker, formatter);
        configureDatePicker(dateDebutPicker, formatter);
        configureDatePicker(dateFinPicker, formatter);
    }

    // Pour convertir les dates à l'affichage
    private void configureDatePicker(DatePicker picker, DateTimeFormatter formatter) {
        picker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
    }


    // Bouton Rechercher
    @FXML
    public void rechercherReservation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rechercher une réservation");
        dialog.setHeaderText("Entrez l'ID de la réservation à rechercher");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Reservation res = reservationDAO.findById(id);
                if (res != null) {
                    resultatTextArea.setText(res.toString());
                } else {
                    resultatTextArea.setText("Aucune réservation trouvée avec l’ID : " + id);
                }
            } catch (NumberFormatException e) {
                resultatTextArea.setText("ID invalide.");
            }
        });
    }


    // Bouton Créer
    @FXML
    public void creerReservation() {
        try {
            Reservation res = lireFormulaireSansId();
            reservationDAO.save(res);
            resultatTextArea.setText("Réservation créée avec succès !");
        } catch (Exception e) {
            resultatTextArea.setText("Erreur création : " + e.getMessage());
        }
    }


    // Bouton Mettre à jour
    @FXML
    public void mettreAJourReservation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mise à jour");
        dialog.setHeaderText("Entrez l'ID de la réservation à mettre à jour");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Reservation res = lireFormulaireSansId();
                res.setIdReservation(id);
                reservationDAO.update(res);
                resultatTextArea.setText("Réservation mise à jour !");
            } catch (Exception e) {
                resultatTextArea.setText("Erreur mise à jour : " + e.getMessage());
            }
        });
    }


    // Bouton Supprimer
    @FXML
    public void supprimerReservation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Suppression");
        dialog.setHeaderText("Entrez l'ID de la réservation à supprimer");
        dialog.setContentText("ID :");

        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Reservation res = new Reservation(id, "", null, null, null, "", 0, 0, 0, 0, 0);
                reservationDAO.delete(res);
                resultatTextArea.setText("Réservation supprimée !");
            } catch (Exception e) {
                resultatTextArea.setText("Erreur suppression : " + e.getMessage());
            }
        });
    }


    // Lecture du formulaire
    private Reservation lireFormulaireSansId() {
        return new Reservation(
                0, // id temporaire
                statutReservationField.getText(),
                dateReservationPicker.getValue(),
                dateDebutPicker.getValue(),
                dateFinPicker.getValue(),
                nbrPersonnesField.getText(),
                Integer.parseInt(nbrChambreField.getText()),
                Integer.parseInt(idStatusResaField.getText()),
                Integer.parseInt(idClientField.getText()),
                Integer.parseInt(idFactureField.getText()),
                Integer.parseInt(idRespField.getText())
        );
    }
}
