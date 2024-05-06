package com.meco.evil.pm.i18n;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class I18nPM {
    private static final String DELIMITER = ";";
    private final Map<String, I18nTranslationPM> translations = new HashMap<>();
    private final ObjectProperty<I18nLanguage> currentLanguage = new SimpleObjectProperty<>(
            I18nLanguage.EN
    );

    public I18nPM(Map<I18nLanguage, String> translationFiles) {
        // Initialize all available language files
        translationFiles.forEach(this::addEntriesFromLanguageFile);
        // Default language
        changeLanguageTo(I18nLanguage.EN);
    }

    public StringProperty getTranslationFor(String i18nKey) {
        return getTranslation(i18nKey).getCurrentTranslation();
    }

    public void changeLanguageTo(I18nLanguage language) {
        currentLanguage.set(language);
        // Change presentation of every key to new one
        for (I18nTranslationPM translation : translations.values()) {
            translation.changeLanguageTo(language);
        }
    }

    public ObjectProperty<I18nLanguage> getCurrentLanguage() {
        return currentLanguage;
    }

    private I18nTranslationPM getTranslation(String i18nKey) {
        var translation = translations.get(i18nKey);
        // Create if not exists
        if (translation == null) {
            translation = new I18nTranslationPM(i18nKey);
            translations.put(i18nKey, translation);
        }
        return translation;
    }

    private BufferedReader getReader(String fileName) {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(reader);
    }

    private void putValueForLanguage(I18nLanguage language, String i18nKey, String value) {
        var translation = getTranslation(i18nKey);
        translation.putLanguageKey(language, value);
    }

    private void addEntriesFromLanguageFile(I18nLanguage language, String fileName) {
        try (BufferedReader reader = getReader(fileName)) {
            reader.lines()
                    .skip(1)
                    .map(line -> line.split(DELIMITER))
                    .forEach(line -> putValueForLanguage(language, line[0], line[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
