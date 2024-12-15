package absence.Controllers;

import absence.Dao.FiliereDAO;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AjouterFilierController {
    private Filiere filiere;
    private FiliereDAO filiereDAO;
    private FilierEtClasseController filierEtClasseController;

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }

    @FXML
    private MFXButton AnnulerFiliereBtn;

    @FXML
    private MFXButton ajouterFiliereBtn;

    @FXML
    private MFXTextField nomFiliere;

    @FXML
    void OnAjouterFilier(ActionEvent event) throws SQLException {
        filiere = new Filiere();
        filiereDAO = new FiliereDAO();
        filiereDAO.ajouterFiliere(nomFiliere.getText());
        filierEtClasseController.getFilieres().add(filiere);
        filierEtClasseController.actualiserFiliere();
        Stage stage = (Stage) ajouterFiliereBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnAnnulerAjoute(ActionEvent event) {
        Stage stage = (Stage) AnnulerFiliereBtn.getScene().getWindow();
        stage.close();
    }
}
