package com.rgos_developer.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rgos_developer.convidados.constants.DataBaseConstants


class GuestDataBase(
    context: Context,
) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 1
    }

    //é executado somente uma vez
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + " (" +
                    DataBaseConstants.GUEST.COLLUMNS.ID + " integer primary key autoincrement," +
                    DataBaseConstants.GUEST.COLLUMNS.NAME + " text," +
                    DataBaseConstants.GUEST.COLLUMNS.PRESENCE + " integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}