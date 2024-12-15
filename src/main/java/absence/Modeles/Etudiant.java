package absence.Modeles;
public class Etudiant {
    private String idEtudiant;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String email;
    private String telephone;
    private String sexe;
    private int idClasse;

    // Constructeur
    public Etudiant(String idEtudiant, String nomEtudiant, String prenomEtudiant, String email,
                    String telephone, String sexe, int idClasse) {
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.idClasse = idClasse;
    }

    // Getters et Setters
    public String getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(String idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    // Méthode toString pour afficher un étudiant
    public String toString() {
        return "Etudiant [ID=" + idEtudiant + ", Nom=" + nomEtudiant + ", Prenom=" + prenomEtudiant +
                ", Email=" + email + ", Téléphone=" + telephone + ", Sexe=" + sexe + ", Classe ID=" + idClasse + "]";
    }
}
