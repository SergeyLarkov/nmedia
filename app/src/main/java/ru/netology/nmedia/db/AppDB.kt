package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.db.PostDaoImpl.PostColumns.DDL

class AppDb private constructor(db: SQLiteDatabase) {
    val postDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDb(context, arrayOf(DDL))
                ).also { instance = it }
            }
        }

        private fun buildDb(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }

}