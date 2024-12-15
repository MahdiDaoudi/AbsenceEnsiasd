package absence.Controllers;

import absence.Dao.ClasseDAO;
import absence.Dao.FiliereDAO;
import absence.Modeles.Classe;
import absence.Modeles.Filiere;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FilierEtClasseController {
        private List<Filiere> filieres;
        private List<Classe> classes;
        FiliereDAO filiereDAO;
        ClasseDAO classeDAO;

        @FXML
        private MFXButton ajouterClasse;

        @FXML
        private MFXButton ajouterFilierBtn;

        @FXML
        private FlowPane classeLayout;

        @FXML
        private FlowPane filierLayout;


        public void initialize() throws SQLException {
                filierLayout.getChildren().clear();
                classeLayout.getChildren().clear();
                loadFiliere();
                actualiserFiliere();
                loadClasse();
                actualiserClasse();

        }

        @FXML
        void OnAjouterFilier(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AjouterFiliere.fxml"));
                Parent root = fxmlLoader.load();
                AjouterFilierController ajouterFilierController = fxmlLoader.getController();
                ajouterFilierController.setFilierEtClasseController(this);
                Scene ajouterAdminScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(ajouterAdminScene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.showAndWait();
        }
     @FXML
        void OnAjouterClasse(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AjouterClasse.fxml"));
                Parent root = fxmlLoader.load();
                AjouterClasseController ajouterClasseController = fxmlLoader.getController();
                ajouterClasseController.setFilierEtClasseController(this);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.showAndWait();
        }





        public void actualiserFiliere() throws SQLException {
                filierLayout.getChildren().clear();
                loadFiliere();
                try {
                        for(int i=0;i<filieres.size();i++) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FilierCard.fxml"));
                                Parent filierCards = loader.load();
                                FilierCardController filierCardController = loader.getController();
                                filierCardController.setFilierEtClasseController(this);
                                FilierCardController controllerBox = loader.getController();
                                controllerBox.setData(filieres.get(i));
                                filierLayout.getChildren().add(filierCards);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void actualiserClasse() throws SQLException {
                classeLayout.getChildren().clear();
                loadClasse();
                try {
                        for(int i=0;i<classes.size();i++) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ClasseBox.fxml"));
                                Parent ClasseCards = loader.load();
                                ClasseCardController classeCardController = loader.getController();
                                classeCardController.setFilierEtClasseController(this);
                                ClasseCardController controllerBox = loader.getController();
                                controllerBox.setData(classes.get(i));
                                classeLayout.getChildren().add(ClasseCards);
                                System.out.println(classes.get(i).getNomClasse());

                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }



        public void loadFiliere() throws SQLException {
                filiereDAO = new FiliereDAO();
                filieres = filiereDAO.obtenirToutesLesFilieres();
        }

        public void loadClasse() throws SQLException {
                classeDAO = new ClasseDAO();
                classes = classeDAO.obtenirToutesLesClasses();
        }

        public List<Filiere> getFilieres() {
                return filieres;
        }

        public List<Classe> getClasses() {
                return classes;
        }
}
