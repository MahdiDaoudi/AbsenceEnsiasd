package absence.Controllers;

import absence.Dao.*;
import absence.Modeles.*;
import absence.Modeles.Module;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckListView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoterAbsenceController {
    @FXML
    private MFXComboBox<Classe> classeComboBox;

    @FXML
    private MFXComboBox<Filiere> filierComboBox;

    @FXML
    private MFXComboBox<Module> moduleComboBox;

    @FXML
    private MFXComboBox<String> seanceComboBox;

    @FXML
    private MFXCheckListView<Etudiant> checkListEtudiants;

    @FXML
    private MFXButton envoyerBtn;

    List<Filiere> filieres;
    List<Classe> classes;
    List<absence.Modeles.Module> modules;
    List<Etudiant> etudiants;
    FiliereDAO filiereDAO;
    ClasseDAO classeDAO;
    ModuleDAO moduleDAO;
    EtudiantDAO etudiantDAO;
    SeanceDAO seanceDAO;
    AbsenceDAO absenceDAO;

    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @FXML
    public void initialize() throws SQLException {
        getFilieres();
        Map<String,LocalTime> map = getHeureDebutFinSeance();
        filierChange();
        classeChange();
        seanceChange();
    }

    public void getFilieres() throws SQLException {
        filiereDAO = new FiliereDAO();
        filieres = filiereDAO.obtenirToutesLesFilieres();
        if (filieres != null && !filieres.isEmpty()) {
            filierComboBox.getItems().setAll(filieres);
        filierComboBox.setConverter(new StringConverter<Filiere>() {
            @Override
            public String toString(Filiere filiere) {
                if (filiere != null) {
                    return filiere.getNomFiliere();
                }else{
                    return "";
                }
            }

            @Override
            public Filiere fromString(String string) {
                return null;
            }
        });

        }else{
            System.out.println("Aucune filière trouvée");
        }
    }

    @FXML
    void getClasse(MouseEvent event) throws SQLException {
        if(filierComboBox.getSelectedItem() == null) {
            filierComboBox.requestFocus();
        }else{
            classeComboBox.getItems().clear();
            classeDAO = new ClasseDAO();
            classes = classeDAO.getClasseParFilier(filierComboBox.getSelectedItem().getIdFiliere());
            classeComboBox.getItems().addAll(classes);
            classeComboBox.setConverter(new StringConverter<Classe>() {
                @Override
                public String toString(Classe classe) {
                    if (classe != null) {

                    return classe.getNomClasse();
                    }else {
                        return "";
                    }
                }

                @Override
                public Classe fromString(String string){
                    return null;
                }

            });
            classeComboBox.show();
        }
    }

    @FXML
    void getModules(MouseEvent event) throws SQLException {
        if(classeComboBox.getSelectedItem() == null || filierComboBox.getSelectedItem() == null) {
            classeComboBox.requestFocus();
        }else {
            moduleDAO = new ModuleDAO();
            modules = moduleDAO.getModuleParClasseProf(utilisateur.getID_User(), classeComboBox.getSelectedItem().getIdClasse());
            moduleComboBox.getItems().clear();
            moduleComboBox.getItems().addAll(modules);
            moduleComboBox.setConverter(new StringConverter<Module>() {
                @Override
                public String toString(Module module) {
                    if (module != null) {
                        return module.getNomModule();
                    }else{
                        return "";
                    }
                }

                @Override
                public Module fromString(String string) {
                    return null;
                }
            });
            moduleComboBox.show();
        }
    }

    @FXML
    void getSeance(MouseEvent event) throws SQLException {
        if(classeComboBox.getSelectedItem() == null || filierComboBox.getSelectedItem() == null || moduleComboBox.getSelectedItem() == null) {
            filierComboBox.requestFocus();
        }else{
            List<String> seance = List.of("COURS","TD","TP");
            seanceComboBox.getItems().clear();
            seanceComboBox.getItems().addAll(seance);
            seanceComboBox.show();
        }
    }

    public void remplireCheckListEtudiant(int id_classe) throws SQLException {
        etudiantDAO = new EtudiantDAO();
        etudiants = etudiantDAO.getEtudiantsParClasse(id_classe);
        checkListEtudiants.getItems().clear();
        checkListEtudiants.getItems().addAll(etudiants);
        checkListEtudiants.setConverter(new StringConverter<Etudiant>() {
            @Override
            public String toString(Etudiant etudiant) {
                return etudiant.getNomEtudiant()+" "+etudiant.getPrenomEtudiant();
            }

            @Override
            public Etudiant fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    void envoyer(ActionEvent event) throws SQLException {
        List<Etudiant> listEtudiantAbsences = checkListEtudiants.getSelectionModel().getSelectedValues();
        seanceDAO = new SeanceDAO();
        absenceDAO = new AbsenceDAO();
        Map<String,LocalTime> heureDebutfin = getHeureDebutFinSeance();
        if (listEtudiantAbsences != null) {
            Seance seance = new Seance();
            seance.setDateSeance(LocalDate.now());
            seance.setHeure_debut(heureDebutfin.get("HEURE_DEBUT"));
            seance.setHeure_fin(heureDebutfin.get("HEURE_FIN"));
            seance.setTypeSeance(seanceComboBox.getSelectedItem());
            seance.setIdClasse(classeComboBox.getSelectedItem().getIdClasse());
            seance.setIdModule(moduleComboBox.getSelectedItem().getIdModule());
            seance.setId_user(utilisateur.getID_User());
            int id_seance = seanceDAO.ajouterSeance(seance);
            absenceDAO.ajouterListeAbsence(listEtudiantAbsences, id_seance);
            filierComboBox.getItems().clear();
            classeComboBox.getItems().clear();
            moduleComboBox.getItems().clear();
            seanceComboBox.getItems().clear();
            checkListEtudiants.getItems().clear();
        }else{
            Seance seance = new Seance();
            seance.setDateSeance(LocalDate.now());
            seance.setHeure_debut(heureDebutfin.get("HEURE_DEBUT"));
            seance.setHeure_fin(heureDebutfin.get("HEURE_FIN"));
            seance.setTypeSeance(seanceComboBox.getSelectedItem());
            seance.setIdClasse(classeComboBox.getSelectedItem().getIdClasse());
            seance.setIdModule(moduleComboBox.getSelectedItem().getIdModule());
            seance.setId_user(utilisateur.getID_User());
            System.out.println(seance.getHeure_debut() +" "+seance.getHeure_fin());
            int id_seance = seanceDAO.ajouterSeance(seance);
            filierComboBox.getItems().clear();
            classeComboBox.getItems().clear();
            moduleComboBox.getItems().clear();
            seanceComboBox.getItems().clear();
            checkListEtudiants.getItems().clear();
        }
    }

    public Map<String,LocalTime> getHeureDebutFinSeance(){
        LocalTime localTime = LocalTime.now();
        Map<String,LocalTime> heureDebutfin = new HashMap<>();
        if (localTime.isAfter(LocalTime.of(8, 15)) && localTime.isBefore(LocalTime.of(10, 30))) {
            heureDebutfin.put("HEURE_DEBUT",LocalTime.of(8, 15));
            heureDebutfin.put("HEURE_FIN",LocalTime.of(10, 15));
        }else if (localTime.isAfter(LocalTime.of(10, 30)) && localTime.isBefore(LocalTime.of(12, 30))){
            heureDebutfin.put("HEURE_DEBUT",LocalTime.of(10, 15));
            heureDebutfin.put("HEURE_FIN",LocalTime.of(12, 15));
        }else if (localTime.isAfter(LocalTime.of(14, 15)) && localTime.isBefore(LocalTime.of(16, 30))){
            heureDebutfin.put("HEURE_DEBUT",LocalTime.of(14, 15));
            heureDebutfin.put("HEURE_FIN",LocalTime.of(16, 15));
        }else if (localTime.isAfter(LocalTime.of(16, 30)) && localTime.isBefore(LocalTime.of(18, 30))){
            heureDebutfin.put("HEURE_DEBUT",LocalTime.of(16, 15));
            heureDebutfin.put("HEURE_FIN",LocalTime.of(18, 15));
        }
        return heureDebutfin;
    }

    Filiere ancienFiliere;
    Classe ancienClasse;
    @FXML
    void getAncienFilier(MouseEvent event) {
        ancienFiliere = filierComboBox.getSelectionModel().getSelectedItem();
    }


    public void filierChange() {
        filierComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                classeComboBox.getSelectionModel().clearSelection();
                moduleComboBox.getSelectionModel().clearSelection();
            }
        });
    }


    public void classeChange() {
        classeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if (newValue != oldValue) {
                moduleComboBox.getSelectionModel().clearSelection();
                seanceComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    public void seanceChange() {
        seanceComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    remplireCheckListEtudiant(classeComboBox.getSelectedItem().getIdClasse());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
