module edu.metrostate.booknow.booknow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.metrostate.booknow to javafx.fxml;
    exports edu.metrostate.booknow;
}