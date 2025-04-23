package com.mircontapp.sportalbum.presentation.match.match_start

import AttaccoUC
import CentrocampoUC
import ConclusioneUC
import PunizioneUC
import RigoreUC
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.findBestPlayerInRole
import com.mircontapp.sportalbum.commons.ext.getLineUpRoles
import com.mircontapp.sportalbum.commons.ext.toPlayerMatchModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamLegendUC
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamFromNameUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsForMatchUC
import com.mircontapp.sportalbum.presentation.match.updateEnergy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchStartViewModel @Inject constructor(
    val getTeamsFromAreaUC: GetTeamsFromAreaUC,
    val getPlayersByTeamUC: GetPlayersByTeamUC,
    val getPlayersByTeamLegendUC: GetPlayersByTeamLegendUC,
    val getTeamsSuperlegaUC: GetTeamsForMatchUC,
    val getTeamFromNameUC: GetTeamFromNameUC
) : ViewModel() {
    var app = SportAlbumApplication.instance
    val homeTeam: MutableLiveData<TeamModel> = MutableLiveData()
    val awayTeam: MutableLiveData<TeamModel> = MutableLiveData()

    private val _homeRoster = MutableLiveData<MutableList<PlayerModel>>()
    val homeRoster: LiveData<MutableList<PlayerModel>> = _homeRoster
    private val _awayRoster = MutableLiveData<MutableList<PlayerModel>>()
    val awayRoster: LiveData<MutableList<PlayerModel>> = _awayRoster

    val currentPlayer: MutableLiveData<PlayerModel> = MutableLiveData()
    val currentFocus: MutableLiveData<Int> = MutableLiveData()
    val lineUpChoice: MutableLiveData<Enums.LineUpChoice> = MutableLiveData()
    val minute: MutableLiveData<Int> = MutableLiveData()
//    private var homeLUManager: LineUpDataManager? = null
//    private var awayLUManager: LineUpDataManager? = null
    val homeEleven: MutableStateFlow<List<PlayerMatchModel>> = MutableStateFlow(emptyList())
    val awayEleven: MutableStateFlow<List<PlayerMatchModel>> = MutableStateFlow(emptyList())
    val homeBench: MutableStateFlow<List<PlayerMatchModel>> = MutableStateFlow(emptyList())
    val awayBench: MutableStateFlow<List<PlayerMatchModel>> = MutableStateFlow(emptyList())
    var isLegend: Boolean = true
    var matchType: Enums.MatchType = Enums.MatchType.SIMPLE_MATCH
    val playerSelected: MutableStateFlow<PlayerMatchModel?> = MutableStateFlow(null)
    var playerToChangeRole: PlayerMatchModel? = null
    var firstPlayerSelected: Boolean = false
    val showRoleSelection = MutableStateFlow(false)

    var teamPosition = Enums.TeamPosition.HOME
    val teams = mutableStateOf<List<TeamModel>>(emptyList())
    val showSelection = mutableStateOf(false)
    val currentScreen  = mutableStateOf(Screen.LINE_UP_HOME_START)

    val matchModel: MutableStateFlow<MatchModel> = MutableStateFlow(initMatchModel())

    private val _state = MutableStateFlow(MatchStartState())
    val state: StateFlow<MatchStartState> get() = _state


    enum class LineUpPlace {
        FIELD, BENCH, TRIBUNE
    }

    enum class Screen {
        LINE_UP_HOME_START, LINE_UP_AWAY_START, MATCH, LINE_UP_HOME, LINE_UP_AWAY
    }

    init {
        viewModelScope.launch {
            val list = getTeamsSuperlegaUC()
            withContext(Dispatchers.Main) {
                teams.value = list
                if (teams.value.size > 0) {
                    homeTeam.value = teams.value[0]
                }
                if (teams.value.size > 1) {
                    awayTeam.value = teams.value[1]
                }
            }
        }
    }

    fun onAction(action: MatchStartAction) {
        when (action) {
            is MatchStartAction.Load -> _state.value = MatchStartState(isLoading = true)
            is MatchStartAction.SelectHomeTeam -> {
                _state.value = MatchStartState(homeTeam = action.team)
            }
            is MatchStartAction.SelectAwayTeam-> {
                _state.value = MatchStartState(awayTeam = action.team)
            }
        }
    }

    fun initMatch() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getTeamsSuperlegaUC()
            withContext(Dispatchers.Main) {
                teams.value = list
                if (teams.value.size > 0) {
                    homeTeam.value = teams.value[0]
                }
                if (teams.value.size > 1) {
                    awayTeam.value = teams.value[1]
                }
            }
        }
    }

    fun nextScreen() {
        val screen = when (currentScreen.value) {
            Screen.LINE_UP_HOME_START -> Screen.LINE_UP_AWAY_START
            Screen.LINE_UP_AWAY_START -> {
                matchModel.value.home = homeTeam.value?.name ?: ""
                matchModel.value.away = awayTeam.value?.name ?: ""
                updatePlayersInMatch()
                Screen.MATCH
            }
            Screen.LINE_UP_HOME -> Screen.MATCH
            Screen.LINE_UP_AWAY -> Screen.MATCH
            else -> Screen.MATCH
        }
        Log.i("BUPI", screen.toString())
        currentScreen.value = screen
    }

    private fun updatePlayersInMatch() {
        matchModel.value.let {
            it.playersHome = homeEleven.value.toMutableList()
            it.playersAway= awayEleven.value.toMutableList()
        }
    }

    private fun initMatchModel(): MatchModel {
        return MatchModel(
            homeTeam.value?.name ?: "",
            awayTeam.value?.name ?: "",
            0,
            0,
            0,
            Enums.TeamPosition.HOME,
            Enums.Fase.CENTROCAMPO,
            Enums.Evento.NONE,
            false,
            ArrayList(),
            ArrayList(),
            ArrayList(),
            null,
            null,
            ArrayList()
        )
    }


    fun initLineUp(homeT: TeamModel?, awayT: TeamModel?) {
        if (homeT != null && awayT != null) {
            this.homeTeam.value = homeT
            this.awayTeam.value = awayT
            viewModelScope.launch(Dispatchers.IO) {
                val list = getPlayersByTeamLegendUC(homeTeam.value!!)
                withContext(Dispatchers.Main) {
                    _homeRoster.value =
                        list.filter { it.value != null && it.value > 50 }.toMutableList()
                    initOnFieledOrBench(Enums.TeamPosition.HOME)
                }
            }.invokeOnCompletion {
                viewModelScope.launch(Dispatchers.IO) {
                    val list = getPlayersByTeamLegendUC(awayTeam.value!!)
                    withContext(Dispatchers.Main) {
                        _awayRoster.value =
                            list.filter { it.value != null && it.value > 50 }.toMutableList()
                        Log.i("BUPI", "INIT ROSTER")
                        awayRoster.value?.forEach {
                            Log.i("BUPI", it.name)
                        }
                        initOnFieledOrBench(Enums.TeamPosition.AWAY)
                    }
                }
            }
        }

    }

    /*** split players on field or in bench  */
    fun initOnFieledOrBench(teamPosition: Enums.TeamPosition) {
        val teamIsHome = teamPosition == Enums.TeamPosition.HOME

        val field: MutableList<PlayerMatchModel> = ArrayList()
        val roster: MutableList<PlayerMatchModel> = ArrayList()
        if (teamIsHome)  {
            homeRoster.value?.forEach {
                roster.add(it.toPlayerMatchModel(isLegend))
            }
        } else {
            awayRoster.value?.forEach {
                roster.add(it.toPlayerMatchModel(isLegend))
            }
        }

        val module = if (teamIsHome) homeTeam.value?.module else awayTeam.value?.module ?: Enums.MatchModule.M442
        val roles = module.getLineUpRoles()

        for (roleLineUp in roles) {
            roster.findBestPlayerInRole(roleLineUp, isLegend )?.let {playerModel->
                playerModel.let {
                    it.roleMatch = roleLineUp
                    field.add(it)
                    roster.remove(it)
                }
            }
        }

        roster.forEach {
            it.roleMatch = Enums.RoleLineUp.PAN
        }

        val bench = roster.sortedBy { it.roleLineUp } ?: emptyList()

        if (teamIsHome) {
            homeEleven.value = field
            homeBench.value = bench
        } else {
            awayEleven.value = field
            awayBench.value = bench
        }

    }

    fun changeModule(teamPosition: Enums.TeamPosition, module: Enums.MatchModule) {
        val players = ArrayList<PlayerMatchModel>()
        val roles = module.getLineUpRoles()
        if (teamPosition == Enums.TeamPosition.HOME) {
            players.addAll(homeEleven.value)
        } else {
            players.addAll(awayEleven.value)
        }
        for (i in 0..players.size) {
            if (i<roles.size) {
                val player = players.get(i)
                val role = roles[i]
                player.roleMatch = role
            }

        }

        if (teamPosition == Enums.TeamPosition.HOME) {
            homeEleven.value = players
        } else {
            awayEleven.value = players
        }

    }

    fun substitutePlayer(player1: PlayerMatchModel, player2: PlayerMatchModel, teamPosition: Enums.TeamPosition) {
        val teamIsHome = teamPosition == Enums.TeamPosition.HOME

        if (player1.roleMatch == Enums.RoleLineUp.PAN && player2.roleMatch == Enums.RoleLineUp.PAN) return

        val roleMatch1 = player2.roleMatch
        val roleMatch2 = player1.roleMatch
        player1.roleMatch = roleMatch1
        player2.roleMatch = roleMatch2

        var eleven: MutableList<PlayerMatchModel> = ArrayList()
        var bench: MutableList<PlayerMatchModel> = ArrayList()
        if (teamIsHome) {
            eleven.addAll(homeEleven.value)
            bench.addAll(homeBench.value)
        } else {
            eleven.addAll(awayEleven.value)
            bench.addAll(awayBench.value)
        }

        if (eleven.contains(player1) && bench.contains(player2)) {
            eleven.add(player2)
            eleven.remove(player1)
            bench.add(player1)
            bench.remove(player2)
        } else if (bench.contains(player1) && eleven.contains(player2)) {
            eleven.add(player1)
            eleven.remove(player2)
            bench.add(player2)
            bench.remove(player1)
        }

        Log.i("BUPI", "Eleven")
        awayEleven.value?.forEach {
            Log.i("BUPI", it.name)
        }

        eleven = (eleven.sortedBy { it.roleMatch }?.toMutableList() ?: emptyList()).toMutableList()
        bench = (bench.sortedBy { it.roleLineUp }?.toMutableList() ?: emptyList()).toMutableList()

        if (teamIsHome) {
            homeEleven.value = eleven
            homeBench.value = bench
        } else {
            awayEleven.value = eleven
            awayBench.value = bench
        }
        playerSelected.value = null
    }

    fun changePlayerRole(teamPosition: Enums.TeamPosition) {
        val players: MutableList<PlayerMatchModel> = ArrayList()
        players.addAll(homeEleven.value)
        if (teamPosition == Enums.TeamPosition.HOME) {
            for (p in players) {
                playerToChangeRole?.let {
                    if (p.name == it?.name) {
                        p.roleMatch = it.roleMatch
                    }
                }
            }
        }
        homeEleven.value = players
    }

    fun nextAction() {
        val match = when (matchModel.value.fase) {
            Enums.Fase.CENTROCAMPO -> CentrocampoUC().centrocampo(matchModel.value)
            Enums.Fase.ATTACCO -> AttaccoUC().attacco(matchModel.value)
            Enums.Fase.CONCLUSIONE -> ConclusioneUC().conclusione(matchModel.value)
            Enums.Fase.PUNIZIONE -> PunizioneUC().punizione(matchModel.value)
            Enums.Fase.RIGORE -> RigoreUC().rigoreDiretto(matchModel.value)
        }

        matchModel.value = MatchModel(
            match.home,
            match.away,
            match.homeScore,
            match.awayScore,
            match.minute + 1,
            match.possesso,
            match.fase,
            match.evento,
            match.isLegend,
            updateEnergy(homeEleven.value),
            updateEnergy(awayEleven.value),
            match.comment,
            match.protagonista,
            match.coprotagonista,
            match.marcatori
        )

    }


}