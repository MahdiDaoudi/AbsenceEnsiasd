package absence.Controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class SeConnecterController {
    @FXML private Circle closeBtn;

    @FXML private Pane paneTop;

    @FXML private Circle reduirBtn;

    @FXML private MFXButton seConnecterBtn;

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
        Stage stage = (Stage) seConnecterBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/rootPage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setX(10);
            stage.setY(10);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void reinitialiserMotsdePass(ActionEvent event) {

    }
}
