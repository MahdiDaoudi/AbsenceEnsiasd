package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Dao.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AccueilAdminController {

    @FXML
    private Label labelNombreAbsenceAujourdhui;

    @FXML
    private Label labelNombreAbsenceCetteSemaine;

    @FXML
    private Label labelNombreAbsenceCeMois;

    private final AbsenceDAO absenceDAO;

    public AccueilAdminController() {
        this.absenceDAO = new AbsenceDAO();
    }

    @FXML
    public void initialize() {
        mettreAJourStatistiquesAbsence();
    }

    private void mettreAJourStatistiquesAbsence() {
        try {
            // Récupération des données depuis le DAO
            int absencesAujourdhui = absenceDAO.getNombreAbsencesAujourdHui();
            int absencesCetteSemaine = absenceDAO.getNombreAbsencesCetteSemaine();
            int absencesCeMois = absenceDAO.getNombreAbsencesCeMois();

            // Mise à jour des labels dans la vue
            labelNombreAbsenceAujourdhui.setText(String.valueOf(absencesAujourdhui));
            labelNombreAbsenceCetteSemaine.setText(String.valueOf(absencesCetteSemaine));
            labelNombreAbsenceCeMois.setText(String.valueOf(absencesCeMois));

        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour des statistiques d'absence: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
