package com.mirco.sportalbum.ui.tournaments.match;

import com.mirco.sportalbum.models.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class ElevenPlayersHelper {

    public static List<PlayerModel> checkList(List<PlayerModel> players) {

        List<PlayerModel> playerModels = new ArrayList<>();
        if (players.size() < 11) {
            for (int i = players.size()-1; i<11; i++) {
                players.add(new PlayerModel());
            }
        }


        if (players.size() > 11) {
            for (int i = 0; i<11; i++) {
                PlayerModel p = players.get(i);
                playerModels.add(p);
            }
        } else {
            playerModels = players;
        }

        return playerModels;
    }

}
