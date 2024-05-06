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

import java.awt.event.KeyEvent;


public class ApplicationUI extends StackPane {
    private final ApplicationPM model;
    private MasterDetailPane mainView;
    private HeaderUI headerUI;
    private BirdOverviewUI birdOverviewUI;
    private BirdOverviewPM birdOverviewPM;
    private BirdDetailUI birdDetailUI;
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
        birdOverviewPM = new BirdOverviewPM();
        birdOverviewUI = new BirdOverviewUI(birdOverviewPM, model);
        headerUI = new HeaderUI(birdOverviewPM, model);
        main = new VBox();
    }

    private void layoutControls() {
        main.setSpacing(10);
        mainView.setMasterNode(birdOverviewUI);
        mainView.setShowDetailNode(false);
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
        this.headerUI.btnRemoveOnAction().set(event -> removeCurrentSelection());
        this.headerUI.btnAddOnAction().set(onChange());
        setOnKeyPressed(event -> {
            var keyCode = event.getCode().getCode();
            if (keyCode == KeyEvent.VK_ESCAPE) {
                mainView.setShowDetailNode(false);
            }
            if (!model.isBtnCtrl() && keyCode == KeyEvent.VK_CONTROL) {
                this.model.setBtnCtrl(true);
            }
            if (model.isBtnCtrl() && keyCode == KeyEvent.VK_S) {
                model.setBtnCtrl(false);
                model.getdataStorage().save();
            }
            if (keyCode == KeyEvent.VK_DELETE) {
                removeCurrentSelection();
            }
        });
    }

    private void setupValueChangedListeners() {
        this.birdOverviewPM.currentSelection().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                birdPm = newValue;
                if (birdDetailUI != null) {
                    birdDetailUI.unbind();
                }
                showDetailViewWithCurrentBird();
            } else {
                mainView.setShowDetailNode(false);
            }
        });
    }

    private void showDetailViewWithCurrentBird() {
        birdDetailUI = new BirdDetailUI(birdPm, model);
        mainView.setDetailNode(birdDetailUI);
        mainView.setDividerPosition(0.5);
        mainView.setShowDetailNode(true);
        var delay = new PauseTransition(Duration.ONE);
        delay.setOnFinished(e -> this.requestFocus());
        delay.playFromStart();
    }

    private void removeCurrentSelection() {
        var currentItem = birdOverviewPM.currentSelection().get();
        var deleteEntry = Utility.showConfirmation(
                model.getI18n().getTranslationFor("birdOverview.msg.deleteBird.title").get(),
                String.format(
                        "%s \n%s",
                        model.getI18n().getTranslationFor("birdOverview.msg.deleteBird.message").get(),
                        currentItem.getName()
                )
        );
        if (deleteEntry) {
            mainView.setDetailNode(null);
            mainView.setShowDetailNode(false);
            model.getdataStorage().removeBird(currentItem);
        }
    }

    private EventHandler<? super MouseEvent> onChange() {
        return event -> {
            birdPm = new BirdPM(model.getChangeActionService());
            model.getdataStorage().addBird(birdPm);
            showDetailViewWithCurrentBird();
            var delay = new PauseTransition(Duration.ONE);
            delay.setOnFinished(e -> {
                Notifications.create()
                        .title(model.getI18n().getTranslationFor("birdOverView.msg.createBird.title").get())
                        .text(model.getI18n().getTranslationFor("birdOverView.msg.createBird.message").get())
                        .position(Pos.TOP_RIGHT)
                        .showInformation();
            });
            delay.playFromStart();
            birdOverviewPM.setCurrentSelection(birdPm);
        };
    }

    private void setupBindings() {
    }
}
