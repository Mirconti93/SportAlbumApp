package com.mirco.sportalbum.ui.tournaments.match;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mirco.sportalbum.file_manager.LineUpDataManager;
import com.mirco.sportalbum.file_manager.PlayerDataManager;
import com.mirco.sportalbum.file_manager.TeamDataManager;
import com.mirco.sportalbum.models.CoachModel;
import com.mirco.sportalbum.models.MarcatoreModel;
import com.mirco.sportalbum.models.MatchModel;
import com.mirco.sportalbum.models.PlayerModel;
import com.mirco.sportalbum.models.TeamModel;
import com.mirco.sportalbum.models.TiratoreModel;
import com.mirco.sportalbum.utils.AlbumApplication;
import com.mirco.sportalbum.utils.Enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PenaltiesViewModel extends MatchViewModel {

    public MutableLiveData<List<TiratoreModel>> tiratoriHome;
    public MutableLiveData<List<TiratoreModel>> tiratoriAway;
    Integer turn;
    boolean ok;

    private TiratoreModel homeSelected;
    private TiratoreModel awaySelected;

    public PenaltiesViewModel() {
        super();
        tiratoriHome = new MutableLiveData<>();
        tiratoriAway = new MutableLiveData<>();
        tiratoriHome.setValue(new ArrayList<>());
        tiratoriAway.setValue(new ArrayList<>());

    }

    public void init(MatchViewModel matchViewModel) {
        this.getMatch().setValue(matchViewModel.getMatch().getValue());
        this.getHomeTeam().setValue(matchViewModel.getHomeTeam().getValue());
        this.getAwayTeam().setValue(matchViewModel.getAwayTeam().getValue());
        this.getHomeEleven().setValue(matchViewModel.getHomeEleven().getValue());
        this.getAwayEleven().setValue(matchViewModel.getAwayEleven().getValue());
        this.setCoachHome(matchViewModel.getCoachHome());
        this.setCoachAway(matchViewModel.getCoachAway());
        List<TiratoreModel> home = new ArrayList<>();
        for (int i = getHomeEleven().getValue().size() -1; i>= 0; i--) {
            PlayerModel p = getHomeEleven().getValue().get(i);
            if (!p.isEspuslo()) {
                home.add(new TiratoreModel(p));
            }
        }
        tiratoriHome.setValue(home);
        Collections.sort(home, (t1, t2) -> - t1.getPlayerModel().getPlayerStatsModel().getRig().compareTo(t2.getPlayerModel().getPlayerStatsModel().getRig()));

        List<TiratoreModel> away = new ArrayList<>();
        for (int i = getAwayEleven().getValue().size() -1; i>= 0; i--) {
            PlayerModel p = getAwayEleven().getValue().get(i);
            if (!p.isEspuslo()) {
                away.add(new TiratoreModel(p));
            }
        }
        Collections.sort(away, (t1, t2) -> - t1.getPlayerModel().getPlayerStatsModel().getRig().compareTo(t2.getPlayerModel().getPlayerStatsModel().getRig()));
        tiratoriAway.setValue(away);

        turn = 0;
        ok = false;

    }

    public void nextAction() {
        if (getMatch().getValue().getPossesso() == Enums.Possesso.HOME) {
            int max = tiratoriHome.getValue().size() - 1;
            calciaRigore(tiratoriHome.getValue().get(turn % max));
        } else {
            int max = tiratoriAway.getValue().size() - 1;
            calciaRigore(tiratoriAway.getValue().get(turn % max));
            turn++;
        }

        if (turn > 4) {
            ok = getMatch().getValue().getScoreHome() != getMatch().getValue().getScoreAway();
        }
    }

    public void calciaRigore(TiratoreModel playerModel) {
        MatchModel matchModel = MatchHelper.rigoreDiretto(buildMatchManager(), playerModel,
                    getMatch().getValue().getPossesso() == Enums.Possesso.HOME?
                            //goalkeeper from opposite team
                            tiratoriAway.getValue().get(tiratoriAway.getValue().size()-1).getPlayerModel() :
                            tiratoriHome.getValue().get(tiratoriAway.getValue().size()-1).getPlayerModel()
                    );
        getMatch().setValue(matchModel);
    }

    public void homeSelection(TiratoreModel playerModel) {

        if (tiratoriHome.getValue().indexOf(playerModel) == tiratoriHome.getValue().size() -1) {
            return;
        }

        if (homeSelected != null) {
            playerModel.setSelected(false);
            homeSelected.setSelected(false);

            int i1 = tiratoriHome.getValue().indexOf(homeSelected);
            int i2 = tiratoriHome.getValue().indexOf(playerModel);
            Collections.swap(tiratoriHome.getValue(), i1, i2);

            homeSelected = null;

        } else {
            homeSelected = playerModel;
            homeSelected.setSelected(true);
        }

        tiratoriHome.setValue(tiratoriHome.getValue());
    }

    public void awaySelection(TiratoreModel playerModel) {
        if (tiratoriAway.getValue().indexOf(playerModel) == tiratoriAway.getValue().size() -1) {
            return;
        }

        if (awaySelected != null) {
            playerModel.setSelected(false);
            awaySelected.setSelected(false);

            int i1 = tiratoriAway.getValue().indexOf(awaySelected);
            int i2 = tiratoriAway.getValue().indexOf(playerModel);
            Collections.swap(tiratoriAway.getValue(), i1, i2);

            awaySelected = null;

        } else {
            awaySelected = playerModel;
            awaySelected.setSelected(true);
        }

        tiratoriAway.setValue(tiratoriAway.getValue());
    }

    public MutableLiveData<List<TiratoreModel>> getTiratoriHome() {
        return tiratoriHome;
    }

    public MutableLiveData<List<TiratoreModel>> getTiratoriAway() {
        return tiratoriAway;
    }

    public Integer getTurn() {
        return turn;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
