package absence.Modeles;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AbsenceTableView {
    private int id_absence;
    private String cne;
    private String nomPrenom;
    private String nomModule;
    private String typeSeance;
    private LocalDate dateAbsence;
    private String heures;
    private String justifie;
    private String motif;

    public AbsenceTableView(int id_absence,String cne, String nomPrenom, String nomModule, String typeSeance, LocalDate dateAbsence, String heures,String justifie, String motif) {
        this.id_absence = id_absence;
        this.cne = cne;
        this.nomPrenom = nomPrenom;
        this.nomModule = nomModule;
        this.typeSeance = typeSeance;
        this.dateAbsence = dateAbsence;
        this.heures = heures;
        this.justifie = justifie;
        this.motif = motif;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }


    public String getJustifie() {
        return justifie;
    }

    public void setJustifie(String justifie) {
        this.justifie = justifie;
    }

    public String getHeures() {
        return heures;
    }


    public void setHeures(String heures) {
        this.heures = heures;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getId_absence() {
        return id_absence;
    }

    public void setId_absence(int id_absence) {
        this.id_absence = id_absence;
    }
}
