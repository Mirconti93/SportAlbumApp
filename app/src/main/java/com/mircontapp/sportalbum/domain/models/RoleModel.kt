package com.example.bupialbum.models

data class RoleModel (
    val name: String,
    val general: String,
    val pos: Int
) : GenericModel () {
    override fun getTitle(): String? {
        return name
    }

    override fun getSubtitle(): String? {
        return general
    }
}