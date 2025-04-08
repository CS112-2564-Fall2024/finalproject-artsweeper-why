package edu.miracosta.cs112.finalproject.finalproject;

public class Tile {
    boolean isMine;

    boolean isClicked;
    boolean isFlagged;
    int greyVal;

    public Tile(boolean isMine, int greyVal)  {
        this.isMine = isMine;
        this.greyVal = greyVal;
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
}
