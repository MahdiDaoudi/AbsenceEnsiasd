package absence.Controllers;

import absence.Modeles.Classe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class ClasseCardController {
    private FilierEtClasseController filierEtClasseController;
    private Classe currentClasse;

    public FilierEtClasseController getFilierEtClasseController() {
        return filierEtClasseController;
    }

    public void setFilierEtClasseController(FilierEtClasseController filierEtClasseController) {
        this.filierEtClasseController = filierEtClasseController;
    }

    public Classe getCurrentClasse() {
        return currentClasse;
    }

    public void setCurrentClasse(Classe currentClasse) {
        this.currentClasse = currentClasse;
    }


    @FXML
    private ImageView modifierClasse;

    @FXML
    private Label nomClasse;

    @FXML
    private ImageView supprimerClasse;

    @FXML
    void OnModifierClasse(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ModifierClasse.fxml"));
            Parent root = fxmlLoader.load();
            ModifierClasseController modifierClasseController = fxmlLoader.getController();
            modifierClasseController.setClasseCardController(this);
            modifierClasseController.setFilierEtClasseController(filierEtClasseController);
            modifierClasseController.setData();
            Scene modifierAdmin = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(modifierAdmin);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.setUserData(currentClasse);
            stage.showAndWait();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void OnSupprimerClasse(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SupprimerClasse.fxml"));
            Parent root = fxmlLoader.load();
            SupprimerClasseController supprimerClasseController = fxmlLoader.getController();
            supprimerClasseController.setClasseCardController(this);
            supprimerClasseController.setFilierEtClasseController(filierEtClasseController);
            supprimerClasseController.setData();
            Scene modifierAdmin = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(modifierAdmin);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setAlwaysOnTop(true);
            stage.setUserData(currentClasse);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void setData(Classe classe){
        nomClasse.setText(classe.getNomClasse());
        currentClasse = classe;
    }
}
