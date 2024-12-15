package absence.Controllers;

import absence.Dao.FiliereDAO;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SupprimerFilierController {
    private Filiere filiere;
    private FiliereDAO filiereDAO;
    private FilierCardController filierCardController;
    private FilierEtClasseController filierEtClasseController;
    @FXML
    private MFXButton AnnulerFiliereBtn;

    @FXML
    private Label nomFiliere;

    @FXML
    private MFXButton supprimerFiliereBtn;

    @FXML

    void OnAnnulerSuppretion(ActionEvent event) {
        Stage stage = (Stage) supprimerFiliereBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnSupprimerFilier(ActionEvent event) throws SQLException {
        filiereDAO = new FiliereDAO();
        filiereDAO.supprimerFiliere(filiere.getIdFiliere());
        filierEtClasseController.actualiserFiliere();
        Stage stage = (Stage) AnnulerFiliereBtn.getScene().getWindow();
        stage.close();
    }

    public  void setData() {
        filiere = filierCardController.getCurrentfiliere();
        nomFiliere.setText(filiere.getNomFiliere());

    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public FilierCardController getFilierCardController() {
        return filierCardController;
    }

    public void setFilierCardController(FilierCardController filierCardController) {
        this.filierCardController = filierCardController;
    }

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }
}
