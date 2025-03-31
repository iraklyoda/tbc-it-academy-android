package com.iraklyoda.transferapp.domain.common.enum

enum class Currency(private val currencySymbol: String) {
    USD("$"),
    GEL("₾"),
    EUR("€"),
    UNKNOWN("");

    fun getSymbol(): String {
        return currencySymbol
    }
}