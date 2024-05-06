package com.meco.evil.pm;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;

import com.meco.evil.pm.changeAction.ChangeActionService;
import com.meco.evil.pm.i18n.I18nPM;
import com.meco.evil.pm.i18n.*;

public class ApplicationPM {

    private final ChangeActionService changeActionService = new ChangeActionService();
    private final BirdListPM dataStorageService = new BirdListPM("birds_of_switzerland.tsv", changeActionService, this);
    private final I18nPM i18n = new I18nPM(
            new HashMap<>() {{
                put(I18nLanguage.DE, "de.csv");
                put(I18nLanguage.EN, "en.csv");
            }}
    );
    private final StringProperty applicationTitle = new SimpleStringProperty("EVIL Shit");

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }
    BooleanProperty btnCtrl = new SimpleBooleanProperty(false);

    public I18nPM getI18n() {
        return i18n;
    }

    public ChangeActionService getChangeActionService() {
        return changeActionService;
    }

    public BirdListPM getdataStorage() {
        return dataStorageService;
    }

    public boolean isBtnCtrl() {
        return btnCtrl.get();
    }

    public void setBtnCtrl(boolean btnCtrl) {
        this.btnCtrl.set(btnCtrl);
    }

    public BooleanProperty btnCtrlProperty() {
        return btnCtrl;
    }
}
