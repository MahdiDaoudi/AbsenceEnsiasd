package absence.Controllers;
import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.showMessageDialog;

public class ControllerAjouterProf {
    private Utilisateur prof ;
    private ProfController  controlerProf ;
    private UtilisateurDAO utilisateurDAO;
    public ProfController getControlerProf() {
        return controlerProf;
    }


    public void setControlerProf(ProfController controlerProf) {
        this.controlerProf = controlerProf;
    }

    @FXML
    private MFXButton ajouter;

    @FXML
    private HBox ajouterPhotobtn;

    @FXML
    private MFXButton anuler;

    @FXML
    private TextField cnfFill;

    @FXML
    private TextField emailFill;

    @FXML
    private TextField nomFill;

    @FXML
    private TextField prenomFill;

    @FXML
    private TextField telephoneFill;

    @FXML
    private Label erreur;
    @FXML
    void AjouterProf(ActionEvent event) throws Exception {
        if(!Validation.valider(nomFill,prenomFill,telephoneFill,emailFill,erreur)) {
            return;
        }
        prof = new Utilisateur();

        prof.setNOM_USER(nomFill.getText());
        prof.setPRENOM_USER(prenomFill.getText());
        prof.setEMAIL(emailFill.getText());
        prof.setTELEPHONE(telephoneFill.getText());
        String nom = nomFill.getText();
        int randomNumber = (int) (Math.random() * 1000);
        String randomCharacters = generateRandomCharacters(5);
        String password = nom.substring(0, Math.min(3, nom.length())) + randomNumber + randomCharacters;
        prof.setPASSWORD(AESUtil.encrypt(password));
        prof.setROLE("PROFESSEUR");
        utilisateurDAO = new UtilisateurDAO();
        utilisateurDAO.creerUtilisateur(prof);
        controlerProf.getProfesseur().add(prof);
        controlerProf.actualiserProf();

        Stage stage = (Stage)ajouter.getScene().getWindow();
        stage.close();


    }

    @FXML
    void AnulerLajoute(ActionEvent event) {
        Stage stage = (Stage)anuler.getScene().getWindow();
        stage.close();
    }
    public Utilisateur getProf() {
        return prof;
    }

    public void setProf(Utilisateur prof) {
        this.prof = prof;
    }
    private String generateRandomCharacters(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@.-_";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

}
