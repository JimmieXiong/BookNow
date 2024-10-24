module edu.metrostate.booknow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens edu.metrostate.booknow to javafx.fxml;
    exports edu.metrostate.booknow;
}