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

public class HeaderUI extends HBox implements BaseUI {
    private ApplicationPM applicationPM;
    private BirdOverviewPM birdOverviewPM;
    private Button btnSave;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnUndo;
    private Button btnRedo;
    private Button btnDe;
    private Button btnEn;
    private Button btnHelp;
    private CustomTextField txtSearch;
    private PauseTransition searchDebounce = new PauseTransition(Duration.seconds(0.5));

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
        return btnRemove.onMouseClickedProperty();
    }

    public ObjectProperty<EventHandler<? super MouseEvent>> btnAddOnAction() {
        return btnAdd.onMouseClickedProperty();
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
        btnSave = new Button("", GlyphsDude.createIcon(FontAwesomeIcon.SAVE));
        btnAdd = new Button("", GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        btnRemove = new Button("", GlyphsDude.createIcon(FontAwesomeIcon.TRASH));
        btnRemove.setDisable(true);
        btnUndo = new Button("", GlyphsDude.createIcon(FontAwesomeIcon.ARROW_CIRCLE_ALT_LEFT));
        btnRedo = new Button("", GlyphsDude.createIcon(FontAwesomeIcon.ARROW_CIRCLE_ALT_RIGHT));
        btnDe = new Button("DE");
        btnEn = new Button("EN");
        btnHelp = new Button("Info", GlyphsDude.createIcon(FontAwesomeIcon.QUESTION));
        txtSearch = new CustomTextField();
        txtSearch.setLeft(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH));
        changeLanguageBtnSelection(applicationPM.getI18n().getCurrentLanguage().getValue());
    }

    @Override
    public void layoutControls() {
        var buttonBarLeft = new HBox();
        buttonBarLeft.setSpacing(10);
        buttonBarLeft.getChildren().addAll(
                btnSave,
                btnAdd,
                btnRemove,
                btnUndo,
                btnRedo
        );
        HBox.setHgrow(buttonBarLeft, Priority.ALWAYS);
        var buttonBarRight = new HBox();
        buttonBarRight.getStyleClass().add("header-right");
        buttonBarRight.setSpacing(10);
        buttonBarRight.getChildren().addAll(
                btnHelp,
                btnDe,
                btnEn,
                txtSearch
        );
        btnHelp.getStyleClass().add("info-button");
        getChildren().addAll(
                buttonBarLeft,
                buttonBarRight
        );
    }

    @Override
    public void setupEventHandlers() {
        btnHelp.setOnAction(event -> {
            Notifications.create()
                    .title(applicationPM.getI18n().getTranslationFor("header.msg.help.title").get())
                    .text(applicationPM.getI18n().getTranslationFor("header.msg.help.message").get())
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
            var delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> {
                // try {
                //     // Desktop.getDesktop().browse(new URL("https://github.com/frithjofhoppe/twittr-readme").toURI());
                // } catch (IOException | URISyntaxException ex) {
                //     throw new RuntimeException(ex);
                // }
            });
            delay.playFromStart();
        });
        btnDe.setOnAction(e -> applicationPM.getI18n().changeLanguageTo(I18nLanguage.DE));
        btnEn.setOnAction(e -> applicationPM.getI18n().changeLanguageTo(I18nLanguage.EN));
        btnUndo.setOnAction(e -> applicationPM.getChangeActionService().undo());
        btnRedo.setOnAction(e -> applicationPM.getChangeActionService().redo());
        btnSave.setOnAction(e -> applicationPM.getdataStorage().save());
        this.birdOverviewPM.currentSelection().addListener((observable, oldValue, newValue) -> {
            this.btnRemove.setDisable(newValue == null);
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchDebounce.setOnFinished(event -> applicationPM.getdataStorage().search(newValue));
            searchDebounce.playFromStart();
        });
        applicationPM.getI18n().getCurrentLanguage().addListener((observable, oldValue, newValue) -> changeLanguageBtnSelection(newValue));
    }

    private void changeLanguageBtnSelection(I18nLanguage newValue) {
        if (newValue.equals(I18nLanguage.EN)) {
            btnEn.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
            btnDe.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        } else {
            btnDe.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
            btnEn.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        }
    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {
        btnSave.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.save"));
        btnAdd.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.add"));
        btnRemove.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.remove"));
        btnUndo.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.undo"));
        btnRedo.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.redo"));
        btnHelp.textProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.help"));
        txtSearch.promptTextProperty().bind(applicationPM.getI18n().getTranslationFor("header.actions.search"));
    }
}
