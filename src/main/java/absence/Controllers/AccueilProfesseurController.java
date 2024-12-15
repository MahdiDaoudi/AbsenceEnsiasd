package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import io.github.palexdev.materialfx.theming.base.Theme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.collections.FXCollections;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccueilProfesseurController {

    @FXML
    private AreaChart<String,Number> areaChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private HBox hbox;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private PieChart pieChart;

    private Utilisateur utilisateur;
    private AbsenceDAO absenceDAO;
    private HashMap<String,Integer> listeNombreAbsenceParModule;
    private HashMap<String,Integer> listeNombreAbsenceParSexe;

    @FXML
    void initialize() {
        absenceDAO = new AbsenceDAO();
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (utilisateur != null) {
            listeNombreAbsenceParModule = absenceDAO.getNombreAbsenceParModule(utilisateur.getID_User());
            listeNombreAbsenceParSexe = absenceDAO.getNombreAbsenceParSexe(utilisateur.getID_User());
            remplireAreaChart();
            remplirPieChart();
        }
    }

        public void remplireAreaChart() {
            areaChart.getData().clear();
            // Créer une nouvelle série de données
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Nombre d'absences par module"); // Légende de la série

            // Ajouter les données de la liste (HashMap) à la série
            for (Map.Entry<String, Integer> entry : listeNombreAbsenceParModule.entrySet()) {
                String module = entry.getKey();
                Integer nombreAbsence = entry.getValue();

                // Ajouter chaque module et son nombre d'absences à la série
                series.getData().add(new XYChart.Data<>(module, nombreAbsence));
            }

            // Ajouter la série remplie dans l'AreaChart
            areaChart.getData().add(series);

            // Appliquer un style CSS
            areaChart.getStylesheets().clear(); // Nettoyer les styles existants
            areaChart.getStylesheets().add(getClass().getResource("/Css/accueilProfesseur.css").toExternalForm());

            // Message pour confirmer
            System.out.println("L'AreaChart a été rempli avec succès !");
        }

    public void remplirPieChart() {
        // Effacer les anciennes données pour éviter des doublons
        pieChart.getData().clear();

        // Créer une liste observable pour les données du PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Ajouter les données de la liste à la ObservableList
        for (Map.Entry<String, Integer> entry : listeNombreAbsenceParSexe.entrySet()) {
            String sexe = entry.getKey();              // Clé : le sexe
            Integer nombreAbsence = entry.getValue();
            PieChart.Data data = new PieChart.Data(sexe, nombreAbsence);// Valeur : le nombre d'absences
            data.setName(sexe + " (" + nombreAbsence + ")");
            pieChartData.add(data);
        }

        // Appliquer les données au PieChart
        pieChart.setData(pieChartData);
    }



}
