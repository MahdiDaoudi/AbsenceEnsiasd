package absence.Controllers;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class SeConnecterController {
    @FXML private Circle closeBtn;

    @FXML private Pane paneTop;

    @FXML private Circle reduirBtn;

    @FXML private MFXButton seConnecterBtn;
    @FXML private MFXTextField emailField;
    @FXML private MFXPasswordField passwordField;
    private UtilisateurDAO utilisateurDAO=new UtilisateurDAO();

    private double x=0,y=0;

    @FXML
    void Close(MouseEvent event) {
        Stage stage= (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Reduire(MouseEvent event) {
        Stage stage = (Stage) reduirBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) paneTop.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }
    @FXML
    void connecter(ActionEvent event) {
//        String email = emailField.getText();
//        String password = passwordField.getText();
        String email = "me@gmail.com";
        String password = "2004";
        Utilisateur utilisateur = utilisateurDAO.verifierLogin(email, password);

        if (utilisateur != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/rootPage.fxml"));
                Parent root = fxmlLoader.load();
                RootPageController rootPageController = fxmlLoader.getController();
                rootPageController.setUtilisateur(utilisateur);
                Stage stage = (Stage) seConnecterBtn.getScene().getWindow();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.setX(10);
                stage.setY(10);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Connexion");
            alert.showAndWait();
        }
    }

    @FXML
    void reinitialiserMotsdePass(ActionEvent event) {

    }
}
