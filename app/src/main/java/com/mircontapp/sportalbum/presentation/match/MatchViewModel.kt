import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import dagger.hilt.android.lifecycle.HiltViewModel

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
class MatchViewModel : ViewModel() {
    var app = SportAlbumApplication.instance
    val homeTeam: MutableLiveData<TeamModel> = MutableLiveData()
    val awayTeam: MutableLiveData<TeamModel> = MutableLiveData()
    val homeRoster: MutableLiveData<List<PlayerModel>?> = MutableLiveData()
    val awayRoster: MutableLiveData<List<PlayerModel>?> = MutableLiveData()
    var currentPlayer: MutableLiveData<PlayerModel> = MutableLiveData()
    var currentFocus: MutableLiveData<Int> = MutableLiveData()
//    private val lineUpChoice: MutableLiveData<Enums.LineUpChoice>
//    val minute: MutableLiveData<Int>
//    private var homeLUManager: LineUpDataManager? = null
//    private var awayLUManager: LineUpDataManager? = null
//    val homeEleven: MutableLiveData<List<PlayerModel>>
//    val awayEleven: MutableLiveData<List<PlayerModel>>
//    val homeBench: MutableLiveData<List<PlayerModel>>
//    val awayBench: MutableLiveData<List<PlayerModel>>
//    var isLegend: Boolean
//    private var matchType: Enums.MatchType
//    var playerSelected: PlayerModel? = null
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
//    /*** split players on field or in bench  */
//    fun homeOnFieledOrBench(sort: Boolean) {
//        val field: MutableList<PlayerModel> = ArrayList()
//        val bench: MutableList<PlayerModel> = ArrayList()
//        if (homeRoster.value != null) {
//            val size = homeRoster.value!!.size
//            //n di titolari 11 o meno se non ce ne sono 11
//            val starting = Math.min(11, size)
//            for (i in 0 until size) {
//                val p = homeRoster.value!![i]
//                if (i < starting) {
//                    field.add(p)
//                } else {
//                    bench.add(p)
//                }
//            }
//        }
//        if (sort) {
//            homeEleven.setValue(PlayerDataManager.sortPlayerByRoleLU(field))
//            homeBench.setValue(PlayerDataManager.sortPlayerByRoleLU(bench))
//        } else {
//            homeEleven.setValue(field)
//            homeBench.setValue(field)
//        }
//    }
//
//    fun awayOnFieledOrBench(sort: Boolean) {
//        val field: MutableList<PlayerModel> = ArrayList()
//        val bench: MutableList<PlayerModel> = ArrayList()
//        if (awayRoster.value != null) {
//            val size = awayRoster.value!!.size
//            val starting = Math.min(11, size)
//            Math.min(11, size)
//            for (i in 0 until size) {
//                val p = awayRoster.value!![i]
//                if (i < starting) {
//                    field.add(p)
//                } else {
//                    bench.add(p)
//                }
//            }
//        }
//        if (sort) {
//            awayEleven.setValue(PlayerDataManager.sortPlayerByRoleLU(field))
//            awayBench.setValue(PlayerDataManager.sortPlayerByRoleLU(bench))
//        } else {
//            awayEleven.setValue(field)
//            awayBench.setValue(bench)
//        }
//    }
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
    companion object {
        const val HOME = 0
        const val AWAY = 1
    }
}