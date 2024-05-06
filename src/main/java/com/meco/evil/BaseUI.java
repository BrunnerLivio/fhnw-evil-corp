package com.meco.evil;

public interface BaseUI<Model> {

    Model getModel();

    void initializeSelf();

    void initializeControls();

    void layoutControls();

    void setupEventHandlers();

    void setupValueChangedListeners();

    void setupBindings();
}
