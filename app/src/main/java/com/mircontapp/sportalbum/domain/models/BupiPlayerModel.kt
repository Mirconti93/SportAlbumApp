package com.mircontapp.sportalbum.domain.models

data class BupiPlayerModel(
    val name: String,
    var team: String,
    val role: Int
)  : GenericModel() {

    override fun getTitle(): String? {
        return name
    }

    override fun getSubtitle(): String? {
        return team
    }

    fun printBupi(): String {
        return name + "_" + team + "_" + role
    }

}
