package com.rgos_developer.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.rgos_developer.convidados.constants.DataBaseConstants
import com.rgos_developer.convidados.model.GuestModel
import java.lang.Exception

class GuestRepository constructor(context: Context) {
    private val guestDatabase = GuestDataBase(context)

    //singleton é um padrão de projeto, controla o acesso as intancias das classes
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDatabase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        try {
            val db = guestDatabase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLLUMNS.ID,
                DataBaseConstants.GUEST.COLLUMNS.NAME,
                DataBaseConstants.GUEST.COLLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.PRESENCE))

                    guest =  GuestModel(id, name, presence == 1)
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return guest
        }

        return guest
    }

    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLLUMNS.ID,
                DataBaseConstants.GUEST.COLLUMNS.NAME,
                DataBaseConstants.GUEST.COLLUMNS.PRESENCE
            )

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 0))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }
}