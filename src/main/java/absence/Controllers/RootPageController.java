package absence.Controllers;

import absence.Dao.DatabaseConnection;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RootPageController {

    @FXML
    private Circle closeBtn;

    @FXML
    private Circle maximizeBtn;

    @FXML
    private GridPane topGrid;

    @FXML
    private Circle reduireBtn;

    @FXML
    private AnchorPane contenuPane;

    @FXML
    private HBox accueilAdimeBtn;

    @FXML
    private HBox accueilProfesseurBtn;

    @FXML
    private HBox adminsBtn;

    @FXML
    private HBox etudiantsBtn;

    @FXML
    private HBox deconnecterBtn;

    @FXML
    private HBox absenceAnterieurBtn;

    @FXML
    private HBox professeursBtn;

    @FXML
    private HBox noteAbsenceBtn;

    @FXML
    private Text nomPrenomUtilisateur;

    @FXML
    private HBox listemailBtn;

    @FXML
    private HBox avertissementbtn;

    @FXML
    private HBox historiqueAbsence;

    @FXML
    private HBox profileBtn;

    @FXML
    private VBox vboxLien;


    private double x=0,y=0;


    private  Utilisateur utilisateur;
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (utilisateur != null) {
            nomPrenomUtilisateur.setText(utilisateur.getPRENOM_USER().charAt(0) + "." + utilisateur.getNOM_USER());
            vboxLien.getChildren().remove(avertissementbtn);
        }
    }


    @FXML
    public void initialize() {
        try {
            UserAgentBuilder.builder().themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setResolveAssets(true).build().setGlobal();
            Node node = (Node) FXMLLoader.load(getClass().getResource("/View/AccueilAdmin.fxml"));
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    @FXML
    void allerVersAccueilAdmine(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            Node node = (Node) FXMLLoader.load(getClass().getResource("/View/AccueilAdmin.fxml"));
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersAccueilProfesseur(MouseEvent event) {
        try {
            UserAgentBuilder.builder()
                    .themes(JavaFXThemes.MODENA) // Passe null pour retirer le thème
                    .setDeploy(true)
                    .setResolveAssets(true)
                    .build()
                    .setGlobal();
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AccueilProfesseur.fxml"));
            Node node = (Node) fxmlLoader.load();
            AccueilProfesseurController accueilProfesseurController = fxmlLoader.getController();
            accueilProfesseurController.setUtilisateur(utilisateur);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersAdmins(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/Admins.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersNoteAbsence(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/NoteAbsence.fxml"));
            Node node = (Node) fxmlLoader.load() ;
            NoterAbsenceController noterAbsenceController = fxmlLoader.getController();
            noterAbsenceController.setUtilisateur(utilisateur);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersEtudiants(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Etudiants.fxml"));
            Node node = (Node) fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersProfesseur(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/Professeur.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersAbsenceAnterieur(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AbsenceAnterieur.fxml"));
            Node node = (Node) fxmlLoader.load() ;
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersAvertissement(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AVertissement.fxml"));
            Node node = (Node) fxmlLoader.load() ;
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersListEmails(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ListEmail.fxml"));
            Node node = (Node) fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersHistoriqueAbsence(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/HistoriqueAbsence.fxml"));
            Node node = (Node) fxmlLoader.load();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void allerVersProfile(MouseEvent event) {
        try {
            contenuPane.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Profile.fxml"));
            Node node = (Node) fxmlLoader.load();
            ProfileController profileController = fxmlLoader.getController();
            profileController.setUtilisateur(utilisateur);
            profileController.setRootPageController(this);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            contenuPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deconnecter(MouseEvent event) {
//        DatabaseConnection.deconnecter();
        try {
            Stage stage = (Stage) deconnecterBtn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/SeConnecter.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}