package absence.Controllers;

import absence.Modeles.Filiere;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FilierCardController {
    private Filiere currentfiliere ;
    private FilierEtClasseController filierEtClasseController;

    public Filiere getCurrentfiliere() {
        return currentfiliere;
    }

    public void setCurrentfiliere(Filiere currentfiliere) {
        this.currentfiliere = currentfiliere;
    }

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }

    @FXML
    private AnchorPane editFilier;

    @FXML
    private Label nomFilier;

    @FXML
    private ImageView supprimerFilier;

    @FXML
    void onEditerFilier(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ModifierFiliere.fxml"));
            Parent root = fxmlLoader.load();
            ControllerModifierFiliere controllerModifierFiliere = fxmlLoader.getController();
            controllerModifierFiliere.setFilierCardController(this);
            controllerModifierFiliere.setFilierEtClasseController(filierEtClasseController);
            controllerModifierFiliere.setData();
            Scene modifierAdmin = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(modifierAdmin);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.setUserData(currentfiliere);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(Filiere filiere){
        nomFilier.setText(filiere.getNomFiliere());
        currentfiliere = filiere;
    }


    @FXML
    void OnSupprimerFilier(MouseEvent event) {

     try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SupprimerFiliere.fxml"));
        Parent root = fxmlLoader.load();
        SupprimerFilierController supprimerFilierController = fxmlLoader.getController();
        supprimerFilierController.setFilierCardController(this);
         supprimerFilierController.setFilierEtClasseController(filierEtClasseController);
        supprimerFilierController.setData();
        Scene supprimerProf = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(supprimerProf);
        stage.showAndWait();
    }catch (IOException e) {
        e.printStackTrace();
    }
    }

}
