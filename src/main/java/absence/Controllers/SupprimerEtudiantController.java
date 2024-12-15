package absence.Controllers;

import absence.Dao.EtudiantDAO;
import absence.Modeles.Etudiant;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerEtudiantController {

    @FXML
    private MFXButton annulerBtn;

    @FXML
    private MFXButton supprimerBtn;

    @FXML
    private Label labelInformation;

    private Etudiant etudiant;

    private EtudiantsController etudiantsController;

    @FXML
    void initialize() {

    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        if (etudiant != null) {
            labelInformation.setText("Êtes-vous sûr de vouloir supprimer "+etudiant.getNomEtudiant()+" "+etudiant.getPrenomEtudiant()+" ?");
        }
    }

    public EtudiantsController getEtudiantsController() {
        return etudiantsController;
    }

    public void setEtudiantsController(EtudiantsController etudiantsController) {
        this.etudiantsController = etudiantsController;
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void supprimer(ActionEvent event) throws SQLException, IOException {
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        etudiantDAO.supprimerEtudiant(etudiant.getCne());
        etudiantsController.getEtudiantsListe();
        Stage stage = (Stage) supprimerBtn.getScene().getWindow();
        stage.close();
        Notification.getNotification("L'étudiant a été supprimé avec succès.");
    }

}