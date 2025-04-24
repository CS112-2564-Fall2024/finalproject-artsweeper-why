package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.control.Label;

public class Tile {

    private final boolean isMine;
    private final int greyVal;
    private boolean isClicked;
    private boolean isFlagged;

    private Label label;

    private final int x, y;

    public Tile(boolean isMine, int greyVal, int x, int y)  {
        this.isMine = isMine;
        this.greyVal = greyVal;
        this.x = x;
        this.y = y;
    }

    public String toString(){
        if(isFlagged) return "# "; // is flagged
        else if (isMine) return "0 "; // is mine
        else return "- "; // is clear
    }

    // getters and setters
    public boolean getIsMine() {
        return isMine;
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public boolean getIsFlagged() {
        return isFlagged;
    }

    public int getGreyVal() {
        return greyVal;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public Label getLabel() { return this.label; }

    public void setLabel(Label label) { this.label = label; }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
