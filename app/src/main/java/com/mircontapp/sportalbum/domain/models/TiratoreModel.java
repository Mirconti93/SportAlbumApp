package com.mircontapp.sportalbum.domain.models;

public class TiratoreModel{
    PlayerModel playerModel;
    boolean hasShot;
    boolean gol;
    boolean selected;

    public TiratoreModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
        hasShot = false;
        gol = false;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public boolean isHasShot() {
        return hasShot;
    }

    public void setHasShot(boolean hasShot) {
        this.hasShot = hasShot;
    }

    public boolean isGol() {
        return gol;
    }

    public void setGol(boolean gol) {
        this.gol = gol;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
