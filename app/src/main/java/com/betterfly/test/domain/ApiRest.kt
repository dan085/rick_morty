package com.betterfly.test.domain

class ApiRest {
    companion object {
       const val IP: String = "rickandmortyapi.com"
        const val PROTOCOL: String = "https"
        const val GET_ITEMS = PROTOCOL + "://${IP}/api/character/?page="
    }
}