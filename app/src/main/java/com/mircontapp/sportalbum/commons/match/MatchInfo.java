package com.mirco.sportalbum.ui.tournaments.match;

import com.mirco.sportalbum.models.PlayerModel;
import com.mirco.sportalbum.utils.Enums;

public interface MatchInfo {
    boolean isLegend();
    int checkMarcatore(PlayerModel player);
    String getTeamName();
}