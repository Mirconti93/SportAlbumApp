package com.mirco.sportalbum.ui.tournaments.match;

import com.mirco.sportalbum.file_manager.LineUpDataManager;
import com.mirco.sportalbum.models.MatchModel;
import com.mirco.sportalbum.models.PlayerModel;

import java.util.List;

public class MatchManager {

    public List<PlayerModel> attackers;
    public List<PlayerModel> defenders;

    MatchModel matchModel;

    boolean isLegend;

    public MatchManager(List<PlayerModel> attackers, List<PlayerModel> defenders, MatchModel matchModel, boolean isLegend) {
        this.attackers = ElevenPlayersHelper.checkList(attackers);
        this.defenders = ElevenPlayersHelper.checkList(defenders);
        this.matchModel = matchModel;
        this.isLegend = isLegend;
    }

    public List<PlayerModel> getAttackers() {
        return attackers;
    }

    public void setAttackers(List<PlayerModel> attackers) {
        this.attackers = attackers;
    }

    public List<PlayerModel> getDefenders() {
        return defenders;
    }

    public void setDefenders(List<PlayerModel> defenders) {
        this.defenders = defenders;
    }

    public MatchModel getMatchModel() {
        return matchModel;
    }

    public void setMatchModel(MatchModel matchModel) {
        this.matchModel = matchModel;
    }

    public Boolean getLegend() {
        return isLegend;
    }

    public void setLegend(Boolean legend) {
        isLegend = legend;
    }
}
