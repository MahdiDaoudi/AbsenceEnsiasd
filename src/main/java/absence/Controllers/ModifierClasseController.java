package absence.Controllers;

import absence.Dao.ClasseDAO;
import absence.Dao.FiliereDAO;
import absence.Modeles.Classe;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ModifierClasseController {
    private Classe classe;
    private ClasseDAO classeDAO;
    private FiliereDAO filiereDAO;
    private FilierEtClasseController filierEtClasseController;
    private ClasseCardController classeCardController;

    @FXML
    private MFXButton AnnulerClasseBtn;

    @FXML
    private Label classeNom;

    @FXML
    private MFXComboBox<String> filieres;

    @FXML
    private MFXButton modifierClasseBtn;

    @FXML
    private MFXTextField nomClasse;

    @FXML
    void OnAnnulerAjoute(ActionEvent event) {
        Stage stage = (Stage) AnnulerClasseBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnModifierClasse(ActionEvent event) throws SQLException {
        String filiereSelectionnee = filieres.getSelectionModel().getSelectedItem();
        classe = classeCardController.getCurrentClasse();
        Filiere currentFiliere=new Filiere();
        List<Filiere> toutFileres= filiereDAO.obtenirToutesLesFilieres();
        for(Filiere filiere : toutFileres) {
            if(filiere.getNomFiliere().equals(filiereSelectionnee)){
                currentFiliere = filiere;
            }
        }
        classe.setNomClasse(nomClasse.getText());
        classe.setIdFiliere(currentFiliere.getIdFiliere());

        classeDAO = new ClasseDAO();
        classeDAO.modifierClasse(classe);

        filierEtClasseController.getClasses().add(classe);
        filierEtClasseController.actualiserClasse();
        Stage stage = (Stage) modifierClasseBtn.getScene().getWindow();
        stage.close();
    }

    public void setData() throws SQLException {
        classe = classeCardController.getCurrentClasse();
        nomClasse.setText(classe.getNomClasse());
        classeNom.setText(classe.getNomClasse());
        filiereDAO = new FiliereDAO();
        Filiere currentFiliere=new Filiere();
        List<Filiere> toutFileres= filiereDAO.obtenirToutesLesFilieres();
        for(Filiere filiere : toutFileres) {
            filieres.getItems().add(filiere.getNomFiliere());
            if(filiere.getIdFiliere() == classe.getIdFiliere()){
                currentFiliere = filiere;
            }
        }
        filieres.getSelectionModel().selectItem(currentFiliere.getNomFiliere());


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