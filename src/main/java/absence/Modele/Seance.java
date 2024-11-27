package absence.Modele;

import java.time.LocalDateTime;

public class Seance {
    private int idSeance;
    private LocalDateTime dateSeance;
    private int duree;
    private String typeSeance;
    private int idModule;
    private int idClasse;

    public Seance(int idSeance, LocalDateTime dateSeance, int duree, String typeSeance, int idModule, int idClasse) {
        this.idSeance = idSeance;
        this.dateSeance = dateSeance;
        this.duree = duree;
        this.typeSeance = typeSeance;
        this.idModule = idModule;
        this.idClasse = idClasse;
    }

    // Getters et Setters
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public LocalDateTime getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(LocalDateTime dateSeance) {
        this.dateSeance = dateSeance;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
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
}
