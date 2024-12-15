package absence.Controllers;

import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private MFXTextField tfEmail;

    @FXML
    private MFXTextField tfNom;

    @FXML
    private MFXPasswordField tfPassword;

    @FXML
    private MFXTextField tfPrenom;

    @FXML
    private MFXTextField tfTelephone;

    private Utilisateur utilisateur;

    private UtilisateurDAO utilisateurDAO;

    private RootPageController rootPageController;

    public RootPageController getRootPageController() {
        return rootPageController;
    }

    public void setRootPageController(RootPageController rootPageController) {
        this.rootPageController = rootPageController;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (utilisateur != null) {
            setDataTextField();
        }
    }

    @FXML
    void initialize() {
    }

    @FXML
    void modifier(ActionEvent event) throws Exception {
        if (modifierBtn.getText().equals("Modifier")) {
            setTextFieldEditable(true);
            modifierBtn.setText("Sauvegarder");
        }else{
            setTextFieldEditable(false);
            utilisateur.setEMAIL(tfEmail.getText());
            utilisateur.setPASSWORD(tfPassword.getText());
            utilisateur.setNOM_USER(tfNom.getText());
            utilisateur.setTELEPHONE(tfTelephone.getText());
            utilisateur.setPRENOM_USER(tfPrenom.getText());
            utilisateurDAO = new UtilisateurDAO();
            utilisateurDAO.mettreAJourUtilisateur(utilisateur);
            utilisateur = utilisateurDAO.getUtilisateur(utilisateur.getID_User());
            rootPageController.setUtilisateur(utilisateur);
            modifierBtn.setText("Modifier");
            setDataTextField();
            Notification.getNotification("Information Bien Modifie");
        }
    }

    public void setTextFieldEditable(boolean editable) {
        tfNom.setEditable(editable);
        tfPrenom.setEditable(editable);
        tfTelephone.setEditable(editable);
        tfEmail.setEditable(editable);
        tfPassword.setEditable(editable);
    }

    public void setDataTextField(){
        tfNom.setText(utilisateur.getNOM_USER());
        tfEmail.setText(utilisateur.getEMAIL());
        tfPassword.setText(utilisateur.getPASSWORD());
        tfPrenom.setText(utilisateur.getPRENOM_USER());
        tfTelephone.setText(utilisateur.getTELEPHONE());
    }

}
