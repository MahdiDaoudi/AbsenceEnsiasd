package absence.Controllers;
import absence.Dao.UtilisateurDAO;
import absence.Modeles.Utilisateur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerModifierAdmin {
    private ControllerBoxAdmin controllerBoxAdmin ;
    private UtilisateurDAO utilisateurDAO;
    private AdminsController adminsController;
    private Utilisateur admin ;


    @FXML
    private MFXButton ajouter;

    @FXML
    private MFXButton anuler;

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
    void AnulerLamodification(ActionEvent event) {
    Stage stage = (Stage) anuler.getScene().getWindow();
    stage.close();
    }

    @FXML
    void ModifierAdmin(ActionEvent event) {
        if(!Validation.valider(nomAdmin,prenomAdmin,telephoneAdmin,emailAdmin,erreur)) {
            return;
        }
        Utilisateur admin = controllerBoxAdmin.getCurrentAdmin();
        System.out.println(admin.getID_User()+" "+admin.getPRENOM_USER());
        utilisateurDAO = new UtilisateurDAO();
        admin.setPRENOM_USER(prenomAdmin.getText());
        admin.setTELEPHONE(telephoneAdmin.getText());
        admin.setNOM_USER(nomAdmin.getText());
        admin.setEMAIL(emailAdmin.getText());
        utilisateurDAO.mettreAJourUtilisateur(admin);
        adminsController.actualiserAdmin();
        Stage stage = (Stage)anuler.getScene().getWindow();
        stage.close();

    }
//    public boolean validation() {
//        StringBuilder erreurs = new StringBuilder();
//        String nom = nomAdmin.getText().trim();
//        String prenom = prenomAdmin.getText().trim();
//        String email = emailAdmin.getText().trim();
//        String telephone = telephoneAdmin.getText().trim();
//
//        if (nom.isEmpty()) {
//            erreurs.append("Le champ 'Nom' est obligatoire.\n");
//        }
//        if (prenom.isEmpty()) {
//            erreurs.append("Le champ 'Prénom' est obligatoire.\n");
//        }
//        if (email.isEmpty()) {
//            erreurs.append("Le champ 'Email' est obligatoire.\n");
//        } else if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
//            erreurs.append("Le champ 'Email' n'est pas valide.\n");
//        }
//        if (telephone.isEmpty()) {
//            erreurs.append("Le champ 'Téléphone' est obligatoire.\n");
//        } else if (!telephone.matches("^\\d{10}$")) {
//            erreurs.append("Le champ 'Téléphone' doit contenir 10 chiffres.\n");
//        }
//        if (erreurs.length() > 0) {
//            erreur.setText(erreurs.toString());
//            return false;
//        }
//        erreur.setText("");
//        return true;
//    }
    public void setData() {
        admin = controllerBoxAdmin.getCurrentAdmin();
        nomAdmin.setText(admin.getNOM_USER());
        prenomAdmin.setText(admin.getPRENOM_USER());
        telephoneAdmin.setText(admin.getTELEPHONE());
        emailAdmin.setText(admin.getEMAIL());
    }


    public Utilisateur getAdmin() {
        return admin;
    }

    public void setAdmin(Utilisateur admin) {
        this.admin = admin;
    }

    public ControllerBoxAdmin getControllerBoxAdmin() {
        return controllerBoxAdmin;
    }

    public void setControllerBoxAdmin(ControllerBoxAdmin controllerBoxAdmin) {
        this.controllerBoxAdmin = controllerBoxAdmin;
    }

    public AdminsController getAdminsController() {
        return adminsController;
    }

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }




}
