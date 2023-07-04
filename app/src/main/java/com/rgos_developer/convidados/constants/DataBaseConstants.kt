package com.rgos_developer.convidados.constants

class DataBaseConstants private constructor(){
    object GUEST{
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }

    }
}