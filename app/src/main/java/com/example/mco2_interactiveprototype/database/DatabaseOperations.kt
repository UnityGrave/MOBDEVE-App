package com.example.mco2_interactiveprototype.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.mco2_interactiveprototype.model.AccountItem
import android.content.Context

class DatabaseOperations(context: Context) {
    // A private instance of the DB helper
    private lateinit var databaseHandler : DatabaseHandler

    // Initializes the databaseHandler instance using the context provided.
    init {
        this.databaseHandler = DatabaseHandler(context)
    }

    // Inserts an account item into the database. Returns the id provided by the DB.
    fun addAccount(account: AccountItem) : Int {
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.ACCOUNT_USERNAME, account.username)
        contentValues.put(DatabaseHandler.ACCOUNT_PASSWORD, account.password)

        val _id = db.insert(DatabaseHandler.ACCOUNT_TABLE, null, contentValues)

        db.close()

        return _id.toInt()
    }

    fun updateAccount(account: AccountItem) {
        val db = databaseHandler.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHandler.ACCOUNT_USERNAME, account.username)
            put(DatabaseHandler.ACCOUNT_PASSWORD, account.password)
        }

        db.update(DatabaseHandler.ACCOUNT_TABLE, contentValues, "${DatabaseHandler.ACCOUNT_ID} = ?",
            arrayOf(account.accountID.toString()))

        db.close()
    }

    fun deleteAccount(account: AccountItem) {
        val db = databaseHandler.writableDatabase

        db.delete(DatabaseHandler.ACCOUNT_TABLE, "${DatabaseHandler.ACCOUNT_ID} = ?",
            arrayOf(account.accountID.toString()))

        db.close()
    }

    fun getAccountList(): ArrayList<AccountItem>{
        val result = ArrayList<AccountItem>()
        val selectQuery = "Select * " + " FROM ${DatabaseHandler.ACCOUNT_TABLE}"
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.close()
            return ArrayList()
        }
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val username = cursor.getString(1)
                val password = cursor.getString(2)

                result.add(AccountItem(id, username, password))
            } while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return result
    }
}