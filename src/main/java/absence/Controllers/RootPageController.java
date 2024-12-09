package absence.Controllers;

import absence.Dao.DatabaseConnection;
import absence.Modeles.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private HBox accueilBtn;

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

    private double x=0,y=0;


    private  Utilisateur utilisateur;
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (utilisateur != null) {
            nomPrenomUtilisateur.setText(utilisateur.getPRENOM_USER().charAt(0) + "." + utilisateur.getNOM_USER());
        }
    }


    @FXML
    public void initialize() {
        try {
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
    void allerVersAccueil(MouseEvent event) {
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
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/Etudiants.fxml")));
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
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/AVertissement.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void allerVersListEmails(MouseEvent event) {
        contenuPane.getChildren().clear();
        try {
            contenuPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/View/ListEmail.fxml")));
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