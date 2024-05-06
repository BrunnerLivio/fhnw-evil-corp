package com.meco.evil.ui;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.PauseTransition;
import javafx.beans.property.ObjectProperty;
import javafx.css.PseudoClass;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HeaderUI extends HBox implements BaseUI {
    private ApplicationPM applicationPM;
    private Button btnPixelated;
    private Button btnBW;
    private Button btnGreyScale;

    private Button btnDiscard;
    private Button btnDownload;

    public HeaderUI(ApplicationPM applicationPM) {
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

        this.btnBW.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), !applicationPM.getImageIsBw().get());
        this.btnGreyScale.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
                !applicationPM.getImageIsGrayscale().get());
        this.btnPixelated.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
                !applicationPM.getImageIsPixelated().get());
    }

    @Override
    public void layoutControls() {
        var buttonBarLeft = new HBox();

        buttonBarLeft.setSpacing(10);
        buttonBarLeft.getChildren().addAll(
                btnPixelated,
                btnBW,
                btnGreyScale);
        HBox.setHgrow(buttonBarLeft, Priority.ALWAYS);
        var buttonBarRight = new HBox();
        buttonBarRight.getStyleClass().add("header-right");
        buttonBarRight.setSpacing(10);
        buttonBarRight.getChildren().addAll(
                btnDiscard,
                btnDownload);
        getChildren().addAll(
                buttonBarLeft,
                buttonBarRight);
    }

    @Override
    public void setupEventHandlers() {
        this.btnBW.setOnAction(e -> {
            applicationPM.getImageIsBw().set(!applicationPM.getImageIsBw().get());
            this.btnBW.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
                    !applicationPM.getImageIsBw().get());
        });
        this.btnGreyScale.setOnAction(e -> {
            applicationPM.getImageIsGrayscale().set(!applicationPM.getImageIsGrayscale().get());
            this.btnGreyScale.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
                    !applicationPM.getImageIsGrayscale().get());
        });
        this.btnPixelated.setOnAction(e -> {
            applicationPM.getImageIsPixelated().set(!applicationPM.getImageIsPixelated().get());
            this.btnPixelated.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
                    !applicationPM.getImageIsPixelated().get());
        });
        this.btnDiscard.setOnAction(e -> {
            this.removeImage();
        });
        this.btnDownload.setOnAction(e -> {
            if(saveImage(SwingFXUtils.fromFXImage(this.applicationPM.getCurrentProcessedImage().get(), null))) {
                this.removeImage();
            }
        });
    }

    private void removeImage() {
        this.applicationPM.getCurrentImage().set(null);
        this.applicationPM.getImageIsBw().set(false);
        this.applicationPM.getImageIsGrayscale().set(false);
        this.applicationPM.getImageIsPixelated().set(false);
        this.applicationPM.getCurrentFileMetadata().set(null);
    }

    private boolean saveImage(BufferedImage bufferedImage) {
        FileChooser fileChooser = new FileChooser();
        var fileType = this.applicationPM.getCurrentFileMetadata().get().getMimeType();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", String.format("*.%s", "png")));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        fileChooser.setInitialFileName(String.format(
            "%s_%s.%s",
            this.applicationPM.getCurrentFileMetadata().get().getFileName(),
            timestamp,
            fileType
            )
        );
        // Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(bufferedImage, "png", file);
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void setupValueChangedListeners() {
        this.applicationPM.getImageIsBw().addListener((obs, oldVal, newVal) -> {
            this.btnBW.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), !newVal);
        });

        this.applicationPM.getImageIsGrayscale().addListener((obs, oldVal, newVal) -> {
            this.btnGreyScale.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), !newVal);
        });

        this.applicationPM.getImageIsPixelated().addListener((obs, oldVal, newVal) -> {
            this.btnPixelated.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), !newVal);
        });
    }

    @Override
    public void setupBindings() {
    }
}
