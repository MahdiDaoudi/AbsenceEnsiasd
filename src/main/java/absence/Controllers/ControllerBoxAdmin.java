package absence.Controllers;

import absence.Modeles.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerBoxAdmin {

    private Utilisateur currentAdmin;
    private AdminsController adminsController;

    public AdminsController getAdminsController() {
        return adminsController;
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    @FXML
    private VBox boxProf;

    @FXML
    private ImageView deleteAdmin;

    @FXML
    private ImageView editAdmin;

    @FXML
    private Label emailAdmin;

    @FXML
    private Label nomAdmin;

    @FXML
    private Label phoneAdmin;



    public void setData(Utilisateur admin) {

        this.nomAdmin.setText(admin.getNOM_USER()+" "+admin.getPRENOM_USER());
        this.emailAdmin.setText(admin.getEMAIL());
        this.phoneAdmin.setText(admin.getTELEPHONE());
        currentAdmin = admin;

    }

    @FXML
    private void onEditAdmin(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/modifierAdmin.fxml"));
            Parent root = fxmlLoader.load();
            ControllerModifierAdmin controllerModifierAdmin = fxmlLoader.getController();
            controllerModifierAdmin.setControllerBoxAdmin(this);
            controllerModifierAdmin.setAdminsController(adminsController);
            controllerModifierAdmin.setData();
            Scene modifierAdmin = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(modifierAdmin);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.setUserData(currentAdmin);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteAdmin(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SupprimerAdmin.fxml"));
            Parent root = fxmlLoader.load();
            SupprimerAdminController supprimerAdminController = fxmlLoader.getController();
            supprimerAdminController.setControllerBoxAdmin(this);
            supprimerAdminController.setAdminsController(adminsController);
            supprimerAdminController.initData();
            Scene supprimerProf = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(supprimerProf);
            stage.showAndWait();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Utilisateur getCurrentAdmin() {
        return currentAdmin;
    }

}
