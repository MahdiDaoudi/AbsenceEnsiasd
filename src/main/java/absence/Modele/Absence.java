package absence.Modele;

public class Absence {
    private int idAbsence;
    private int justifie; // 0 ou 1 pour Justifié
    private String motif;
    private String idEtudiant;
    private int idSeance;

    // Constructeur
    public Absence(int idAbsence, int justifie, String motif, String idEtudiant, int idSeance) {
        this.idAbsence = idAbsence;
        this.justifie = justifie;
        this.motif = motif;
        this.idEtudiant = idEtudiant;
        this.idSeance = idSeance;
    }

    // Getter et Setter
    public int getIdAbsence() {
        return idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    public int getJustifie() {
        return justifie;
    }

    public void setJustifie(int justifie) {
        this.justifie = justifie;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(String idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }
}
