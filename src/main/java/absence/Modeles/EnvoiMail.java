package absence.Modeles;

import java.sql.Timestamp;

public class EnvoiMail {
    private int idMail;
    private String emailDestinataire;
    private String message;
    private Timestamp dateEnvoi;

    // Constructeur
    public EnvoiMail(int idMail, String emailDestinataire, String message, Timestamp dateEnvoi) {
        this.idMail = idMail;
        this.emailDestinataire = emailDestinataire;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
    }

    // Getters et setters
    public int getIdMail() {
        return idMail;
    }

    public void setIdMail(int idMail) {
        this.idMail = idMail;
    }

    public String getEmailDestinataire() {
        return emailDestinataire;
    }

    public void setEmailDestinataire(String emailDestinataire) {
        this.emailDestinataire = emailDestinataire;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Timestamp dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}
