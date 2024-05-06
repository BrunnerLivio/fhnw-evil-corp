package com.meco.evil.ui;

import java.io.File;

import org.controlsfx.glyphfont.Glyph;

import com.meco.evil.BaseUI;
import com.meco.evil.filters.BlackAndWhiteFilter;
import com.meco.evil.filters.GrayscaleFilter;
import com.meco.evil.filters.PixelatedFilter;
import com.meco.evil.pm.ApplicationPM;
import com.meco.evil.pm.BirdListPM;
import com.meco.evil.pm.BirdPM;
import com.meco.evil.pm.FilePM;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;
import java.nio.ByteBuffer;

public class FileUploadOverview extends VBox {
    private final ApplicationPM appModel;

    private VBox fileUploadZone = new VBox();
    private VBox fileUploadDropZone = new VBox();

    private Button btnSelectImage = new Button("Select Image", GlyphsDude.createIcon(FontAwesomeIcon.IMAGE));
    private ImageView imageView = new ImageView();

    private Text uploadParagrpah = new Text("Upload an image or Drag and Drop to get started");
    private Text uploadSubText = new Text("allowed file types: .jpg, .png");
    private Rectangle2D screenBonds = Screen.getPrimary().getVisualBounds();

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
        fileUploadDropZone.setPrefSize(this.screenBonds.getWidth() * 0.7, this.screenBonds.getHeight() * 0.7);
        fileUploadDropZone.getStyleClass().add("file-upload-zone");
        fileUploadDropZone.getChildren().addAll(
                btnSelectImage,
                uploadParagrpah,
                uploadSubText);
        fileUploadZone.getChildren().add(fileUploadDropZone);
        getChildren().addAll(
                fileUploadZone);
    }

    public BufferedImage uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                return bufferedImage;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }


    public void setupEventHandlers() {
        this.btnSelectImage.setOnAction(e -> {
            var image = this.uploadImage();
            if (image != null) {
                this.appModel.getCurrentImage().set(SwingFXUtils.toFXImage(image, null));
            }
        });
        this.appModel.getImageIsBw().addListener((obs, oldVal, newVal) -> {
            this.updateImageWithFilters();
        });
        this.appModel.getImageIsGrayscale().addListener((obs, oldVal, newVal) -> {
            this.updateImageWithFilters();
        });
        this.appModel.getImageIsPixelated().addListener((obs, oldVal, newVal) -> {
            this.updateImageWithFilters();
        });
        this.appModel.getCurrentImage().addListener((obs, oldImage, newImage) -> {
            if(newImage != null) {
                this.updateImageWithFilters(newImage);
            } else {
                this.getChildren().clear();
                this.getChildren().add(fileUploadZone);
            }
        });
        this.appModel.getCurrentProcessedImage().addListener((obs, oldImage, newImage) -> {
            this.getChildren().clear();
            this.imageView.setImage(newImage);
            this.imageView.setFitWidth(this.screenBonds.getWidth() * 0.7);
            this.imageView.setFitHeight(this.screenBonds.getHeight() * 0.7);
            this.getChildren().add(this.imageView);
        });
    }

    private void updateImageWithFilters() {
        this.updateImageWithFilters(this.appModel.getCurrentImage().get());
    }

    private void updateImageWithFilters(Image currentImage) {
        if (currentImage != null) {
            var bufferImage = SwingFXUtils.fromFXImage(currentImage, null);
            if (this.appModel.getImageIsGrayscale().get()) {
                var grayScaleFilter = new GrayscaleFilter();
                grayScaleFilter.applyFilter(bufferImage);
            }

            if (this.appModel.getImageIsPixelated().get()) {
                var pixelatedFilter = new PixelatedFilter();
                pixelatedFilter.applyFilter(bufferImage);
            }

            if (this.appModel.getImageIsBw().get()) {
                var bwFilter = new BlackAndWhiteFilter();
                bwFilter.applyFilter(bufferImage);
            }
            this.appModel.getCurrentProcessedImage().set(SwingFXUtils.toFXImage(bufferImage, null));
        }
    }

    public void setupValueChangedListeners() {

    }

    public void setupBindings() {
    }
}
