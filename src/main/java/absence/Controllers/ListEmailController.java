package absence.Controllers;

import absence.Dao.EnvoiMailDAO;
import absence.Modeles.EnvoiMail;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListEmailController {

    @FXML
    private TableView<EnvoiMail> tableView; // TableView pour afficher les e-mails envoyés

    @FXML
    private TableColumn<EnvoiMail, String> colDestinataire; // Colonne pour le destinataire
    @FXML
    private TableColumn<EnvoiMail, String> colMessage; // Colonne pour la date d'envoi
    @FXML
    private TableColumn<EnvoiMail, String> colMessage1; // Colonne pour le message

    private EnvoiMailDAO envoiMailDAO = new EnvoiMailDAO(); // Instance de la classe DAO pour récupérer les e-mails envoyés

    @FXML
    private void initialize() {
        System.out.println("Hello");
        UserAgentBuilder.builder().themes(MaterialFXStylesheets.forAssemble(true)).setDeploy(true).setResolveAssets(true).build().setGlobal();
        // Initialiser les colonnes de la TableView avec des CellValueFactories adaptées aux getters
        colDestinataire.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmailDestinataire());
        });

        colMessage.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateEnvoi().toString());
        });

        colMessage1.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMessage());
        });

        // Remplir la TableView avec les envois récupérés
        ObservableList<EnvoiMail> envoisObservableList = FXCollections.observableArrayList(envoiMailDAO.recupererEnvois());
        tableView.getItems().setAll(envoisObservableList);// Remplir la TableView avec les données
        tableView.widthProperty().addListener(((observable, oldValue, newValue) -> resizeTableView()));
    }

    public void resizeTableView() {
        double totalWidth = tableView.getWidth();

        for (TableColumn column : tableView.getColumns()) {
            column.setPrefWidth(totalWidth/3);
        }
    }
}
