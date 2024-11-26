module absence.absence {
    requires MaterialFX;


    opens absence to javafx.fxml;
    exports absence;

    opens absence.Controllers to javafx.fxml;
    exports absence.Controllers;
}