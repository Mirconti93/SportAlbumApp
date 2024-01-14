package com.mircontapp.sportalbum.domain.models;

import com.mirco.sportalbum.utils.Enums;

import java.util.ArrayList;
import java.util.List;

public class LineUpModel extends GenericModel {

    private Enums.LineUpModule module;

    List<PlayerModel> starting;
    List<PlayerModel> availables;

    boolean isLegend;

    public LineUpModel() {
        starting = new ArrayList<>();
        availables = new ArrayList<>();
        module = Enums.LineUpModule.P442;
        isLegend = false;
    }

    public Enums.LineUpModule getModule() {
        return module;
    }

    public void setModule(Enums.LineUpModule module) {
        this.module = module;
    }

    public List<PlayerModel> getStarting() {
        return starting;
    }

    public void setStarting(List<PlayerModel> starting) {
        this.starting = starting;
    }

    public List<PlayerModel> getAvailables() {
        return availables;
    }

    public void setAvailables(List<PlayerModel> availables) {
        this.availables = availables;
    }

    public boolean isLegend() {
        return isLegend;
    }

    public void setLegend(boolean legend) {
        isLegend = legend;
    }
}
