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
import com.mirco.sportalbum.utils.AlbumApplication;
import com.mirco.sportalbum.utils.Enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchViewModel extends ViewModel {

    AlbumApplication app = AlbumApplication.getInstance();

    private MutableLiveData<TeamModel> homeTeam;
    private MutableLiveData<TeamModel> awayTeam;
    private MutableLiveData<List<PlayerModel>> homeRoster;
    private MutableLiveData<List<PlayerModel>> awayRoster;
    private MutableLiveData<PlayerModel> currentPlayer;
    private MutableLiveData<Integer> currentFocus;
    private MutableLiveData<Enums.LineUpChoice> lineUpChoice;
    private MutableLiveData<Integer> minute;

    private LineUpDataManager homeLUManager;
    private LineUpDataManager awayLUManager;

    private MutableLiveData<List<PlayerModel>> homeEleven;
    private MutableLiveData<List<PlayerModel>> awayEleven;
    private MutableLiveData<List<PlayerModel>> homeBench;
    private MutableLiveData<List<PlayerModel>> awayBench;

    private boolean isLegend;
    private Enums.MatchType matchType;

    private PlayerModel playerSelected;
    private CoachModel coachHome;
    private CoachModel coachAway;
    private Enums.MatchModule modHome;
    private Enums.MatchModule modAway;

    private MutableLiveData<MatchModel> match;

    public final static int HOME = 0;
    public final static int AWAY = 1;

    public MatchViewModel() {
        homeTeam = new MutableLiveData<>();
        awayTeam = new MutableLiveData<>();
        homeRoster = new MutableLiveData<>();
        awayRoster = new MutableLiveData<>();
        currentPlayer = new MutableLiveData<>();
        currentFocus = new MutableLiveData<>();
        lineUpChoice = new MutableLiveData<>();
        minute = new MutableLiveData<>();
        match = new MutableLiveData<>();
        updateHomeTeam(TeamDataManager.teamFromName("Atalanta"));
        updateAwayTeam(TeamDataManager.teamFromName("Inter"));
        isLegend = false;
        matchType = Enums.MatchType.SIMPLE_MATCH;
        minute.setValue(0);
        currentFocus.setValue(HOME);
        lineUpChoice.setValue(Enums.LineUpChoice.FIELD);

        homeEleven = new MutableLiveData<>();
        awayEleven = new MutableLiveData<>();
        homeBench = new MutableLiveData<>();
        awayBench = new MutableLiveData<>();

    }

    public void buildTeamsPlayers(Context context) {

        coachHome = TeamDataManager.coachByName(isLegend ? homeTeam.getValue().getCoachlegend() : homeTeam.getValue().getCoach());
        coachAway = TeamDataManager.coachByName(isLegend ? awayTeam.getValue().getCoachlegend() : awayTeam.getValue().getCoach());

        modHome = coachHome.getMatchModule(app.getBaseContext());
        modAway = coachAway.getMatchModule(app.getBaseContext());

        /*** home roster */
        if (homeTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALI) || homeTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALIFEMMINILI)) {
            Enums.Gender gender = homeTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALIFEMMINILI) ? Enums.Gender.F : Enums.Gender.M;
            homeRoster.setValue(isLegend ?
                    PlayerDataManager.playersFromNationalLegend(homeTeam.getValue().getNation(), gender) :
                    PlayerDataManager.playersFromNational(homeTeam.getValue().getNation(), gender));

        } else {
            homeRoster.setValue(isLegend ?
                    PlayerDataManager.playersFromTeamLegend(homeTeam.getValue().getName()) :
                    PlayerDataManager.playersFromTeam(homeTeam.getValue().getName()));
        }

        /*** away roster */
        if (awayTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALI) || awayTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALIFEMMINILI)) {
            Enums.Gender gender = awayTeam.getValue().getArea().getArea().equals(Enums.Area.NAZIONALIFEMMINILI) ? Enums.Gender.F : Enums.Gender.M;
            awayRoster.setValue(isLegend ?
                    PlayerDataManager.playersFromNationalLegend(awayTeam.getValue().getNation(), gender) :
                    PlayerDataManager.playersFromNational(awayTeam.getValue().getNation(), gender));

        } else {
            awayRoster.setValue(isLegend ?
                    PlayerDataManager.playersFromTeamLegend(awayTeam.getValue().getName()) :
                    PlayerDataManager.playersFromTeam(awayTeam.getValue().getName()));
        }

        /*** home stats */
        //homeRoster.setValue(PlayerDataManager.loadStatsTeam(context, homeRoster.getValue()));
        /*** away stats */
        //awayRoster.setValue(PlayerDataManager.loadStatsTeam(context, awayRoster.getValue()));

        homeLUManager = new LineUpDataManager(homeRoster.getValue(), coachHome.getMatchModule(app.getBaseContext()));
        awayLUManager = new LineUpDataManager(awayRoster.getValue(), coachAway.getMatchModule(app.getBaseContext()));

        homeRoster.setValue(homeLUManager.composeLineUp());
        awayRoster.setValue(awayLUManager.composeLineUp());

        homeOnFieledOrBench(true);
        awayOnFieledOrBench(true);

        match.setValue(new MatchModel(homeTeam.getValue(), awayTeam.getValue()));

    }

    public void checkSelection(PlayerModel playerModel, boolean sort) {
        if (playerModel.isEspuslo()) {
            return;
        }

        if (playerSelected != null) {
            playerModel.setSelected(false);
            playerSelected.setSelected(false);

            Enums.RoleLineUp role1 = playerSelected.getRoleLineUp();
            Enums.RoleLineUp role2 = playerModel.getRoleLineUp();
            playerModel.setRoleLineUp(role1);
            playerSelected.setRoleLineUp(role2);

            if (currentFocus.getValue() == HOME) {
                int i1 = homeRoster.getValue().indexOf(playerSelected);
                int i2 = homeRoster.getValue().indexOf(playerModel);
                Collections.swap(homeRoster.getValue(), i1, i2);

            } else {
                int i1 = awayRoster.getValue().indexOf(playerSelected);
                int i2 = awayRoster.getValue().indexOf(playerModel);
                Collections.swap(awayRoster.getValue(), i1, i2);

            }
            playerSelected = null;

        } else {
            playerSelected = playerModel;
            playerSelected.setSelected(true);
        }

        if (currentFocus.getValue() == HOME) {
            homeRoster.setValue(homeRoster.getValue());
            homeOnFieledOrBench(sort);
        } else {
            awayRoster.setValue(awayRoster.getValue());
            awayOnFieledOrBench(sort);
        }

    }

    public void checkSelection(PlayerModel playerModel) {
        checkSelection(playerModel, true);
    }

    public void changeModule(Enums.MatchModule matchModule) {
        if (currentFocus.getValue() == MatchViewModel.HOME) {
            modHome = matchModule;
            homeEleven.setValue(LineUpDataManager.changeModule(homeEleven.getValue(), matchModule));
        } else {
            modAway = matchModule;
            awayEleven.setValue(LineUpDataManager.changeModule(awayEleven.getValue(), matchModule));

        }

    }

    /*** split players on field or in bench */
    public void homeOnFieledOrBench(boolean sort) {
        List<PlayerModel> field = new ArrayList<>();
        List<PlayerModel> bench = new ArrayList<>();
        if (homeRoster.getValue() != null) {
            int size = homeRoster.getValue().size();
            //n di titolari 11 o meno se non ce ne sono 11
            int starting = Math.min(11, size);
            for (int i = 0; i < size; i++) {
                PlayerModel p = homeRoster.getValue().get(i);
                if (i < starting) {
                    field.add(p);
                } else {
                    bench.add(p);
                }
            }
        }
        if (sort) {
            homeEleven.setValue(PlayerDataManager.sortPlayerByRoleLU(field));
            homeBench.setValue(PlayerDataManager.sortPlayerByRoleLU(bench));
        } else {
            homeEleven.setValue(field);
            homeBench.setValue(field);
        }

    }

    public void awayOnFieledOrBench(boolean sort) {
        List<PlayerModel> field = new ArrayList<>();
        List<PlayerModel> bench = new ArrayList<>();
        if (awayRoster.getValue() != null) {
            int size = awayRoster.getValue().size();
            int starting = Math.min(11, size);
            Math.min(11, size);
            for (int i = 0; i < size; i++) {
                PlayerModel p = awayRoster.getValue().get(i);
                if (i < starting) {
                    field.add(p);
                } else {
                    bench.add(p);
                }
            }
        }
        if (sort) {
            awayEleven.setValue(PlayerDataManager.sortPlayerByRoleLU(field));
            awayBench.setValue(PlayerDataManager.sortPlayerByRoleLU(bench));
        } else {
            awayEleven.setValue(field);
            awayBench.setValue(bench);
        }
    }

    public MatchManager buildMatchManager() {
        return match.getValue().getPossesso() == Enums.Possesso.HOME ?
                new MatchManager(homeEleven.getValue(), awayEleven.getValue(), match.getValue(), isLegend) :
                new MatchManager(awayEleven.getValue(), homeEleven.getValue(), match.getValue(), isLegend);
    }

    public void nextAction() {
        match.getValue().setMinuto(minute.getValue());

        switch (match.getValue().getFase()) {
            case PUNIZIONE:
                match.setValue(MatchHelper.punizione(buildMatchManager()));
                break;
            case RIGORE:
                match.setValue(MatchHelper.rigore(buildMatchManager()));
                break;
            case CENTROCAMPO:
                match.setValue(MatchHelper.centrocampo(buildMatchManager()));
                break;
            case ATTACCO:
                match.setValue(MatchHelper.attacco(buildMatchManager()));
                break;
            case CONCLUSIONE:
                match.setValue(MatchHelper.finalizzazione(buildMatchManager()));
                break;
            default:
                match.setValue(MatchHelper.centrocampo(buildMatchManager()));
                break;
        }

        checkCartellino();

    }

    public void checkCartellino() {
        if (match.getValue().getEvento() == Enums.Evento.AMMONIZIONE) {
            ammonizione(match.getValue().getCoprotagonista(),
                    match.getValue().getPossesso() == Enums.Possesso.HOME ?
                    awayRoster.getValue() :
                    homeRoster.getValue());
        }
        if (match.getValue().getEvento() == Enums.Evento.ESPULSIONE) {
            espulsione(match.getValue().getCoprotagonista(),
                    match.getValue().getPossesso() == Enums.Possesso.HOME ?
                            awayRoster.getValue() :
                            homeRoster.getValue());
        }
    }

    public List<PlayerModel> ammonizione(String name, List<PlayerModel> playerModels){
        for (PlayerModel p : playerModels) {
            if (name.equals(p.getName())) {
                if (p.isAmmonito()) {
                    espulsione(name, playerModels);
                } else {
                    p.setAmmonito(true);
                }
            }
        }

        return playerModels;
    }

    public List<PlayerModel> espulsione(String name, List<PlayerModel> playerModels){
        for (PlayerModel p : playerModels) {
            if (name.equals(p.getName())) {
                p.setEspuslo(true);
            }
        }

        return playerModels;
    }

    public MatchInfo getMatchInfo(String team) {
        return new MatchInfo() {
            @Override
            public boolean isLegend() {
                return isLegend;
            }

            @Override
            public int checkMarcatore(PlayerModel player) {
                int num = 0;
                for (MarcatoreModel marcatoreModel : match.getValue().getMarcatori()) {
                    if (marcatoreModel.getName().equalsIgnoreCase(player.getMinifiedName())) {
                        num++;
                    }
                }
                return num;
            }

            @Override
            public String getTeamName() {
                return team;
            }
        };
    }

    public void updateHomeTeam(TeamModel teamModel) {
        homeTeam.setValue(teamModel);
    }

    public void updateAwayTeam(TeamModel teamModel) {
        awayTeam.setValue(teamModel);
    }

    public MutableLiveData<TeamModel> getHomeTeam() {
        return homeTeam;
    }

    public MutableLiveData<TeamModel> getAwayTeam() {
        return awayTeam;
    }

    public MutableLiveData<List<PlayerModel>> getHomeRoster() {
        return homeRoster;
    }

    public MutableLiveData<List<PlayerModel>> getAwayRoster() {
        return awayRoster;
    }

    public MutableLiveData<PlayerModel> getCurrentPlayer() {
        return currentPlayer;
    }

    public MutableLiveData<Integer> getCurrentFocus() {
        return currentFocus;
    }

    public MutableLiveData<Enums.LineUpChoice> getLineUpChoice() {
        return lineUpChoice;
    }

    public MutableLiveData<Integer> getMinute() {
        return minute;
    }

    public boolean isLegend() {
        return isLegend;
    }

    public void setLegend(boolean legend) {
        isLegend = legend;
    }

    public PlayerModel getPlayerSelected() {
        return playerSelected;
    }

    public void setPlayerSelected(PlayerModel playerSelected) {
        this.playerSelected = playerSelected;
    }

    public Enums.MatchModule getModHome() {
        return modHome;
    }

    public void setModHome(Enums.MatchModule modHome) {
        this.modHome = modHome;
    }

    public Enums.MatchModule getModAway() {
        return modAway;
    }

    public void setModAway(Enums.MatchModule modAway) {
        this.modAway = modAway;
    }

    public MutableLiveData<List<PlayerModel>> getHomeEleven() {
        return homeEleven;
    }

    public MutableLiveData<List<PlayerModel>> getAwayEleven() {
        return awayEleven;
    }

    public MutableLiveData<List<PlayerModel>> getHomeBench() {
        return homeBench;
    }

    public MutableLiveData<List<PlayerModel>> getAwayBench() {
        return awayBench;
    }

    public MutableLiveData<MatchModel> getMatch() {
        return match;
    }

    public Enums.MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(Enums.MatchType matchType) {
        this.matchType = matchType;
    }

    public CoachModel getCoachHome() {
        return coachHome;
    }

    public CoachModel getCoachAway() {
        return coachAway;
    }

    public void setCoachHome(CoachModel coachHome) {
        this.coachHome = coachHome;
    }

    public void setCoachAway(CoachModel coachAway) {
        this.coachAway = coachAway;
    }
}
