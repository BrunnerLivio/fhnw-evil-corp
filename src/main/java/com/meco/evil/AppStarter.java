package com.meco.evil;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.ui.ApplicationUI;

public class AppStarter extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationPM pm = new ApplicationPM();
		Parent rootPanel = new ApplicationUI(pm);

		Scene scene = new Scene(rootPanel);

		primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("bund-logo.png"))));
		primaryStage.titleProperty().bind(pm.applicationTitleProperty());

		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
