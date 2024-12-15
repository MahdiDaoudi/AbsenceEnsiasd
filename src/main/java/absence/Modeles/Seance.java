package absence.Modeles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Seance {
    private int idSeance;
    private LocalDate dateSeance;
    private LocalTime Heure_debut;
    private LocalTime Heure_fin;
    private String typeSeance;
    private int idModule;
    private int idClasse;
    private int id_user;

    public Seance(LocalDate dateSeance, LocalTime Heure_debut,LocalTime Heure_Fin, String typeSeance, int idModule, int idClasse,int id_user) {
        this.dateSeance = dateSeance;
        this.Heure_debut = Heure_debut;
        this.Heure_fin = Heure_fin;
        this.typeSeance = typeSeance;
        this.idModule = idModule;
        this.idClasse = idClasse;
        this.id_user = id_user;
    }

    public Seance() {}

    // Getters et Setters
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public LocalDate getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public LocalTime getHeure_debut() {
        return Heure_debut;
    }

    public void setHeure_debut(LocalTime heure_debut) {
        Heure_debut = heure_debut;
    }

    public LocalTime getHeure_fin() {
        return Heure_fin;
    }

    public void setHeure_fin(LocalTime heure_fin) {
        Heure_fin = heure_fin;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
