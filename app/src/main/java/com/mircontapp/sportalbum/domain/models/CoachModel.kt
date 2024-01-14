package com.mircontapp.sportalbum.domain.models;

import android.content.Context;

import com.mirco.sportalbum.file_manager.DataOperator;
import com.mirco.sportalbum.utils.Enums;

import java.util.Objects;

public class CoachModel extends GenericModel {
    String name;
    String module;
    String legend;
    String country;

    public CoachModel() {
        name = "Coach";
        module = "442";
    }

    public CoachModel(String row) {
        this();
        String[] field = row.split("_", -1);

        if (field.length == 1) {
            name = field[0];
        }

        if (field.length >= 3) {
            name = field[0];
            module = field[1];
            country = field[2];
        }
    }

    public Enums.MatchModule getMatchModule(Context context) {
        for (Enums.MatchModule matchModule : Enums.MatchModule.values()) {
            if (context.getString(matchModule.getText()).equals(module)) {
                return matchModule;
            }
        }

        return Enums.MatchModule.M442;
    }

    public static DataOperator<CoachModel> parse() {

        return new DataOperator<CoachModel>() {
            @Override
            public CoachModel parseObject(String row) {

                return new CoachModel(row);
            }

            @Override
            public String writeObject() {
                return null;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachModel that = (CoachModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
