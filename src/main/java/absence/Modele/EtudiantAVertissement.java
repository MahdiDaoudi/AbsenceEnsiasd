package absence.Modele;

import absence.Modele.Etudiant;

public class EtudiantAVertissement {
    private Etudiant etudiant;
    private int nombreAbscence;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  String message;

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public int getNombreAbscence() {
        return nombreAbscence;
    }

    public void setNombreAbscence(int nombreAbscence) {
        this.nombreAbscence = nombreAbscence;
    }

    public EtudiantAVertissement(Etudiant etudiant,int nombreAbscence) {
        this.etudiant = etudiant;
        this.nombreAbscence =nombreAbscence ;
    }

    @Override
    public String toString() {
        return "EtudiantAVertissement{" +
                "etudiant=" + etudiant +
                ", nombreAbscence=" + nombreAbscence +
                ", message='" + message + '\'' +
                '}';
    }
}