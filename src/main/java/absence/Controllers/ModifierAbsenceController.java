package absence.Controllers;

import absence.Dao.AbsenceDAO;
import absence.Modeles.AbsenceTableView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierAbsenceController {

    @FXML
    private MFXButton annulerBtn;

    @FXML
    private MFXComboBox<String> comboboxJustifie;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private MFXTextField tfCne;

    @FXML
    private MFXTextField tfMotif;

    @FXML
    private MFXTextField tfNomPrenom;

    private AbsenceAnterieurController absenceAnterieurController;

    private AbsenceTableView absenceTableView;

    private AbsenceDAO absenceDAO;

    public AbsenceTableView getAbsenceTableView() {
        return absenceTableView;
    }

    public void setAbsenceTableView(AbsenceTableView absenceTableView) {
        this.absenceTableView = absenceTableView;
        if (absenceTableView != null) {
            tfCne.setText(absenceTableView.getCne());
            tfNomPrenom.setText(absenceTableView.getNomPrenom());
            tfMotif.setText(absenceTableView.getMotif());
            comboboxJustifie.getItems().addAll("Oui","Non");
            comboboxJustifie.getSelectionModel().selectItem(absenceTableView.getJustifie());
        }
    }

    @FXML
    void initialize() {
    }

    @FXML
    void annuler(ActionEvent event) {
        Stage stage = (Stage) annulerBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifier(ActionEvent event) {
        absenceDAO = new AbsenceDAO();
        if(!absenceTableView.getJustifie().equals(comboboxJustifie.getValue()) || !absenceTableView.getNomPrenom().equals(tfMotif.getText())){
            absenceDAO.mettreAJourAbsence(absenceTableView.getId_absence(),comboboxJustifie.getValue().equals("Oui") ? 1 : 0,tfMotif.getText());
        }
        absenceAnterieurController.getAbsenceListe();
        Stage stage = (Stage) modifierBtn.getScene().getWindow();
        stage.close();
        try {
            Notification.getNotification("Absence Bien Modifie");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AbsenceAnterieurController getAbsenceAnterieurController() {
        return absenceAnterieurController;
    }

    public void setAbsenceAnterieurController(AbsenceAnterieurController absenceAnterieurController) {
        this.absenceAnterieurController = absenceAnterieurController;
    }

}