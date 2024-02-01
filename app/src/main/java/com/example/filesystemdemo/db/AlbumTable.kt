package com.example.filesystemdemo.db

object AlbumTable {

    const val TABLE_ALBUMS = "ALBUMS_SQLITE"

    const val COLUMN_USERID = "USERID"

    const val COLUMN_ID = "ID"

    const val COLUMN_TITLE = "TITLE"

    const val QUERY_STRING = """
            CREATE TABLE IF NOT EXISTS $TABLE_ALBUMS ($COLUMN_USERID TEXT, $COLUMN_ID TEXT, $COLUMN_TITLE TEXT) 
        """
}