package com.meco.evil.pm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ApplicationPM {


    private final BooleanProperty imageIsBw = new SimpleBooleanProperty(false);
    private final BooleanProperty imageIsPixelated = new SimpleBooleanProperty(false);
    private final BooleanProperty imageIsGrayscale = new SimpleBooleanProperty(false);
    private final ObjectProperty<Image> currentImage = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> currentProcessedImage = new SimpleObjectProperty<>();
    private final ObjectProperty<FileMetadata> currentFileMetadata = new SimpleObjectProperty<>();  
    private final StringProperty applicationTitle = new SimpleStringProperty("EVIL Shit");
    private final BooleanProperty btnCtrl = new SimpleBooleanProperty(false);

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }
    
    public BooleanProperty getImageIsBw() {
        return imageIsBw;
    }

    public BooleanProperty getImageIsPixelated() {
        return imageIsPixelated;
    }

    public BooleanProperty getImageIsGrayscale() {
        return imageIsGrayscale;
    }
    
    public BooleanProperty getBtnCtrl() {
        return btnCtrl;
    }

    public ObjectProperty<Image> getCurrentImage() {
        return currentImage;
    }

    public ObjectProperty<Image> getCurrentProcessedImage() {
        return currentProcessedImage;
    }

    public ObjectProperty<FileMetadata> getCurrentFileMetadata() {
        return currentFileMetadata;
    }
}
