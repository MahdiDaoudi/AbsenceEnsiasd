package absence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificationController {

    @FXML
    private Label labelNotification;

    public Label getLabelNotification() {
        return labelNotification;
    }

    public void setLabelNotification(Label labelNotification) {
        this.labelNotification = labelNotification;
    }
}