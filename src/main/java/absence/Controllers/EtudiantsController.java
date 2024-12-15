package absence.Controllers;

import absence.Dao.EtudiantDAO;
import absence.Modeles.Etudiant;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EtudiantsController {

    @FXML
    private TableColumn<Etudiant, String> columnClasse;

    @FXML
    private TableColumn<Etudiant, String> columnCne;

    @FXML
    private TableColumn<Etudiant, String> columnEmail;

    @FXML
    private TableColumn<Etudiant, String> columnFiliere;

    @FXML
    private TableColumn<Etudiant, String> columnNomPrenom;

    @FXML
    private TableColumn<Etudiant, String> columnSexe;

    @FXML
    private TableColumn<Etudiant, String> columnTelephone;

    @FXML
    private TableView<Etudiant> tableviewEtudiants;

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private MFXTextField tfFiltre;

    private EtudiantDAO etudiantDAO;
    private List<Etudiant> etudiants;
    private ObservableList<Etudiant> observableList;

    private TableColumn<Etudiant, HBox> columnAction;

    private ImageView imageViewSupprimer;
    private ImageView imageViewModifier;

    @FXML
    void initialize() throws SQLException {
        UserAgentBuilder.builder().themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setResolveAssets(true).build().setGlobal();
        Image supprimerImage = new Image(getClass().getResourceAsStream("/asset/supprimer.png"));
        imageViewSupprimer = new ImageView(supprimerImage);
        imageViewSupprimer.setFitWidth(16); // Ajuster la taille
        imageViewSupprimer.setFitHeight(16);

        Image modifierImage = new Image(getClass().getResourceAsStream("/asset/modifier.png"));
        imageViewModifier = new ImageView(modifierImage);
        imageViewModifier.setFitWidth(16);
        imageViewModifier.setFitHeight(16);

        etudiantDAO = new EtudiantDAO();
        remplireTableEtudiants();
        tableviewEtudiants.widthProperty().addListener(((observable, oldValue, newValue) -> resizeTableView()));
        filtreTableViewAbsence();
    }

    public void remplireTableEtudiants() throws SQLException {
        columnCne.setCellValueFactory(new PropertyValueFactory<>("cne"));
        columnNomPrenom.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        columnFiliere.setCellValueFactory(new PropertyValueFactory<>("nomFiliere"));
        columnClasse.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        columnSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnAction = new TableColumn<>("Action");
        columnAction.setCellFactory(param -> new TableCell<Etudiant, HBox>() {
            ImageView imageViewSupprimer;
            ImageView imageViewModifier;
            Button modifierBtn;
            Button supprimerBtn;
            HBox hBox;

            {
                Image supprimerImage = new Image(getClass().getResourceAsStream("/asset/supprimer.png"));
                imageViewSupprimer = new ImageView(supprimerImage);
                imageViewSupprimer.setFitWidth(25);
                imageViewSupprimer.setFitHeight(25);

                Image modifierImage = new Image(getClass().getResourceAsStream("/asset/modifier.png"));
                imageViewModifier = new ImageView(modifierImage);
                imageViewModifier.setFitWidth(25);
                imageViewModifier.setFitHeight(25);

                modifierBtn = new Button();
                modifierBtn.setGraphic(imageViewModifier);

                supprimerBtn = new Button();
                supprimerBtn.setGraphic(imageViewSupprimer);

                hBox = new HBox(10, modifierBtn, supprimerBtn);
                hBox.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    modifierBtn.setOnAction(event -> {
                        Etudiant etudiant = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ModifierEtudiant.fxml"));
                            Parent root = fxmlLoader.load();
                            ModifierEtudiantController modifierEtudiantController = fxmlLoader.getController();
                            modifierEtudiantController.setEtudiantsController(EtudiantsController.this);
                            modifierEtudiantController.setEtudiant(etudiant);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Modifier etudiant");
                            stage.setScene(scene);
                            stage.setAlwaysOnTop(true);
                            stage.showAndWait();
                        } catch (RuntimeException | IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    supprimerBtn.setOnAction(event -> {
                        Etudiant etudiant = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SupprimerEtudiant.fxml"));
                            Parent root = fxmlLoader.load();
                            SupprimerEtudiantController supprimerEtudiantController = fxmlLoader.getController();
                            supprimerEtudiantController.setEtudiantsController(EtudiantsController.this);
                            supprimerEtudiantController.setEtudiant(etudiant);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Supprimer etudiant");
                            stage.setScene(scene);
                            stage.setAlwaysOnTop(true);
                            stage.showAndWait();
                        } catch (RuntimeException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    setGraphic(hBox);
                    setText(null);
                }
            }
        });
        tableviewEtudiants.getColumns().add(columnAction);
        getEtudiantsListe();
    }

    public void getEtudiantsListe() throws SQLException {
//        tableviewEtudiants.getItems().clear();
        etudiants = etudiantDAO.getEtudiants();
        observableList = FXCollections.observableList(etudiants);
        tableviewEtudiants.setItems(observableList);
        filtreTableViewAbsence();
    }

    public void resizeTableView() {
        double totalWidth = tableviewEtudiants.getWidth();

        for (TableColumn column : tableviewEtudiants.getColumns()) {
            switch (column.getText()) {
                case "CNE":
                    column.setPrefWidth(totalWidth * 0.086);
                    break;
                case "Nom Prenom":
                    column.setPrefWidth(totalWidth * 0.1215);
                    break;
                case "Filiere":
                    column.setPrefWidth(totalWidth * 0.2064);
                    break;
                case "Classe":
                    column.setPrefWidth(totalWidth * 0.129);
                    break;
                case "Sexe":
                    column.setPrefWidth(totalWidth * 0.0753);
                    break;
                case "Telephone":
                    column.setPrefWidth(totalWidth * 0.0968);
                    break;
                case "Email":
                    column.setPrefWidth(totalWidth * 0.1849);
                    break;
                case "Action":
                    column.setPrefWidth(totalWidth * 0.1);
                    break;
            }
        }
    }

    public void filtreTableViewAbsence() {
        FilteredList<Etudiant> filteredList = new FilteredList(observableList, p -> true);
        tfFiltre.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(etudiant->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (etudiant.getCne().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if(etudiant.getNomEtudiant().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(etudiant.getPrenomEtudiant().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(etudiant.getEmail().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (etudiant.getNomClasse().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(etudiant.getSexe().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(etudiant.getNomFiliere().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(etudiant.getTelephone().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        tableviewEtudiants.setItems(filteredList);
    }

    @FXML
    void ajouter(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AjouterEtudiant.fxml"));
            Parent root = fxmlLoader.load();
            AjouterEtudiantsController ajouterEtudiantsController = fxmlLoader.getController();
            ajouterEtudiantsController.setEtudiantsController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter etudiant");
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
