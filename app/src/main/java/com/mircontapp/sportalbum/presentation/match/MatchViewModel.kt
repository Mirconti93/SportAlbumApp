package com.mircontapp.sportalbum.presentation.match

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
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.findBestPlayerInRole
import com.mircontapp.sportalbum.commons.ext.getLineUpRoles
import com.mircontapp.sportalbum.commons.ext.toPlayerMatchModel
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamLegendUC
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamFromNameUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsForMatchUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//package com.mircontapp.sportalbum.presentation.viewmodels
//
//import android.content.Context
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.mirco.sportalbum.utils.Enums
//import com.mircontapp.sportalbum.SportAlbumApplication
//import com.mircontapp.sportalbum.domain.models.MatchModel
//import com.mircontapp.sportalbum.domain.models.PlayerModel
//import com.mircontapp.sportalbum.domain.models.TeamModel
//

@HiltViewModel
class MatchViewModel @Inject constructor(
    val getPlayersByTeamUC: GetPlayersByTeamUC,
    val getTeamsSuperlegaUC: GetTeamsForMatchUC,
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


    enum class LineUpPlace {
        FIELD, BENCH, TRIBUNE
    }

    enum class Screen {
        LINE_UP_HOME_START, LINE_UP_AWAY_START, MATCH, LINE_UP_HOME, LINE_UP_AWAY
    }

    fun initMatch() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getTeamsSuperlegaUC()
            withContext(Dispatchers.Main) {
                teams.value = list
                if (teams.value.isNotEmpty()) {
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
                val list = getPlayersByTeamUC(homeTeam.value!!)
                withContext(Dispatchers.Main) {
                    _homeRoster.value = list.filter {  it.value != null && it.value > 50 } .toMutableList()
                    initOnFieledOrBench(Enums.TeamPosition.HOME)
                }
            }.invokeOnCompletion {
                viewModelScope.launch(Dispatchers.IO) {
                    val list = getPlayersByTeamUC(awayTeam.value!!)
                    withContext(Dispatchers.Main) {
                        _awayRoster.value = list.filter {  it.value != null && it.value > 50 } .toMutableList()
                        Log.i("BUPI", "INIT ROSTER")
                        awayRoster.value?.forEach {
                            Log.i("BUPI", it. name)
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
            Log.i("BUPI", it. name)
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
            Enums.Fase.CENTROCAMPO -> CentrocampoUC().invoke(matchModel.value)
            Enums.Fase.ATTACCO -> AttaccoUC().invoke(matchModel.value)
            Enums.Fase.CONCLUSIONE -> ConclusioneUC().invoke(matchModel.value)
            Enums.Fase.PUNIZIONE -> PunizioneUC().invoke(matchModel.value)
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

    private fun updateEnergy(players: List<PlayerMatchModel>) : MutableList<PlayerMatchModel>{
        return players.toMutableList().also { list->
            list.forEach {
                it.energy -= 1 - (it.fis/200.0) - ((it.fis/200.0)*Math.random())
                //Log.i("BUPI MIN PLAYED", it.name + " " + it.energy.toString())
            }
        }
    }

}










//    private var coachHome: CoachModel? = null
//    private var coachAway: CoachModel? = null
//    private var modHome: Enums.MatchModule? = null
//    private var modAway: Enums.MatchModule? = null
//    private val match: MutableLiveData<MatchModel>
//
//    init {
//        homeTeam = MutableLiveData()
//        awayTeam = MutableLiveData()
//        homeRoster = MutableLiveData()
//        awayRoster = MutableLiveData()
//        currentPlayer = MutableLiveData()
//        currentFocus = MutableLiveData()
//        lineUpChoice = MutableLiveData<Enums.LineUpChoice>()
//        minute = MutableLiveData()
//        match = MutableLiveData<MatchModel>()
//        updateHomeTeam(TeamDataManager.teamFromName("Atalanta"))
//        updateAwayTeam(TeamDataManager.teamFromName("Inter"))
//        isLegend = false
//        matchType = Enums.MatchType.SIMPLE_MATCH
//        minute.value = 0
//        currentFocus.value = HOME
//        lineUpChoice.setValue(Enums.LineUpChoice.FIELD)
//        homeEleven = MutableLiveData()
//        awayEleven = MutableLiveData()
//        homeBench = MutableLiveData()
//        awayBench = MutableLiveData()
//    }
//
//    fun buildTeamsPlayers(context: Context?) {
//        coachHome =
//            TeamDataManager.coachByName(if (isLegend) homeTeam.value.getCoachlegend() else homeTeam.value!!.coach)
//        coachAway =
//            TeamDataManager.coachByName(if (isLegend) awayTeam.value.getCoachlegend() else awayTeam.value!!.coach)
//        modHome = coachHome.getMatchModule(app.baseContext)
//        modAway = coachAway.getMatchModule(app.baseContext)
//        /*** home roster  */
//        if (homeTeam.value!!.area.getArea()
//                .equals(Enums.Area.NAZIONALI) || homeTeam.value!!.area.getArea()
//                .equals(Enums.Area.NAZIONALIFEMMINILI)
//        ) {
//            val gender: Enums.Gender = if (homeTeam.value!!.area.getArea()
//                    .equals(Enums.Area.NAZIONALIFEMMINILI)
//            ) Enums.Gender.F else Enums.Gender.M
//            homeRoster.setValue(
//                if (isLegend) PlayerDataManager.playersFromNationalLegend(
//                    homeTeam.value.getNation(),
//                    gender
//                ) else PlayerDataManager.playersFromNational(homeTeam.value.getNation(), gender)
//            )
//        } else {
//            homeRoster.setValue(
//                if (isLegend) PlayerDataManager.playersFromTeamLegend(homeTeam.value!!.name) else PlayerDataManager.playersFromTeam(
//                    homeTeam.value!!.name
//                )
//            )
//        }
//        /*** away roster  */
//        if (awayTeam.value!!.area.getArea()
//                .equals(Enums.Area.NAZIONALI) || awayTeam.value!!.area.getArea()
//                .equals(Enums.Area.NAZIONALIFEMMINILI)
//        ) {
//            val gender: Gender = if (awayTeam.value!!.area.getArea()
//                    .equals(Enums.Area.NAZIONALIFEMMINILI)
//            ) Enums.Gender.F else Enums.Gender.M
//            awayRoster.setValue(
//                if (isLegend) PlayerDataManager.playersFromNationalLegend(
//                    awayTeam.value.getNation(),
//                    gender
//                ) else PlayerDataManager.playersFromNational(awayTeam.value.getNation(), gender)
//            )
//        } else {
//            awayRoster.setValue(
//                if (isLegend) PlayerDataManager.playersFromTeamLegend(awayTeam.value!!.name) else PlayerDataManager.playersFromTeam(
//                    awayTeam.value!!.name
//                )
//            )
//        }
//        /*** home stats  */
//        //homeRoster.setValue(PlayerDataManager.loadStatsTeam(context, homeRoster.getValue()));
//        /*** away stats  */
//        //awayRoster.setValue(PlayerDataManager.loadStatsTeam(context, awayRoster.getValue()));
//        homeLUManager =
//            LineUpDataManager(homeRoster.value, coachHome.getMatchModule(app.baseContext))
//        awayLUManager =
//            LineUpDataManager(awayRoster.value, coachAway.getMatchModule(app.baseContext))
//        homeRoster.setValue(homeLUManager.composeLineUp())
//        awayRoster.setValue(awayLUManager.composeLineUp())
//        homeOnFieledOrBench(true)
//        awayOnFieledOrBench(true)
//        match.setValue(MatchModel(homeTeam.value, awayTeam.value))
//    }
//
//    @JvmOverloads
//    fun checkSelection(playerModel: PlayerModel, sort: Boolean = true) {
//        if (playerModel.isEspuslo()) {
//            return
//        }
//        if (playerSelected != null) {
//            playerModel.setSelected(false)
//            playerSelected.setSelected(false)
//            val role1: RoleLineUp? = playerSelected!!.roleLineUp
//            val role2: RoleLineUp? = playerModel.roleLineUp
//            playerModel.setRoleLineUp(role1)
//            playerSelected.setRoleLineUp(role2)
//            if (currentFocus.value == HOME) {
//                val i1 = homeRoster.value!!.indexOf(playerSelected!!)
//                val i2 = homeRoster.value!!.indexOf(playerModel)
//                Collections.swap(homeRoster.value, i1, i2)
//            } else {
//                val i1 = awayRoster.value!!.indexOf(playerSelected!!)
//                val i2 = awayRoster.value!!.indexOf(playerModel)
//                Collections.swap(awayRoster.value, i1, i2)
//            }
//            playerSelected = null
//        } else {
//            playerSelected = playerModel
//            playerSelected.setSelected(true)
//        }
//        if (currentFocus.value == HOME) {
//            homeRoster.setValue(homeRoster.value)
//            homeOnFieledOrBench(sort)
//        } else {
//            awayRoster.setValue(awayRoster.value)
//            awayOnFieledOrBench(sort)
//        }
//    }
//
//    fun changeModule(matchModule: MatchModule?) {
//        if (currentFocus.value == HOME) {
//            modHome = matchModule
//            homeEleven.setValue(LineUpDataManager.changeModule(homeEleven.value, matchModule))
//        } else {
//            modAway = matchModule
//            awayEleven.setValue(LineUpDataManager.changeModule(awayEleven.value, matchModule))
//        }
//    }
//

//
//    fun buildMatchManager(): MatchManager {
//        return if (match.getValue().getPossesso() === Enums.Possesso.HOME) MatchManager(
//            homeEleven.value,
//            awayEleven.value,
//            match.getValue(),
//            isLegend
//        ) else MatchManager(awayEleven.value, homeEleven.value, match.getValue(), isLegend)
//    }
//
//    fun nextAction() {
//        match.getValue().setMinuto(minute.value)
//        when (match.getValue().getFase()) {
//            PUNIZIONE -> match.setValue(MatchHelper.punizione(buildMatchManager()))
//            RIGORE -> match.setValue(MatchHelper.rigore(buildMatchManager()))
//            CENTROCAMPO -> match.setValue(MatchHelper.centrocampo(buildMatchManager()))
//            ATTACCO -> match.setValue(MatchHelper.attacco(buildMatchManager()))
//            CONCLUSIONE -> match.setValue(MatchHelper.finalizzazione(buildMatchManager()))
//            else -> match.setValue(MatchHelper.centrocampo(buildMatchManager()))
//        }
//        checkCartellino()
//    }
//
//    fun checkCartellino() {
//        if (match.getValue().getEvento() === Enums.Evento.AMMONIZIONE) {
//            ammonizione(
//                match.getValue().getCoprotagonista(),
//                if (match.getValue()
//                        .getPossesso() === Enums.Possesso.HOME
//                ) awayRoster.value else homeRoster.value
//            )
//        }
//        if (match.getValue().getEvento() === Enums.Evento.ESPULSIONE) {
//            espulsione(
//                match.getValue().getCoprotagonista(),
//                if (match.getValue()
//                        .getPossesso() === Enums.Possesso.HOME
//                ) awayRoster.value else homeRoster.value
//            )
//        }
//    }
//
//    fun ammonizione(name: String, playerModels: List<PlayerModel>?): List<PlayerModel>? {
//        for (p in playerModels!!) {
//            if (name == p.name) {
//                if (p.isAmmonito()) {
//                    espulsione(name, playerModels)
//                } else {
//                    p.setAmmonito(true)
//                }
//            }
//        }
//        return playerModels
//    }
//
//    fun espulsione(name: String, playerModels: List<PlayerModel>?): List<PlayerModel>? {
//        for (p in playerModels!!) {
//            if (name == p.name) {
//                p.setEspuslo(true)
//            }
//        }
//        return playerModels
//    }
//
//    fun getMatchInfo(team: String): MatchInfo {
//        return object : MatchInfo() {
//            val isLegend: Boolean
//            fun checkMarcatore(player: PlayerModel): Int {
//                var num = 0
//                for (marcatoreModel in match.getValue().getMarcatori()) {
//                    if (marcatoreModel.getName().equalsIgnoreCase(player.getMinifiedName())) {
//                        num++
//                    }
//                }
//                return num
//            }
//
//            val teamName: String
//                get() = team
//        }
//    }
//
//    fun updateHomeTeam(teamModel: TeamModel) {
//        homeTeam.value = teamModel
//    }
//
//    fun updateAwayTeam(teamModel: TeamModel) {
//        awayTeam.value = teamModel
//    }
//
//    fun getLineUpChoice(): MutableLiveData<LineUpChoice> {
//        return lineUpChoice
//    }
//
//    fun getModHome(): MatchModule? {
//        return modHome
//    }
//
//    fun setModHome(modHome: MatchModule?) {
//        this.modHome = modHome
//    }
//
//    fun getModAway(): MatchModule? {
//        return modAway
//    }
//
//    fun setModAway(modAway: MatchModule?) {
//        this.modAway = modAway
//    }
//
//    fun getMatch(): MutableLiveData<MatchModel> {
//        return match
//    }
//
//    fun getMatchType(): Enums.MatchType {
//        return matchType
//    }
//
//    fun setMatchType(matchType: Enums.MatchType) {
//        this.matchType = matchType
//    }
//
//    fun getCoachHome(): CoachModel? {
//        return coachHome
//    }
//
//    fun getCoachAway(): CoachModel? {
//        return coachAway
//    }
//
//    fun setCoachHome(coachHome: CoachModel?) {
//        this.coachHome = coachHome
//    }
//
//    fun setCoachAway(coachAway: CoachModel?) {
//        this.coachAway = coachAway
//    }
//


