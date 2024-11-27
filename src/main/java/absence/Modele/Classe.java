package absence.Modele;

public class Classe {
    private int idClasse;
    private String nomClasse;
    private int idFiliere;

    // Constructeur par défaut
    public Classe() {}

    // Constructeur avec paramètres
    public Classe(int idClasse, String nomClasse, int idFiliere) {
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
        this.idFiliere = idFiliere;
    }

    // Getters et setters
    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public int getIdFiliere() {
        return idFiliere;
    }

    public void setIdFiliere(int idFiliere) {
        this.idFiliere = idFiliere;
    }
}
