package absence.Controllers;

import absence.Dao.FiliereDAO;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControllerModifierFiliere {
    private Filiere filiere;
    private FiliereDAO filiereDAO;
    private FilierEtClasseController filierEtClasseController;
    private FilierCardController filierCardController;

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }

    public FilierCardController getFilierCardController() {
        return filierCardController;
    }

    public void setFilierCardController(FilierCardController filierCardController) {
        this.filierCardController = filierCardController;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @FXML
    private MFXButton AnnulerFiliereBtn;

    @FXML
    private MFXButton modifierFiliereBtn;

    @FXML
    private MFXTextField nomFiliere;

    @FXML
    void OnAnnulerModification(ActionEvent event) {
        Stage stage = (Stage) modifierFiliereBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnMofifierFilier(ActionEvent event) throws SQLException {

        filiereDAO = new FiliereDAO();
        filiereDAO.modifierFiliere(filiere.getIdFiliere(),nomFiliere.getText());
        filierEtClasseController.getFilieres().add(filiere);
        filierEtClasseController.actualiserFiliere();
        Stage stage = (Stage) AnnulerFiliereBtn.getScene().getWindow();
        stage.close();
    }

    public  void setData() {
        filiere = filierCardController.getCurrentfiliere();
        nomFiliere.setText(filiere.getNomFiliere());
    }
}
