module com.meco.evil {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.meco.evil to javafx.fxml;
    exports com.meco.evil;
}
