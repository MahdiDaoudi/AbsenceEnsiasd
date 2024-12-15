package absence.Controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

public class ProfController implements  Initializable{
//    private  ProfController profController;

//    public ProfController getProfController() {
//        return profController;
//    }
//
//    public void setProfController(ProfController profController) {
//        this.profController = profController;
//    }

    @FXML
    private MFXButton ajouterProfBtn;

    @FXML
    private FlowPane profLayout;



    UtilisateurDAO utilisateurDAO;
    private List<Utilisateur> professeurs;

    @Override
    public void initialize(URL location , ResourceBundle resources) {
//        loadProfesseur();
        profLayout.getChildren().clear();
        actualiserProf();

    }

    public void actualiserProf() {
        profLayout.getChildren().clear();
        loadProfesseur();
        try {
            for(int i=0;i<professeurs.size();i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/profCard.fxml"));
                Parent profCard = loader.load();
                ControllerboxProf controllerboxProf = loader.getController();
                controllerboxProf.setProfController(this);
                ControllerboxProf controlerBox = loader.getController();
                controlerBox.setData(professeurs.get(i));
                profLayout.getChildren().add(profCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProfesseur() {
        utilisateurDAO = new UtilisateurDAO();
        professeurs = utilisateurDAO.getProfesseurs();

    }

    @FXML
    void ajouterProf(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ajouterProf.fxml"));
        Parent root = fxmlLoader.load();
        ControllerAjouterProf controllerAjouterProf = fxmlLoader.getController();
        controllerAjouterProf.setControlerProf(this);
        Scene ajouterProfScene =  new Scene(root);
        Stage stage = new Stage();
        stage.setScene(ajouterProfScene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();

    }


    public List<Utilisateur> getProfesseur() {
        return professeurs;
    }

}
