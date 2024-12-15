package absence.Controllers;

import absence.AbsenceApp;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Notification {
    public static void getNotification(String message) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Notification.class.getResource("/View/Notification.fxml"));
        Parent root = fxmlLoader.load();
        NotificationController notificationController = fxmlLoader.getController();
        notificationController.getLabelNotification().setText(message);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(AbsenceApp.class.getResourceAsStream("/asset/Untitled-1.png")));
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setX(javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() - 407);
        stage.setY(20);
        stage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(event -> stage.close());
        delay.play();
    }
}
