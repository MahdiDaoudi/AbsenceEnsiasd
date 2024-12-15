package absence.Controllers;


import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SupprimerProfController  {
    private ControllerboxProf controllerboxProf ;
    private UtilisateurDAO utilisateurDAO;
    private ProfController profController;
    private Utilisateur prof ;



    @FXML
    private MFXButton annuler;

    @FXML
    private Label nomProfesseur;

    @FXML
    private MFXButton supprimer;

    public ControllerboxProf getControllerboxProf() {
        return controllerboxProf;
    }

    public void setControllerboxProf(ControllerboxProf controllerboxProf) {
        this.controllerboxProf = controllerboxProf;
    }

    public Utilisateur getProf() {
        return prof;
    }

    public void setProf(Utilisateur prof) {
        this.prof = prof;
    }

    public ProfController getProfController() {
        return profController;
    }

    public void setProfController(ProfController profController) {
        this.profController = profController;
    }

    public void initData() {
        if(controllerboxProf != null) {
            prof = controllerboxProf.getCurrentProf();
            if(prof != null) {
                nomProfesseur.setText(prof.getNOM_USER()+" "+prof.getPRENOM_USER());
            }
        }
    }

    @FXML
    void OnSupprimer(ActionEvent event) {
        Utilisateur currentProf = controllerboxProf.getCurrentProf();
        System.out.println(currentProf.getNOM_USER());
        utilisateurDAO = new UtilisateurDAO();
        utilisateurDAO.supprimerUtilisateur(currentProf.getID_User());
//        profController.loadProfesseur();
        profController.actualiserProf();
        Stage stage = (Stage) supprimer.getScene().getWindow();
        stage.close();
    }

    @FXML
    void anuller(ActionEvent event) {
        Stage stage = (Stage)annuler.getScene().getWindow();
        stage.close();
    }
}
