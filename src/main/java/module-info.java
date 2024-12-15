module absence.absence {
    requires MaterialFX;
    requires java.sql;
//    requires java.mail;

    opens absence to javafx.fxml;
    exports absence;

    opens absence.Modeles to javafx.base;
    opens absence.Controllers to javafx.fxml;

    exports absence.Controllers;
    exports absence.Modeles;
}