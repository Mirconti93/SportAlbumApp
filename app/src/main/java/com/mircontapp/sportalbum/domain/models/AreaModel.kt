package com.example.bupialbum.models

import com.mirco.sportalbum.utils.Enums

data class AreaModel(val area: Enums.Area, val name: String?) {

    fun AreaModel(row: String) {
        val field = row.split("_".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        if (field.size >= 2) {
            //todo name = field[0]
            //todo area = DataManager.findAreaEnum(field[1])
        }
    }

    /*fun parse(): DataOperator<AreaModel?>? {
        return object : DataOperator<AreaModel?>() {
            fun parseObject(row: String): AreaModel? {
                return AreaModel(row)
            }

            fun writeObject(): String? {
                return null
            }
        }
    }*/

    /*override fun equals(obj: Any?): Boolean {
        return if (obj != null && obj is AreaModel) {
            //todo area == obj.getArea()
        } else false
    }*/

    fun getTitle(): String? {
        return name
    }
}