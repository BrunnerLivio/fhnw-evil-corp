package com.meco.evil.pm;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utility {

    public static void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.show();
    }

    public static boolean showConfirmation(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        return alert.showAndWait().get() == ButtonType.OK;
    }
}
