package absence.Controllers;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Validation {
    public static boolean valider(TextField nomText , TextField prenomText , TextField telephoneText , TextField emailText, Label erreur) {
        StringBuilder erreurs = new StringBuilder();
        String nom = nomText.getText().trim();
        String prenom = prenomText.getText().trim();
        String email = emailText.getText().trim();
        String telephone = telephoneText.getText().trim();
        if (nom.isEmpty()) {
            erreurs.append("Le champ 'Nom' est obligatoire.\n");
        }
        if (prenom.isEmpty()) {
            erreurs.append("Le champ 'Prénom' est obligatoire.\n");
        }
        if (email.isEmpty()) {
            erreurs.append("Le champ 'Email' est obligatoire.\n");
        } else if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            erreurs.append("Le champ 'Email' n'est pas valide.\n");
        }
        if (telephone.isEmpty()) {
            erreurs.append("Le champ 'Téléphone' est obligatoire.\n");
        } else if (!telephone.matches("^\\d{10}$")) {
            erreurs.append("Le champ 'Téléphone' doit contenir 10 chiffres.\n");
        }
        if (erreurs.length() > 0) {
            erreur.setText(erreurs.toString());
            return false;
        }
        erreur.setText("");
        return true;
    }
}
