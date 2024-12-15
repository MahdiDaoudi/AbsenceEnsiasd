package absence.Controllers;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SupprimerAdminController {

    private ControllerBoxAdmin controllerBoxAdmin ;
    private UtilisateurDAO utilisateurDAO;
    private AdminsController adminsController;
    private Utilisateur admin ;



    @FXML
    private MFXButton annuler;

    @FXML
    private Label nomAdmin;

    @FXML
    private MFXButton supprimer;

    public ControllerBoxAdmin getControllerBoxAdmin() {
        return controllerBoxAdmin;
    }

    public void setControllerBoxAdmin(ControllerBoxAdmin controllerBoxAdmin) {
        this.controllerBoxAdmin = controllerBoxAdmin;
    }

    public Utilisateur getAdmin() {
        return admin;
    }

    public void setAdmin(Utilisateur admin) {
        this.admin = admin;
    }

    public AdminsController getAdminsController() {
        return adminsController;
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    public void initData() {
        if(controllerBoxAdmin != null) {
            admin = controllerBoxAdmin.getCurrentAdmin();
            if(admin != null) {
                nomAdmin.setText(admin.getNOM_USER()+" "+admin.getPRENOM_USER());
            }
        }
    }

    @FXML
    void OnSupprimer(ActionEvent event) {
        Utilisateur currentAdmin = controllerBoxAdmin.getCurrentAdmin();
        System.out.println(currentAdmin.getNOM_USER());
        utilisateurDAO = new UtilisateurDAO();
        utilisateurDAO.supprimerUtilisateur(currentAdmin.getID_User());
//        profController.loadProfesseur();
        adminsController.actualiserAdmin();
        Stage stage = (Stage) supprimer.getScene().getWindow();
        stage.close();
    }

    @FXML
    void anuller(ActionEvent event) {
        Stage stage = (Stage)annuler.getScene().getWindow();
        stage.close();
    }
}
