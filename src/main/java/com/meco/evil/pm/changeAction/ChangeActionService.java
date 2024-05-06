package com.meco.evil.pm.changeAction;

import javafx.beans.property.StringProperty;

public class ChangeActionService {
    private ChangeOperation currentOperation = ChangeOperation.NONE;
    private ChangeActionNode head = new ChangeActionNode(null, null, null) {{
        setInitial(true);
    }};

    public void addChange(StringProperty item, String oldValue, String newValue) {
        if (!head.isInitial && omitChange(oldValue, newValue)) {
            // TODO To be removed
            //System.out.println("No change because only reverted action change");
        } else {
            var newItem = new ChangeActionNode(
                    new ChangeAction(item, oldValue, newValue),
                    head,
                    null
            );
            newItem.setPrevious(head);
            head.setNext(newItem);
            head = newItem;
        }
    }

    private boolean omitChange(String oldValue, String newValue) {
        if (currentOperation.equals(ChangeOperation.NONE)) {
            return false;
        }
        if (currentOperation.equals(ChangeOperation.UNDO)) {
            return head.getItem().getOldValue().equals(newValue) && head.getItem().getNewValue().equals(oldValue);
        } else {
            return head.getItem().getOldValue().equals(oldValue) && head.getItem().getNewValue().equals(newValue);
        }
    }

    public void undo() {
        if (head.getPrevious() != null) {
            this.currentOperation = ChangeOperation.UNDO;
            head.getItem().undo();
            head = head.getPrevious();
            this.currentOperation = ChangeOperation.NONE;
        }
    }

    public void redo() {
        if (head.getNext() != null) {
            this.currentOperation = ChangeOperation.REDO;
            head = head.getNext();
            head.getItem().redo();
            this.currentOperation = ChangeOperation.NONE;
        }
    }

    enum ChangeOperation {
        REDO,
        UNDO,
        NONE
    }
}
