module com.meco.evil {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires org.controlsfx.controls;

    opens com.meco.evil to javafx.fxml;
    exports com.meco.evil;
}
