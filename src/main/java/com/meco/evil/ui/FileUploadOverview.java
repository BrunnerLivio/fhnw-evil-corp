package com.meco.evil.ui;

import com.meco.evil.BaseUI;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdListPM;
import com.meco.evil.pm.BirdPM;
import com.meco.evil.pm.FilePM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;



public class FileUploadOverview extends VBox {
    private final ApplicationPM appModel;

    private VBox fileUploadZone = new VBox();
    private VBox fileUploadDropZone = new VBox();

    private Text uploadParagrpah = new Text("Upload an image or Drag and Drop to get started");
    private Text uploadSubText = new Text("allowed file types: .jpg, .png");

    public FileUploadOverview(ApplicationPM appModel) {
        this.appModel = appModel;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    public void initializeSelf() {

    }

    public void initializeControls() {
        VBox.setMargin(fileUploadZone, new Insets(30));
        fileUploadZone.setAlignment(Pos.CENTER);
        fileUploadDropZone.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
        fileUploadDropZone.setSpacing(10);

        uploadParagrpah.getStyleClass().add("upload-paragraph");
        uploadSubText.getStyleClass().add("upload-paragraph-subtitle");
    }

    public void layoutControls() {
        var screenBounds = Screen.getPrimary().getVisualBounds();
        fileUploadDropZone.setPrefSize(screenBounds.getWidth() * 0.7, screenBounds.getHeight() * 0.7);
        fileUploadDropZone.getStyleClass().add("file-upload-zone");
        fileUploadDropZone.getChildren().addAll(
            uploadParagrpah,
            uploadSubText
        );
        fileUploadZone.getChildren().add(fileUploadDropZone);

        getChildren().addAll(
            fileUploadZone
        );
    }

    public void setupEventHandlers() {

    }

    public void setupValueChangedListeners() {

    }

    public void setupBindings() {
    }
}
