package absence.Modeles;
public class Etudiant {
    private String cne;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String email;
    private String telephone;
    private String sexe;
    private int idClasse;
    private String nomClasse;
    private String nomFiliere;

    // Constructeur
    public Etudiant(String cne, String nomEtudiant, String prenomEtudiant, String email,
                    String telephone, String sexe, int idClasse) {
        this.cne = cne;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.idClasse = idClasse;
    }

    public Etudiant(String cne, String nomEtudiant, String prenomEtudiant, String email, String telephone, String sexe, int idClasse,String nomFiliere, String nomClasse) {
        this.cne = cne;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
        this.nomFiliere = nomFiliere;
    }

    // Getters et Setters


    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
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

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    // Méthode toString pour afficher un étudiant
    public String toString() {
        return "Etudiant [ID=" + cne + ", Nom=" + nomEtudiant + ", Prenom=" + prenomEtudiant +
                ", Email=" + email + ", Téléphone=" + telephone + ", Sexe=" + sexe + ", Classe ID=" + idClasse + "]";
    }

    public String getNomFiliere() {
        return nomFiliere;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public String getNomComplet(){
        return nomEtudiant +" "+prenomEtudiant;
    }
}
