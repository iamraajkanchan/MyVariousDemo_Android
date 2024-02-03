package com.example.filesystemdemo.db

import android.annotation.SuppressLint
import com.example.filesystemdemo.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AppSQLDatabaseImpl(private val database: AppSQLDatabase) {
    suspend fun insertAlbum(albums: List<Album>) {
        val queryString = """
            INSERT INTO ${AlbumTable.TABLE_ALBUMS} (${AlbumTable.COLUMN_USERID}, ${AlbumTable.COLUMN_ID}, ${AlbumTable.COLUMN_TITLE}) VALUES (?,?,?)
        """.trimIndent()
        val db = database.writableDatabase
        try {
            if (db.isOpen) {
                for (album in albums) {
                    db.execSQL(queryString, arrayOf(album.userId, album.id, album.title))
                }
            }
        } catch (e: Exception) {
            executeTaskOnCoroutine { e.printStackTrace() }
        } finally {
            executeTaskOnCoroutine { db.close() }
        }
    }

    @SuppressLint("Range")
    suspend fun getAlbums(): Flow<List<Album>> = flow {
        val albums = mutableListOf<Album>()
        val db = database.readableDatabase
        try {
            if (db.isOpen) {
                val cursor = db.query(AlbumTable.TABLE_ALBUMS, null, null, null, null, null, null)
                if (cursor.moveToFirst()) {
                    do {
                        val userId = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_USERID))
                        val id = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ID))
                        val title = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_TITLE))
                        albums.add(Album(userId, id, title))
                    } while (cursor.moveToNext())
                }
                executeTaskOnCoroutine { cursor.close() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executeTaskOnCoroutine { db.close() }
        }
        emit(albums)
    }

    private suspend fun executeTaskOnCoroutine(func: () -> Unit) {
        coroutineScope {
            launch(Dispatchers.IO) {
                func.invoke()
                println("AppSQLDatabaseImpl :: executeTaskOnCoroutine :: Thread : ${Thread.currentThread().name}")
            }
        }
    }

}