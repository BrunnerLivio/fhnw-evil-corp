package com.meco.evil.pm.i18n;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class I18nTranslationPM {
    private final StringProperty currentTranslation = new SimpleStringProperty();
    private final Map<I18nLanguage, String> translation = new HashMap<>();
    private final String i18nKey;

    public I18nTranslationPM(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public StringProperty getCurrentTranslation() {
        return currentTranslation;
    }

    public void putLanguageKey(I18nLanguage language, String value) {
        translation.put(language, value);
    }

    public void changeLanguageTo(I18nLanguage language) {
        // If languages isn't set, return the key to highlight that the value has not been set yet
        var newTranslationValue = translation.get(language);
        currentTranslation.set(newTranslationValue == null ? i18nKey : newTranslationValue);
    }
}
