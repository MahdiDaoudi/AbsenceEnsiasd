package absence.Modeles;

public class
Utilisateur {
    private int ID_User;
    private String NOM_USER;
    private String PRENOM_USER;
    private String EMAIL;
    private String PASSWORD;
    private String TELEPHONE;
    private String ROLE;
    public Utilisateur(int ID_User, String NOM_USER, String PRENOM_USER, String EMAIL, String PASSWORD, String TELEPHONE, String ROLE) {
        this.ID_User = ID_User;
        this.NOM_USER = NOM_USER;
        this.PRENOM_USER = PRENOM_USER;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.TELEPHONE = TELEPHONE;
        this.ROLE = ROLE;
    }

    // Getters et Setters
    public int getID_User() {
        return ID_User;
    }

    public String getNOM_USER() {
        return NOM_USER;
    }

    public String getPRENOM_USER() {
        return PRENOM_USER;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public String getROLE() {
        return ROLE;
    }

    @Override
    public String toString() {
        return "Utilisateur [ID_User=" + ID_User + ", NOM_USER=" + NOM_USER + ", PRENOM_USER=" + PRENOM_USER + ", EMAIL=" + EMAIL + ", TELEPHONE=" + TELEPHONE + ", ROLE=" + ROLE + "]";
    }
}
