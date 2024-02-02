package com.example.filesystemdemo.db

import android.annotation.SuppressLint
import com.example.filesystemdemo.model.Album

class AppSQLDatabaseImpl (private val database: AppSQLDatabase) {
    fun insertAlbum(albums: List<Album>) {
        val queryString = """
            INSERT INTO ${AlbumTable.TABLE_ALBUMS} (${AlbumTable.COLUMN_USERID}, ${AlbumTable.COLUMN_ID}, "${AlbumTable.COLUMN_TITLE}") VALUES (?,?,?)
        """.trimIndent()
        val db = database.writableDatabase
        for (album in albums) {
            db.execSQL(queryString, arrayOf(album.userId, album.id, album.title))
        }
        db.close()
    }

    @SuppressLint("Range")
    fun getAlbums(): List<Album> {
        val albums = mutableListOf<Album>()
        val db = database.readableDatabase
        val cursor = db.query(AlbumTable.TABLE_ALBUMS, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_USERID))
                val id = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_TITLE))
                albums.add(Album(userId, id, title))
            } while (cursor.moveToNext())
        }
        db.close()
        return albums
    }

}