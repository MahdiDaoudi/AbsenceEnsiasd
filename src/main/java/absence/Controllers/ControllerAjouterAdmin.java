package absence.Controllers;
import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAjouterAdmin {
    private Utilisateur admin ;
    private AdminsController  adminsController ;
    private UtilisateurDAO utilisateurDAO;

    public AdminsController getAdminsController() {
        return adminsController;
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    @FXML
    private MFXButton ajouter;

    @FXML
    private MFXButton annuler;

    @FXML
    private TextField emailAdmin;

    @FXML
    private TextField nomAdmin;

    @FXML
    private TextField prenomAdmin;

    @FXML
    private TextField telephoneAdmin;

    @FXML
    private Label erreur;

    @FXML
    void AjouterAdmin(ActionEvent event) throws Exception {
        if (!Validation.valider(nomAdmin,prenomAdmin,telephoneAdmin,emailAdmin,erreur)) {
            return;
        }
        admin = new Utilisateur();
        admin.setNOM_USER(nomAdmin.getText());
        admin.setPRENOM_USER(prenomAdmin.getText());
        admin.setEMAIL(emailAdmin.getText());
        admin.setTELEPHONE(telephoneAdmin.getText());

        String nom = nomAdmin.getText();
        int randomNumber = (int) (Math.random() * 1000);
        String randomCharacters = generateRandomCharacters(5);
        String password = nom.substring(0, Math.min(3, nom.length())) + randomNumber + randomCharacters;
        admin.setPASSWORD(AESUtil.encrypt(password));
        System.out.println(password);
        admin.setROLE("ADMIN");
        utilisateurDAO = new UtilisateurDAO();
        utilisateurDAO.creerUtilisateur(admin);
        adminsController.getAdmin().add(admin);
        adminsController.actualiserAdmin();
        Stage stage = (Stage) ajouter.getScene().getWindow();
        stage.close();

    }

    @FXML
    void AnnulerLajoute(ActionEvent event) {
    Stage stage = (Stage) annuler.getScene().getWindow();
    stage.close();
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
