package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Dao.ClasseDAO;
import absence.Dao.FiliereDAO;
import absence.Modeles.Classe;
import absence.Modeles.Filiere;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccueilAdminController {

    @FXML
    private Label labelNombreAbsenceAujourdhui;

    @FXML
    private Label labelNombreAbsenceCetteSemaine;

    @FXML
    private Label labelNombreAbsenceCeMois;

    @FXML
    private TreeView<String> listeAbsences;

    private AbsenceDAO absenceDAO;

    private List<Filiere> filieres;
    private List<Classe>  classes;

    private FiliereDAO filiereDAO;
    private ClasseDAO classeDAO;

    @FXML
    public void initialize() throws SQLException {
        filiereDAO = new FiliereDAO();
        absenceDAO = new AbsenceDAO();
        classeDAO = new ClasseDAO();
        mettreAJourStatistiquesAbsence();
        getAbsences();
    }

    private void mettreAJourStatistiquesAbsence() {
        try {
            absenceDAO = new AbsenceDAO();
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

    private void getAbsences() throws SQLException {
        List<Filiere> filiers = filiereDAO.obtenirToutesLesFilieres();
        List<Classe> classes = classeDAO.obtenirToutesLesClasses();
        ArrayList<String> absencesAujourdhui = new ArrayList<>();
        TreeItem<String> absencesAujourdhuiTreeItem = new TreeItem<>();
        for (Filiere filiere : filiers) {
            TreeItem<String> filierItem = new TreeItem<>(filiere.getNomFiliere());
            for (Classe classe : classes) {
                if (filiere.getIdFiliere() == classe.getIdFiliere()) {
                    TreeItem<String> classeItem = new TreeItem<>(classe.getNomClasse());
                    absencesAujourdhui = absenceDAO.getAbsencesAujourdHui(filiere.getNomFiliere(), classe.getNomClasse());
                    if (!absencesAujourdhui.isEmpty()) {
                        for (String absence : absencesAujourdhui) {
                            classeItem.getChildren().add(new TreeItem<>(absence));
                        }
                    } else {
                        classeItem.getChildren().add(new TreeItem<>("Aucune absence."));
                    }
                    filierItem.getChildren().add(classeItem);
                }
            }
            absencesAujourdhuiTreeItem.getChildren().add(filierItem);
        }

        listeAbsences.setRoot(absencesAujourdhuiTreeItem);
    }
}
