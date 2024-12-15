package absence.Controllers;

import absence.Dao.ClasseDAO;
import absence.Dao.EtudiantDAO;
import absence.Dao.FiliereDAO;
import absence.Modeles.Classe;
import absence.Modeles.Etudiant;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class AjouterEtudiantsController {

    @FXML
    private MFXButton annulerBtn;

    @FXML
    private MFXComboBox<String> comboBoxClasse;

    @FXML
    private MFXComboBox<String> comboBoxClasseExcel;

    @FXML
    private MFXComboBox<String> comboBoxFiliere;

    @FXML
    private MFXComboBox<String> comboBoxFiliereExcel;

    @FXML
    private MFXComboBox<String> comboBoxSexe;

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private AnchorPane paneAjouter;

    @FXML
    private AnchorPane paneAjouterExcel;

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
    private MFXButton uploadBtn;

    @FXML
    private MFXButton navigationAjouterBtn;

    @FXML
    private MFXButton navigationAjouterExcelBtn;

    @FXML
    private Label classeExcelEreurLabel;

    @FXML
    private Label filiereExcelEreurLabel;

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

    @FXML
    private Label infoFichierSelectionner;

    private EtudiantsController etudiantsController;

    private EtudiantDAO etudiantDAO;
    private FiliereDAO filiereDAO;
    private ClasseDAO classeDAO;
    private List<Filiere> filieres;
    private List<Classe> classes;

    @FXML
    void initialize() throws SQLException {
        filiereDAO = new FiliereDAO();
        classeDAO = new ClasseDAO();
        etudiantDAO = new EtudiantDAO();
        comboBoxSexe.getItems().setAll("Homme", "Femme");
        filieres = filiereDAO.obtenirToutesLesFilieres();
        for (Filiere filiere : filieres) {
            comboBoxFiliere.getItems().add(filiere.getNomFiliere());
            comboBoxFiliereExcel.getItems().add(filiere.getNomFiliere());
        }
        filierChange();
        filierExcelChange();
    }

    @FXML
    void ajouterEtudiant(ActionEvent event) {
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
        Etudiant etudiant = new Etudiant(tfCne.getText(),
                tfNom.getText(), tfPrenom.getText(), tfEmail.getText(),
                tfTelephone.getText(), comboBoxSexe.getSelectedItem(), id_classe);
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        etudiantDAO.ajouterEtudiant(etudiant);
        try {
            etudiantsController.getEtudiantsListe();
            Stage stage = (Stage) ajouterBtn.getScene().getWindow();
            stage.close();
            Notification.getNotification("Etudiant Bien Ajouter");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
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
        filiereExcelEreurLabel.setText("");
        classeExcelEreurLabel.setText("");
    }


    @FXML
    void upload(ActionEvent event) throws SQLException {
        renitializerEreurLabel();

        boolean ereurExiste = false;
        if (comboBoxClasseExcel.getSelectionModel().getSelectedItem() == null) {
            classeExcelEreurLabel.setText("La classe est obligatoire !");
            ereurExiste = true;
        }

        if (comboBoxFiliereExcel.getSelectionModel().getSelectedItem() == null) {
            filiereExcelEreurLabel.setText("La filière est obligatoire !");
            ereurExiste = true;
        }

        if (ereurExiste) {
            return;
        }

        int id_classe = 0;
        for (Classe classe : classes) {
            if (classe.getNomClasse().equals(comboBoxClasseExcel.getSelectedItem())) {
                id_classe = classe.getIdClasse();
                break;
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez le fichier Excel");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel File", "*.xls", "*.xlsx")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File fichier = fileChooser.showOpenDialog(stage);


        if (fichier != null) {
            String cheminFichier = fichier.getAbsolutePath();
            boolean succes = importerEtudiantsDepuisExcel(cheminFichier, id_classe);
            // Afficher un message selon le résultat
            if (succes) {
                infoFichierSelectionner.setStyle("-fx-text-fill: #3ee103;");
            } else {
                infoFichierSelectionner.setStyle("-fx-text-fill: #ff0000;");
                etudiantsController.getEtudiantsListe();
                Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage1.close();
            }
        } else {
            infoFichierSelectionner.setText("Aucun fichier sélectionné.");
            infoFichierSelectionner.setStyle("-fx-text-fill: #ff0000;");
        }

    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidTelephone(String telephone) {
        String telephoneRegex = "\\d{10}";
        return telephone.matches(telephoneRegex);
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void allerAjouter(ActionEvent event) {
        paneAjouterExcel.setVisible(false);
        paneAjouter.setVisible(true);
        navigationAjouterBtn.setStyle("-fx-background-color: #fff;-fx-background-radius: 0;");
        navigationAjouterExcelBtn.setStyle("-fx-background-color: #eee;-fx-background-radius: 0;");
    }

    @FXML
    void allerAjouterExcel(ActionEvent event) {
        paneAjouter.setVisible(false);
        paneAjouterExcel.setVisible(true);
        navigationAjouterBtn.setStyle("-fx-background-color: #eee;-fx-background-radius: 0;");
        navigationAjouterExcelBtn.setStyle("-fx-background-color: #fff;-fx-background-radius: 0;");
    }

    public EtudiantsController getEtudiantsController() {
        return etudiantsController;
    }

    public void setEtudiantsController(EtudiantsController etudiantsController) {
        this.etudiantsController = etudiantsController;
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
        }
    }

    public void getClasseExcel() throws SQLException {
        if (comboBoxFiliereExcel.getSelectedItem() == null) {
            comboBoxFiliereExcel.requestFocus();
        } else {
            comboBoxClasseExcel.getItems().clear();
            classeDAO = new ClasseDAO();
            classes = classeDAO.getClasseParNomFilier(comboBoxFiliereExcel.getSelectedItem());
            for (Classe c : classes) {
                comboBoxClasseExcel.getItems().add(c.getNomClasse());
            }
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

    public void filierExcelChange() {
        comboBoxFiliereExcel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                comboBoxClasseExcel.getSelectionModel().clearSelection();
                comboBoxClasseExcel.getItems().clear();
                classeDAO = new ClasseDAO();
                try {
                    classes = classeDAO.getClasseParNomFilier(comboBoxFiliereExcel.getSelectedItem());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (Classe c : classes) {
                    comboBoxClasseExcel.getItems().add(c.getNomClasse());
                }
            }
        });
    }

    public boolean importerEtudiantsDepuisExcel(String fichierPath, int id_classe) {
        try (FileInputStream fis = new FileInputStream(new File(fichierPath))) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Première feuille

            if (sheet.getPhysicalNumberOfRows() <= 1) { // Vérifie si la feuille est vide (ou contient uniquement les en-têtes)
                infoFichierSelectionner.setText("Le fichier est vide ou ne contient aucune donnée.");
                workbook.close();
                return false;
            }

            // Vérification des en-têtes
            Row headerRow = sheet.getRow(0);
            if (!validerEntetes(headerRow)) {
                infoFichierSelectionner.setText("Le fichier Excel ne correspond pas au format attendu (en-têtes incorrects).");
                workbook.close();
                return false;
            }

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Ignorer les en-têtes (première ligne)

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (!ligneValide(row)) { // Vérifie si la ligne est valide
                    continue; // Ignore cette ligne et passe à la suivante
                }

                // Lecture des cellules après vérification
                String cne = row.getCell(0).getStringCellValue();
                String nom = row.getCell(1).getStringCellValue();
                String prenom = row.getCell(2).getStringCellValue();
                String email = row.getCell(3).getStringCellValue();
                String telephone = row.getCell(4).getStringCellValue();
                String sexe = row.getCell(5).getStringCellValue();

                // Ajouter l'étudiant
                Etudiant etudiant = new Etudiant(cne, nom, prenom, email, telephone, sexe, id_classe);
                etudiantDAO.ajouterEtudiant(etudiant);
            }
            workbook.close();
            infoFichierSelectionner.setText("Les étudiants ont été ajoutés avec succès !");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private boolean validerEntetes(Row headerRow) {
        if (headerRow == null) {
            infoFichierSelectionner.setText("La première ligne (en-têtes) est vide ou n'existe pas.");
            return false;
        }

        return headerRow.getCell(0).getStringCellValue().equalsIgnoreCase("CNE") &&
                headerRow.getCell(1).getStringCellValue().equalsIgnoreCase("Nom") &&
                headerRow.getCell(2).getStringCellValue().equalsIgnoreCase("Prénom") &&
                headerRow.getCell(3).getStringCellValue().equalsIgnoreCase("Email") &&
                headerRow.getCell(4).getStringCellValue().equalsIgnoreCase("Téléphone") &&
                headerRow.getCell(5).getStringCellValue().equalsIgnoreCase("Sexe");
    }

    private boolean ligneValide(Row row) {
        try {
            if (row.getCell(0) == null || row.getCell(0).getStringCellValue().isEmpty()) return false;
            if (row.getCell(1) == null || row.getCell(1).getStringCellValue().isEmpty()) return false;
            if (row.getCell(2) == null || row.getCell(2).getStringCellValue().isEmpty()) return false;
            if (row.getCell(3) == null || row.getCell(3).getStringCellValue().isEmpty()) return false;
            if (row.getCell(4) == null || row.getCell(4).getStringCellValue().isEmpty()) return false;
            if (row.getCell(5) == null || row.getCell(5).getStringCellValue().isEmpty()) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
