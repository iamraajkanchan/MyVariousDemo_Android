package com.example.filesystemdemo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSQLDatabase (
    private val queryString: String,
    context: Context,
    dbName: String,
    version: Int
) :
    SQLiteOpenHelper(context, dbName, null, version) {
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(queryString)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}