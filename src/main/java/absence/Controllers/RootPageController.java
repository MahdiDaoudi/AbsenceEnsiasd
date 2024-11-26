package absence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RootPageController {

    @FXML
    private Circle closeBtn;

    @FXML
    private Circle maximizeBtn;

    @FXML
    private GridPane topGrid;

    @FXML
    private Circle reduireBtn;

    private double x=0,y=0;

    @FXML
    void Close(MouseEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void Maximize(MouseEvent event) {
        Stage stage = (Stage) maximizeBtn.getScene().getWindow();
        if(stage.isMaximized()) {
            stage.setMaximized(false);
        }else{
            stage.setMaximized(true);
        }

    }

    @FXML
    void Reduire(MouseEvent event) {
        Stage stage = (Stage) reduireBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getScreenX();
        y = event.getScreenY();
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) topGrid.getScene().getWindow();
        stage.setX(event.getScreenX() - x + stage.getX());
        stage.setY(event.getScreenY() - y + stage.getY());
        x = event.getScreenX();
        y = event.getScreenY();
    }


}