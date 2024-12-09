module absence.absence {
    requires MaterialFX;
    requires java.sql;
    requires org.apache.poi.ooxml;
    requires mysql.connector.j;
    requires java.mail; // Ajoute cette ligne pour utiliser javax.mail

    // Ouvre les packages utilisés par JavaFX (par exemple, pour les contrôleurs et FXML)
    opens absence to javafx.fxml;
    opens absence.Controllers to javafx.fxml;
    opens absence.Modele to javafx.base; // Ouvre le package modèle pour JavaFX

    // Exporte les packages nécessaires
    exports absence;
    exports absence.Controllers;
    exports absence.Modele; // Permet d'utiliser les classes du package modèle en dehors du module
}
