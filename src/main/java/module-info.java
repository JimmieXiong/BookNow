module edu.metrostate.booknow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.jshell;


    opens edu.metrostate.booknow to javafx.fxml;
    exports edu.metrostate.booknow;
}