package com.meco.evil.ui;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdOverviewPM;
import com.meco.evil.pm.i18n.I18nLanguage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.awt.*;

public class HeaderUI extends HBox implements BaseUI {
    private ApplicationPM applicationPM;
    private BirdOverviewPM birdOverviewPM;
    private Button btnPixelated;
    private Button btnBW;
    private Button btnGreyScale;

    private Button btnDiscard;
    private Button btnDownload;

    public HeaderUI(BirdOverviewPM birdOverviewPM, ApplicationPM applicationPM) {
        this.birdOverviewPM = birdOverviewPM;
        this.applicationPM = applicationPM;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    public ObjectProperty<EventHandler<? super MouseEvent>> btnRemoveOnAction() {
        return btnGreyScale.onMouseClickedProperty();
    }

    public ObjectProperty<EventHandler<? super MouseEvent>> btnAddOnAction() {
        return btnBW.onMouseClickedProperty();
    }

    @Override
    public Object getModel() {
        return null;
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("header-ui");
    }

    @Override
    public void initializeControls() {
        btnPixelated = new Button("Pixelated");
        btnBW = new Button("Black & White");
        btnGreyScale = new Button("Greyscale");
        btnDiscard = new Button("Discard");
        btnDiscard.getStyleClass().add("button-secondary");
        btnDownload = new Button("Dowload");
        
        changeLanguageBtnSelection(applicationPM.getI18n().getCurrentLanguage().getValue());
    }

    @Override
    public void layoutControls() {
        var buttonBarLeft = new HBox();

        buttonBarLeft.setSpacing(10);
        buttonBarLeft.getChildren().addAll(
                btnPixelated,
                btnBW,
                btnGreyScale
        );
        HBox.setHgrow(buttonBarLeft, Priority.ALWAYS);
        var buttonBarRight = new HBox();
        buttonBarRight.getStyleClass().add("header-right");
        buttonBarRight.setSpacing(10);
        buttonBarRight.getChildren().addAll(
                btnDiscard,
                btnDownload
        );
        getChildren().addAll(
                buttonBarLeft,
                buttonBarRight
        );
    }

    @Override
    public void setupEventHandlers() {
        btnDiscard.setOnAction(e -> {});
        btnDownload.setOnAction(e -> {});
        btnPixelated.setOnAction(e -> {});
        // this.birdOverviewPM.currentSelection().addListener((observable, oldValue, newValue) -> {
        //     this.btnGreyScale.setDisable(newValue == null);
        // });
    }

    private void changeLanguageBtnSelection(I18nLanguage newValue) {

    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {
    }
}
