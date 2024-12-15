package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Dao.ClasseDAO;
import absence.Dao.EtudiantDAO;
import absence.Dao.FiliereDAO;
import absence.Modeles.AbsenceTableView;
import absence.Modeles.Classe;
import absence.Modeles.Etudiant;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModifierEtudiantController {

    @FXML
    private MFXButton annulerBtn;

    @FXML
    private MFXComboBox<String> comboBoxClasse;

    @FXML
    private MFXComboBox<String> comboBoxSexe;

    @FXML
    private MFXComboBox<String> comboBoxFiliere;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private MFXTextField tfCne;

    @FXML
    private MFXTextField tfEmail;

    @FXML
    private MFXTextField tfNom;

    @FXML
    private MFXTextField tfPrenom;

    @FXML
    private MFXTextField tfTelephone;

    @FXML
    private Label classeEreurLabel;

    @FXML
    private Label cneEreurLabel;

    @FXML
    private Label emailEreurLabel;

    @FXML
    private Label filiereEreurLabel;

    @FXML
    private Label nomEreurLabel;

    @FXML
    private Label prenomEreurLabel;

    @FXML
    private Label sexeEreurLabel;

    @FXML
    private Label telephoneEreurLabel;

    private Etudiant etudiant;
    private EtudiantsController etudiantsController;
    private List<Classe> classes;
    private List<Filiere> filieres;

    private ClasseDAO classeDAO;
    private FiliereDAO filiereDAO;


    public EtudiantsController getEtudiantsController() {
        return etudiantsController;
    }

    public void setEtudiantsController(EtudiantsController etudiantsController) {
        this.etudiantsController = etudiantsController;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) throws SQLException {
        this.etudiant = etudiant;
        if (etudiant != null) {
            tfCne.setText(etudiant.getCne());
            tfEmail.setText(etudiant.getEmail());
            tfNom.setText(etudiant.getNomEtudiant());
            tfPrenom.setText(etudiant.getPrenomEtudiant());
            tfTelephone.setText(etudiant.getTelephone());
            comboBoxSexe.getItems().setAll("Homme", "Femme");
            comboBoxSexe.getSelectionModel().selectItem(etudiant.getSexe());
            getFilieres();
            getClasse();
        }
    }

    @FXML
    void initialize() throws SQLException {
        filierChange();
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException, IOException {
        renitializerEreurLabel();

        boolean ereurExiste = false;

        if (tfCne.getText().trim().isEmpty()) {
            cneEreurLabel.setText("Le CNE est obligatoire !");
            ereurExiste = true;
        }

        if (tfNom.getText().trim().isEmpty()) {
            nomEreurLabel.setText("Le nom est obligatoire !");
            ereurExiste = true;
        }

        if (tfPrenom.getText().trim().isEmpty()) {
            prenomEreurLabel.setText("Le prénom est obligatoire !");
            ereurExiste = true;
        }

        if (tfEmail.getText().trim().isEmpty()) {
            emailEreurLabel.setText("L'email est obligatoire !");
            ereurExiste = true;
        } else if (!isValidEmail(tfEmail.getText().trim())) {
            emailEreurLabel.setText("L'email est invalide !");
            ereurExiste = true;
        }

        if (tfTelephone.getText().trim().isEmpty()) {
            telephoneEreurLabel.setText("Le téléphone est obligatoire !");
            ereurExiste = true;
        } else if (!isValidTelephone(tfTelephone.getText().trim())) {
            telephoneEreurLabel.setText("Le téléphone doit contenir 10 chiffres !");
            ereurExiste = true;
        }

        if (comboBoxSexe.getSelectionModel().getSelectedItem() == null) {
            sexeEreurLabel.setText("Le sexe est obligatoire !");
            ereurExiste = true;
        }

        if (comboBoxClasse.getSelectionModel().getSelectedItem() == null) {
            classeEreurLabel.setText("La classe est obligatoire !");
            ereurExiste = true;
        }

        if (comboBoxFiliere.getSelectionModel().getSelectedItem() == null) {
            filiereEreurLabel.setText("La filière est obligatoire !");
            ereurExiste = true;
        }

        if (ereurExiste) {
            return;
        }

        int id_classe = 0;
        for (Classe classe : classes) {
            if (classe.getNomClasse().equals(comboBoxClasse.getSelectedItem())) {
                id_classe = classe.getIdClasse();
                break;
            }
        }
        Etudiant etudiantModifier = new Etudiant(tfCne.getText(),
                tfNom.getText(), tfPrenom.getText(), tfEmail.getText(),
                tfTelephone.getText(), comboBoxSexe.getSelectedItem(), id_classe);
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        etudiantDAO.modifierEtudiant(etudiant.getCne(),etudiantModifier);
        try {
            etudiantsController.getEtudiantsListe();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) modifierBtn.getScene().getWindow();
        stage.close();
        Notification.getNotification("Etudiant Bien Modifie");

    }

    private void renitializerEreurLabel() {
        cneEreurLabel.setText("");
        nomEreurLabel.setText("");
        prenomEreurLabel.setText("");
        emailEreurLabel.setText("");
        telephoneEreurLabel.setText("");
        sexeEreurLabel.setText("");
        classeEreurLabel.setText("");
        filiereEreurLabel.setText("");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidTelephone(String telephone) {
        String telephoneRegex = "\\d{10}";
        return telephone.matches(telephoneRegex);
    }

    public void getFilieres() throws SQLException {
        filiereDAO = new FiliereDAO();
        filieres = filiereDAO.obtenirToutesLesFilieres();
        if (filieres != null && !filieres.isEmpty()) {
            for (Filiere filiere : filieres) {
                comboBoxFiliere.getItems().add(filiere.getNomFiliere());
            }
            comboBoxFiliere.getSelectionModel().selectItem(etudiant.getNomFiliere());
        } else {
            System.out.println("Aucune filière trouvée");
        }
    }

    public void getClasse() throws SQLException {
        if (comboBoxFiliere.getSelectedItem() == null) {
            comboBoxFiliere.requestFocus();
        } else {
            comboBoxClasse.getItems().clear();
            classeDAO = new ClasseDAO();
            classes = classeDAO.getClasseParNomFilier(comboBoxFiliere.getSelectedItem());
            for (Classe c : classes) {
                comboBoxClasse.getItems().add(c.getNomClasse());
            }
            comboBoxClasse.getSelectionModel().selectItem(etudiant.getNomClasse());
        }
    }

    public void filierChange() {
        comboBoxFiliere.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                comboBoxClasse.getSelectionModel().clearSelection();
                comboBoxClasse.getItems().clear();
                classeDAO = new ClasseDAO();
                try {
                    classes = classeDAO.getClasseParNomFilier(comboBoxFiliere.getSelectedItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (Classe c : classes) {
                    comboBoxClasse.getItems().add(c.getNomClasse());
                }
            }
        });
    }

}
