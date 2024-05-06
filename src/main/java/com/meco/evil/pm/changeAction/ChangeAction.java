package com.meco.evil.pm.changeAction;

import javafx.beans.property.StringProperty;

public class ChangeAction {
    private StringProperty item;
    private String oldValue;
    private String newValue;

    public ChangeAction(StringProperty item, String oldValue, String newValue) {
        this.item = item;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public void undo() {
        item.set(oldValue);
    }

    public void redo() {
        item.set(newValue);
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }
}
