package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Modeles.AbsenceTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class HistoriqueAbsenceController {

    @FXML
    private TableView<AbsenceTableView> tableviewAbsence;

    @FXML
    private TableColumn<AbsenceTableView, String> columnCne;

    @FXML
    private TableColumn<AbsenceTableView, String> columnNomPrenom;

    @FXML
    private TableColumn<AbsenceTableView, String> columnModule;

    @FXML
    private TableColumn<AbsenceTableView, String> columnSeance;

    @FXML
    private TableColumn<AbsenceTableView, LocalDate> columnDate;

    @FXML
    private TableColumn<AbsenceTableView, String> columnHeures;

    @FXML
    private TableColumn<AbsenceTableView, String> columnMotif;

    @FXML
    private MFXTextField tfFiltre;

    private ArrayList<AbsenceTableView> absenceListe;

    private AbsenceDAO absenceDAO;

    private ObservableList<AbsenceTableView> observableList;

    @FXML
    void initialize() {
        UserAgentBuilder.builder().themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setResolveAssets(true).build().setGlobal();
        absenceListe = new ArrayList<>();
        absenceDAO = new AbsenceDAO();
        remplireTableViewAbsence();
        tableviewAbsence.widthProperty().addListener((observable, oldValue, newValue) -> resizeTableView());
    }

    public void remplireTableViewAbsence() {
        columnCne.setCellValueFactory(new PropertyValueFactory<>("cne"));
        columnNomPrenom.setCellValueFactory(new PropertyValueFactory<>("nomPrenom"));
        columnModule.setCellValueFactory(new PropertyValueFactory<>("nomModule"));
        columnSeance.setCellValueFactory(new PropertyValueFactory<>("typeSeance"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateAbsence"));
        columnHeures.setCellValueFactory(new PropertyValueFactory<>("heures"));
        columnMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        getAbsenceListe();
        filtreTableViewAbsence();
    }

    public void getAbsenceListe() {
        absenceListe.clear();
        absenceListe = absenceDAO.getTousAbsences();
        observableList = FXCollections.observableList(absenceListe);
        tableviewAbsence.setItems(observableList);
    }

    public void filtreTableViewAbsence() {
        FilteredList<AbsenceTableView> filteredList = new FilteredList(observableList, p -> true);
        tfFiltre.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(absence->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (absence.getCne().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(absence.getNomPrenom().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(absence.getNomModule().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(absence.getTypeSeance().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(String.valueOf(absence.getDateAbsence()).toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (absence.getHeures().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(absence.getMotif().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        tableviewAbsence.setItems(filteredList);
    }

    public void resizeTableView() {
        double totalWidth = tableviewAbsence.getWidth();

        for (TableColumn column : tableviewAbsence.getColumns()) {
            switch (column.getText()) {
                case "CNE":
                    column.setPrefWidth(totalWidth * 0.116);
                    break;
                case "Nom Prenom":
                    column.setPrefWidth(totalWidth * 0.1215);
                    break;
                case "Module":
                    column.setPrefWidth(totalWidth * 0.2915);
                    break;
                case "Seance":
                    column.setPrefWidth(totalWidth * 0.0732);
                    break;
                case "Date":
                    column.setPrefWidth(totalWidth * 0.0914);
                    break;
                case "Heures":
                    column.setPrefWidth(totalWidth * 0.097);
                    break;
                case "Motif":
                    column.setPrefWidth(totalWidth * 0.2094);
                    break;
                case "Modifier":
                    column.setPrefWidth(totalWidth * 0.0994);
                    break;
            }
        }
    }
}
