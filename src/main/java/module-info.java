module absence.absence {
    requires MaterialFX;
    requires java.sql;


    opens absence to javafx.fxml;
    exports absence;

    opens absence.Modeles to javafx.base;
    opens absence.Controllers to javafx.fxml;

    exports absence.Controllers;
}