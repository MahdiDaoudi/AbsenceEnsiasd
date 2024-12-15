package absence.Controllers;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminsController implements Initializable {
    private List<Utilisateur> admins;

    @FXML
    private MFXButton ajouterAdminBtn;

    @FXML
    private FlowPane adminLayout;



    UtilisateurDAO utilisateurDAO;

    @Override
    public void initialize(URL location , ResourceBundle resources) {
        loadAdmins();
        adminLayout.getChildren().clear();
        actualiserAdmin();

    }

    public void actualiserAdmin() {
        adminLayout.getChildren().clear();
        loadAdmins();
        try {
            for(int i=0;i<admins.size();i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminCard.fxml"));
                Parent adminCard = loader.load();
                ControllerBoxAdmin controllerBoxAdmin = loader.getController();
                controllerBoxAdmin.setAdminsController(this);
                ControllerBoxAdmin controllerBox = loader.getController();
                controllerBox.setData(admins.get(i));
                adminLayout.getChildren().add(adminCard);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAdmins() {
        utilisateurDAO = new UtilisateurDAO();
        admins = utilisateurDAO.getAdmins();

    }

    @FXML
    void ajouterAdmin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AjouterAdmin.fxml"));
        Parent root = fxmlLoader.load();
        ControllerAjouterAdmin controllerAjouterAdmin = fxmlLoader.getController();
        controllerAjouterAdmin.setAdminsController(this);
        Scene ajouterAdminScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(ajouterAdminScene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();

    }


    public List<Utilisateur> getAdmin() {
        return admins;
    }

}
