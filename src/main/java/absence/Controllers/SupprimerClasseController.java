package absence.Controllers;

import absence.Dao.ClasseDAO;
import absence.Modeles.Classe;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SupprimerClasseController {
    private Classe classe;
    private ClasseDAO classeDAO;
    private ClasseCardController classeCardController;
    private FilierEtClasseController filierEtClasseController;
    @FXML
    private MFXButton AnnulerClasseBtn;

    @FXML
    private Label nomClasse;

    @FXML
    private MFXButton supprimerClasseBtn;

    @FXML
    void OnAnnulerSuppretion(ActionEvent event)  {

        Stage stage = (Stage) AnnulerClasseBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnSupprimerClasse(ActionEvent event) throws SQLException{
        classe = classeCardController.getCurrentClasse();
        classeDAO = new ClasseDAO();
        classeDAO.supprimerClasse(classe.getIdClasse());
        filierEtClasseController.actualiserClasse();
        Stage stage = (Stage) supprimerClasseBtn.getScene().getWindow();
        stage.close();
    }

    public void setData() {
        classe = classeCardController.getCurrentClasse();
        nomClasse.setText(classe.getNomClasse());
    }

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }

    public ClasseCardController getClasseCardController() {
        return classeCardController;
    }

    public void setClasseCardController(ClasseCardController classeCardController) {
        this.classeCardController = classeCardController;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
}
