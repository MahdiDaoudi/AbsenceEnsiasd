package absence.Controllers;

import absence.Dao.DatabaseConnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class RootPageController {

    @FXML
    private Circle closeBtn;

    @FXML
    private Circle maximizeBtn;

    @FXML
    private GridPane topGrid;

    @FXML
    private Circle reduireBtn;

    @FXML
    private AnchorPane contenuPane;

    @FXML
    private HBox accueilBtn;

    @FXML
    private HBox adminsBtn;


    @FXML
    private HBox classeEtFilier;

    @FXML
    private HBox etudiantsBtn;

    @FXML
    private HBox professeursBtn;
    @FXML private MFXButton seDeconnecter;

    private double x=0,y=0;

    @FXML
    void Close(MouseEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Maximize(MouseEvent event) {
        Stage stage = (Stage) maximizeBtn.getScene().getWindow();
        if(stage.isMaximized()) {
            stage.setMaximized(false);
        }else{
            stage.setMaximized(true);
        }

    }

    @FXML
    void Reduire(MouseEvent event) {
        Stage stage = (Stage) reduireBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getScreenX();
        y = event.getScreenY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) topGrid.getScene().getWindow();
        stage.setX(event.getScreenX() - x + stage.getX());
        stage.setY(event.getScreenY() - y + stage.getY());
        x = event.getScreenX();
        y = event.getScreenY();
    }

    @FXML
    void allerVersAccueil(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            Node node = (Node) FXMLLoader.load(getClass().getResource("/View/AccueilAdmin.fxml"));
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersAdmins(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("/View/Admins.fxml"));
            AnchorPane.setTopAnchor(node,0.0);
            AnchorPane.setRightAnchor(node,0.0);
            AnchorPane.setLeftAnchor(node,0.0);
            AnchorPane.setBottomAnchor(node,0.0);
            contenuPane.getChildren().setAll(node );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersEtudiants(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/Etudiants.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void allerVersProfesseur(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Professeur.fxml"));
            Parent root = fxmlLoader.load();
//            ProfController profController= fxmlLoader.getController();
//            profController.setProfController(profController);
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setBottomAnchor(root,0.0);
            AnchorPane.setLeftAnchor(root,0.0);
            AnchorPane.setRightAnchor(root,0.0);

            contenuPane.getChildren().setAll(root) ;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void allerVersClasseEtFilier(MouseEvent event) {
    contenuPane.getChildren().clear();
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/FilierEtClasse.fxml"));
        Parent root = fxmlLoader.load();
        AnchorPane.setTopAnchor(root,0.0);
        AnchorPane.setBottomAnchor(root,0.0);
        AnchorPane.setLeftAnchor(root,0.0);
        AnchorPane.setRightAnchor(root,0.0);
        contenuPane.getChildren().setAll(root);
    }catch(IOException e) {

    }
    }

    public void deconnecter(ActionEvent actionEvent) throws IOException {
        DatabaseConnection.deconnecter();
        Stage currentStage = (Stage) seDeconnecter.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SeConnecter.fxml"));
        currentStage.setScene(fxmlLoader.load());
    }
}