package com.mircontapp.sportalbum.domain.models;

public class MarcatoreModel {

    String name;
    String teamName;
    int minute;

    public MarcatoreModel() {
        name="";
        teamName="";
        minute=0;
    }

    public MarcatoreModel(PlayerModel playerModel) {
        this();
        name=playerModel.getMinifiedName();
        teamName=playerModel.getTeamName();
    }

    public String getMarcatoreText() {
         return  this.getMinute() + "' " + this.getName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
