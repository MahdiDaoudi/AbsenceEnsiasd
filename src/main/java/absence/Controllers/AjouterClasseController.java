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
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.SQLException;
public class AjouterClasseController {
    private Classe classe;
    private ClasseDAO classeDAO;
    private FiliereDAO filiereDAO;
    private FilierEtClasseController filierEtClasseController;

    @FXML
    private MFXButton AnnulerClasseBtn;

    @FXML
    private MFXButton ajouterClasseBtn;

    @FXML
    private MFXComboBox<Filiere> filieres;


    @FXML
    private MFXTextField nomClasse;

    public void initialize() throws SQLException {
        filiereDAO = new FiliereDAO();
        filieres.getItems().addAll(filiereDAO.obtenirToutesLesFilieres());
        filieres.setConverter(new StringConverter<Filiere>() {
            @Override
            public String toString(Filiere filiere) {
                if(filiere != null){
                return filiere.getNomFiliere();
                }else {
                    return  null;
                }
            }

            @Override
            public Filiere fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void OnAjouterClasse(ActionEvent event) throws SQLException {
        Filiere filiereSelectionnee = filieres.getSelectionModel().getSelectedItem();
        classe = new Classe();
        classe.setNomClasse(nomClasse.getText());
        classe.setIdFiliere(filiereSelectionnee.getIdFiliere());

        classeDAO = new ClasseDAO();
        classeDAO.ajouterClasse(classe);

        filierEtClasseController.getClasses().add(classe);
        filierEtClasseController.actualiserClasse();

        Stage stage = (Stage) ajouterClasseBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnAnnulerAjoute(ActionEvent event) {
        Stage stage = (Stage) AnnulerClasseBtn.getScene().getWindow();
        stage.close();
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }
}

