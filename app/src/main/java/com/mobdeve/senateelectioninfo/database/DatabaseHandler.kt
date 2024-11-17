package com.mobdeve.senateelectioninfo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AppDatabase"
        const val ACCOUNT_TABLE = "account_table"
        const val ACCOUNT_ID = "account_id"
        const val ACCOUNT_USERNAME = "account_username"
        const val ACCOUNT_PASSWORD = "account_password"
    }

    // Handles creation of the database
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_ACCOUNT_TABLE = "CREATE TABLE $ACCOUNT_TABLE" +
                "($ACCOUNT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ACCOUNT_USERNAME TEXT, " +
                "$ACCOUNT_PASSWORD TEXT)"
        db?.execSQL(CREATE_ACCOUNT_TABLE)

        val initialData = listOf(
            arrayOf("john", "1234"),
            arrayOf("jane", "1234"),
            arrayOf("test", "1234"),
            arrayOf("admin", "1234"),
            arrayOf("kendrick", "1234")
        )

        for (data in initialData) {
            val values = ContentValues().apply {
                put(ACCOUNT_USERNAME, data[0])
                put(ACCOUNT_PASSWORD, data[1])
            }
            db?.insert(ACCOUNT_TABLE, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $ACCOUNT_TABLE")
        onCreate(db)
    }
}