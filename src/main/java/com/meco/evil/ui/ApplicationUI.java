package com.meco.evil.ui;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.Notifications;

import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdOverviewPM;
import com.meco.evil.pm.BirdPM;
import com.meco.evil.pm.Utility;
import java.awt.event.KeyEvent;


public class ApplicationUI extends StackPane {
    private final ApplicationPM model;
    private MasterDetailPane mainView;
    private HeaderUI headerUI;
    private FileUploadOverview fileUploadOverview;
    private BirdPM birdPm;
    private VBox main;

    public ApplicationUI(ApplicationPM model) {
        this.model = model;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
        var bounds = Screen.getPrimary().getBounds();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeControls() {
        mainView = new MasterDetailPane();
        fileUploadOverview = new FileUploadOverview(model);
        headerUI = new HeaderUI(model);
        main = new VBox();
    }

    private void layoutControls() {
        main.setSpacing(10);
        // mainView.setMasterNode(birdOverviewUI);
        mainView.setShowDetailNode(false);
        mainView.setMasterNode(fileUploadOverview);
        VBox.setVgrow(mainView, Priority.ALWAYS);

        // Main content
        main.getChildren().addAll(
                headerUI,
                mainView
        );
        getStyleClass().add("main-ui");
        getChildren().add(main);
    }

    private void setupEventHandlers() {
        // this.headerUI.btnRemoveOnAction().set(event -> removeCurrentSelection());
        // this.headerUI.btnAddOnAction().set(onChange());
    }

    private void setupValueChangedListeners() {
    }

    private void showDetailViewWithCurrentBird() {

    }

    private void removeCurrentSelection() {
    }

    private void setupBindings() {
    }
}
