package com.meco.evil.pm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BirdOverviewPM {
    private final ObjectProperty<BirdPM> currentSelection = new SimpleObjectProperty<>();
    public ObjectProperty<BirdPM> currentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(BirdPM birdPM) {
        currentSelection.set(birdPM);
    }
}
