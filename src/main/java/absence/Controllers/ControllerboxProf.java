package absence.Controllers;
import absence.Modeles.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerboxProf {
    private Utilisateur currentProf;

    public ProfController getProfController() {
        return profController;
    }

    public void setProfController(ProfController profController) {
        this.profController = profController;
    }

    private ProfController profController;


    @FXML
    private VBox boxProf;

    @FXML
    private ImageView imageProf;


    @FXML
    private ImageView deleteProf;


    @FXML
    private Label emailProf;

    @FXML
    private Label nomProf;

    @FXML
    private Label phoneProf;


    public void setData(Utilisateur prof) {

        this.nomProf.setText(prof.getNOM_USER()+" "+prof.getPRENOM_USER());
        this.emailProf.setText(prof.getEMAIL());
        this.phoneProf.setText(prof.getTELEPHONE());
        currentProf = prof;

    }

    @FXML
    private void onImageClick(MouseEvent event) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/modifierProf.fxml"));
                Parent root = fxmlLoader.load();
                ControllerModifierProf controllerModifierProf = fxmlLoader.getController();
                controllerModifierProf.setControllerboxProf(this);
                controllerModifierProf.setProfController(profController);
                controllerModifierProf.initData();
                Scene modifierProf = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(modifierProf);
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setAlwaysOnTop(true);
                stage.setUserData(currentProf);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void onDeleteProf(MouseEvent event) {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SupprimerProf.fxml"));
        Parent root = fxmlLoader.load();
        SupprimerProfController supprimerProfController = fxmlLoader.getController();
        supprimerProfController.setControllerboxProf(this);
        supprimerProfController.setProfController(profController);
        supprimerProfController.initData();
        Scene supprimerProf = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(supprimerProf);
        stage.showAndWait();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Utilisateur getCurrentProf() {
        return currentProf;
    }

}
