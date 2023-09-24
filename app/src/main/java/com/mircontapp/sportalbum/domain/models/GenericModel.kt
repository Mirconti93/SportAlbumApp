package com.example.bupialbum.models

abstract class GenericModel {
    private var order = 0

    abstract fun getTitle(): String?

    abstract fun getSubtitle(): String?

    fun getOrder(): Int {
        return order
    }

    fun setOrder(order: Int) {
        this.order = order
    }

    fun write(): String? {
        return getTitle() + "_" + getOrder()
    }

}