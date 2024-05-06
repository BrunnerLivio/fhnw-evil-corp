package com.meco.evil.pm.changeAction;

public class ChangeActionNode {
    boolean isInitial;
    ChangeActionNode previous;
    ChangeActionNode next;
    ChangeAction item;

    public ChangeActionNode(ChangeAction item, ChangeActionNode previous, ChangeActionNode next) {
        this.previous = previous;
        this.next = next;
        this.item = item;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public ChangeActionNode getPrevious() {
        return previous;
    }

    public void setPrevious(ChangeActionNode previous) {
        this.previous = previous;
    }

    public ChangeActionNode getNext() {
        return next;
    }

    public void setNext(ChangeActionNode next) {
        this.next = next;
    }

    public ChangeAction getItem() {
        return item;
    }

    public void setItem(ChangeAction item) {
        this.item = item;
    }
}
