package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Modeles.AbsenceTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.Style;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AbsenceAnterieurController {
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
    private TableColumn<AbsenceTableView, String> columnJustifie;

    @FXML
    private TableColumn<AbsenceTableView, String> columnMotif;

    @FXML
    private MFXTextField tfFiltre;

    private TableColumn<AbsenceTableView, Button> columnEdite;

    private ArrayList<AbsenceTableView> absenceListe;

    private AbsenceDAO absenceDAO;

    private ObservableList<AbsenceTableView> observableList;



    @FXML
    void initialize() {
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
        columnJustifie.setCellValueFactory(new PropertyValueFactory<>("justifie"));
        columnMotif.setCellValueFactory(new PropertyValueFactory<>("motif"));

        columnEdite = new TableColumn<>("Modifier");
        columnEdite.setCellFactory(param -> new TableCell<AbsenceTableView, Button>() {
            private final Button editButton = new Button("Editer"); // Bouton personnalisé

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    editButton.setOnAction(event -> {
                        try {
                            AbsenceTableView absence = getTableView().getItems().get(getIndex());
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ModifierAbsence.fxml"));
                            Parent root = fxmlLoader.load();
                            ModifierAbsenceController modifierAbsenceController = fxmlLoader.getController();
                            modifierAbsenceController.setAbsenceTableView(absence);
                            modifierAbsenceController.setAbsenceAnterieurController(AbsenceAnterieurController.this);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setAlwaysOnTop(true);
                            stage.showAndWait();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    setGraphic(editButton);
                    setText(null);
                }
            }
        });
        tableviewAbsence.getColumns().add(columnEdite);
        getAbsenceListe();
        filtreTableViewAbsence();
    }

    public void getAbsenceListe() {
        tableviewAbsence.getItems().clear();
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
                }else if(absence.getJustifie().toLowerCase().contains(lowerCaseFilter)){
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
                    column.setPrefWidth(totalWidth * 0.086);
                    break;
                case "Nom Prenom":
                    column.setPrefWidth(totalWidth * 0.1215);
                    break;
                case "Module":
                    column.setPrefWidth(totalWidth * 0.2215);
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
                case "Justifie":
                    column.setPrefWidth(totalWidth * 0.07);
                    break;
                case "Motif":
                    column.setPrefWidth(totalWidth * 0.14);
                    break;
                case "Modifier":
                    column.setPrefWidth(totalWidth * 0.0994);
                    break;
            }
        }
    }
}
