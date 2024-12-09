package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Dao.ClasseDAO;
import absence.Dao.EnvoiMailDAO;
import absence.Modeles.EtudiantAVertissement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class EtudiantAVertissementController {

    @FXML
    private TableView<EtudiantAVertissement> tableViewAbsences;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colNom;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colPrenom;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colFiliere;
    @FXML
    private TableColumn<EtudiantAVertissement, Integer> colAbsences;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colEmail;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colTelephone;
    @FXML
    private TableColumn<EtudiantAVertissement, String> colMessage;
    @FXML
    private TableColumn<EtudiantAVertissement, Void> colEnvoyerMail;  // Colonne pour les boutons

    private AbsenceDAO absenceDAO;
    private ClasseDAO classeDAO;

    public EtudiantAVertissementController() {
        absenceDAO = new AbsenceDAO();
        classeDAO = new ClasseDAO();
    }

    @FXML
    public void initialize() {
        System.out.println("Initialisation de la vue des étudiants avec avertissements");

        // Configuration des colonnes de la TableView
        colNom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtudiant().getNomEtudiant()));
        colPrenom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtudiant().getPrenomEtudiant()));
        colFiliere.setCellValueFactory(data -> {
            try {
                return new SimpleStringProperty(classeDAO.obtenirNomClasseParId(data.getValue().getEtudiant().getIdClasse()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        colAbsences.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNombreAbscence()).asObject());
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtudiant().getEmail()));
        colTelephone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtudiant().getTelephone()));
        colMessage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMessage()));

        // Récupérer les absences des étudiants et les informations complètes
        List<EtudiantAVertissement> absencesList = absenceDAO.getAbsencesParEtudiant();
        System.out.println("Données récupérées depuis le DAO : " + absencesList);

        // Filtrage : ne garder que les absences > 8 heures
        absencesList.removeIf(etudiant -> etudiant.getNombreAbscence() < 8);
        System.out.println("Liste après filtrage des absences supérieures à 8 : " + absencesList.size());

        // Boucler sur la liste pour ajouter les messages selon les absences
        for (EtudiantAVertissement etudiant : absencesList) {
            // Récupérer le message basé sur le nombre d'absences
            String message = getMessageByAbsence(etudiant.getNombreAbscence());
            // Assigner le message à l'étudiant
            etudiant.setMessage(message);
            // Affichage du message pour chaque étudiant
            System.out.println("Message pour " + etudiant.getEtudiant().getNomEtudiant() + ": " + message);
        }

        // Ajouter les données filtrées et enrichies à la TableView
        tableViewAbsences.getItems().setAll(absencesList);

        // Configuration de la colonne "Envoyer Mail"
        colEnvoyerMail.setCellFactory(col -> new TableCell<EtudiantAVertissement, Void>() {
            private final Button btnEnvoyer = new Button("Envoyer");
            {
                // Personnalisation du bouton
                btnEnvoyer.setStyle("-fx-background-color: #88c78b; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-border-radius: 10px; -fx-cursor: hand;");

                // Ajouter un effet au survol
                btnEnvoyer.setOnMouseEntered(event -> btnEnvoyer.setStyle("-fx-background-color: #58c55d; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-border-radius: 10px; -fx-cursor: hand;"));
                btnEnvoyer.setOnMouseExited(event -> btnEnvoyer.setStyle("-fx-background-color: #88c78b; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-border-radius: 10px; -fx-cursor: hand;"));

                // Action lors du clic sur le bouton
                btnEnvoyer.setOnAction(event -> handleEnvoyerMail(getTableRow().getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEnvoyer);
                }
            }
        });
    }

    /**
     * Générer un message basé sur le nombre d'absences
     */
    private String getMessageByAbsence(int nombreAbsence) {
        if (nombreAbsence >= 16) {
            return "Traduit en conseil de discipline";
        } else if (nombreAbsence >= 14) {
            return "Blâme";
        } else if (nombreAbsence >= 10) {
            return "2ème Avertissement";
        } else if (nombreAbsence >= 8) {
            return "1er Avertissement";
        } else {
            return "Absence Justifiée ou Raisons Acceptées";
        }
    }

    // Méthode pour gérer l'envoi d'un email
    private void handleEnvoyerMail(int index) {
        // Récupérer l'étudiant de la ligne actuelle
        EtudiantAVertissement etudiant = tableViewAbsences.getItems().get(index);

        // Récupérer l'email et le message de l'étudiant
        String email = etudiant.getEtudiant().getEmail();
        String message = etudiant.getMessage();

        // Créer une boîte de dialogue de confirmation
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation d'envoi");
        confirmationDialog.setHeaderText("Êtes-vous sûr de vouloir envoyer cet email ?");
        confirmationDialog.setContentText("Email : " + email + "\nMessage : " + message);

        // Ajouter des boutons pour la confirmation
        ButtonType boutonOui = new ButtonType("Oui");
        ButtonType boutonNon = new ButtonType("Non");
        confirmationDialog.getButtonTypes().setAll(boutonOui, boutonNon);

        // Attendre la réponse de l'utilisateur
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == boutonOui) {
                // Si l'utilisateur confirme, enregistrer et envoyer l'email
                EnvoiMailDAO envoiMailDAO = new EnvoiMailDAO();
                envoiMailDAO.enregistrerEnvoi(email, message);
                envoiMailDAO.envoyerEmail(email, message, etudiant.getEtudiant().getNomEtudiant() + " " + etudiant.getEtudiant().getPrenomEtudiant());

                // Message de succès
                Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
                successDialog.setTitle("Email envoyé");
                successDialog.setHeaderText(null);
                successDialog.setContentText("L'email a été envoyé avec succès à " + etudiant.getEtudiant().getNomEtudiant() + " " + etudiant.getEtudiant().getPrenomEtudiant() + ".");
                successDialog.showAndWait();
            }
        });
    }
}
