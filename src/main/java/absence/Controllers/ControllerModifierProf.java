package absence.Controllers;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerModifierProf {
    private ControllerboxProf controllerboxProf ;
    private UtilisateurDAO utilisateurDAO;
    private ProfController profController;
    private Utilisateur prof ;

    public ProfController getProfController() {
        return profController;
    }

    public void setProfController(ProfController profController) {
        this.profController = profController;
    }


    public Utilisateur getProf() {
        return prof;
    }

    public void setProf(Utilisateur prof) {
        this.prof = prof;
    }

    @FXML
    private MFXButton ajouter;

    @FXML
    private HBox ajouterPhotobtn;

    @FXML
    private MFXButton anuler;

    @FXML
    private TextField emailFill;

    @FXML
    private TextField nomFill;

    @FXML
    private TextField prenomFill;

    @FXML
    private TextField telephoneFill;

    @FXML
    private Label erreur;

    @FXML
    void AnulerLamodification(ActionEvent event) {
    Stage stage = (Stage)ajouter.getScene().getWindow();
    stage.close();
    }

    @FXML
    void ModifierProf(ActionEvent event) {
        if(!Validation.valider(nomFill,prenomFill,telephoneFill,emailFill,erreur)){
            return;
        }
        Utilisateur prof = controllerboxProf.getCurrentProf();
        System.out.println(prof.getID_User()+" "+prof.getPRENOM_USER());
        utilisateurDAO = new UtilisateurDAO();
        prof.setPRENOM_USER(prenomFill.getText());
        prof.setTELEPHONE(telephoneFill.getText());
        prof.setNOM_USER(nomFill.getText());
        prof.setEMAIL(emailFill.getText());
        utilisateurDAO.mettreAJourUtilisateur(prof);
        profController.actualiserProf();
        Stage stage = (Stage)anuler.getScene().getWindow();
        stage.close();
    }

    public void initData() {
        if (controllerboxProf != null) {
            prof = controllerboxProf.getCurrentProf();
            if (prof != null) {
                nomFill.setText(prof.getNOM_USER());
                emailFill.setText(prof.getEMAIL());
                prenomFill.setText(prof.getPRENOM_USER());
                telephoneFill.setText(prof.getTELEPHONE());
            } else {
                System.out.println("Prof is null!");
            }
        } else {
            System.out.println("ControllerboxProf is null!");
        }
    }


    public ControllerboxProf getControllerboxProf() {
        return controllerboxProf;
    }

    public void setControllerboxProf(ControllerboxProf controllerboxProf) {
        this.controllerboxProf = controllerboxProf;
    }
}
