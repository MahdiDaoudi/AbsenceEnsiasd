package absence;

import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class AbsenceApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserAgentBuilder.builder().themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setDebug(true).setResolveAssets(true).build().setGlobal();
        FXMLLoader fxmlLoader = new FXMLLoader(AbsenceApp.class.getResource("/View/rootPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}