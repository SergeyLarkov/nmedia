package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getLongOrNull
import ru.netology.nmedia.data.Post
import java.util.*

class PostDaoImpl(private var db: SQLiteDatabase): PostDao {

    companion object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "postid"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_POSTDATE = "postdate"
        const val COLUMN_POSTTEXT = "posttext"
        const val COLUMN_URLVIDEO = "urlvideo"
        const val COLUMN_LIKED = "liked"
        const val COLUMN_LIKESCOUNT = "likesCount"
        const val COLUMN_SHARECOUNT = "shareCount"
        const val COLUMN_VIEWCOUNT = "viewCount"
        val ALL_COUMNS = arrayOf(
            COLUMN_ID, COLUMN_AUTHOR, COLUMN_POSTDATE,
            COLUMN_POSTTEXT, COLUMN_URLVIDEO, COLUMN_LIKED,
            COLUMN_LIKESCOUNT, COLUMN_SHARECOUNT, COLUMN_VIEWCOUNT
        )
        val DDL = """
            create table $TABLE (
            $COLUMN_ID integer not null primary key autoincrement,
            $COLUMN_AUTHOR string not null,
            $COLUMN_POSTDATE string not null,
            $COLUMN_POSTTEXT string not null,
            $COLUMN_URLVIDEO string,
            $COLUMN_LIKED integer,
            $COLUMN_LIKESCOUNT integer,
            $COLUMN_SHARECOUNT integer,
            $COLUMN_VIEWCOUNT integer)
            """.trimIndent()
    }

    private fun map(cursor: Cursor): Post {
        with (cursor) {
            return Post(
                getLong(getColumnIndexOrThrow(COLUMN_ID)),
                getString(getColumnIndexOrThrow(COLUMN_AUTHOR)),
                getString(getColumnIndexOrThrow(COLUMN_POSTDATE)),
                getString(getColumnIndexOrThrow(COLUMN_POSTTEXT)),
                getString(getColumnIndexOrThrow(COLUMN_URLVIDEO)),
                getInt(getColumnIndexOrThrow(COLUMN_LIKED)) !=0,
                getInt(getColumnIndexOrThrow(COLUMN_LIKESCOUNT)),
                getInt(getColumnIndexOrThrow(COLUMN_SHARECOUNT)),
                getInt(getColumnIndexOrThrow(COLUMN_VIEWCOUNT)),
            )
        }
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            TABLE,
            ALL_COUMNS,
            null,
            null,
            null,
            null,
            "$COLUMN_ID desc").use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun getMaxPostId(): Long {
        db.query(
            TABLE,
            arrayOf("max($COLUMN_ID)"),
            null,
            null,
            null,
            null,
            null).use {
            if (it.count>0) {
                it.moveToFirst()
                return it.getLongOrNull(0) ?: 0
            } else {
                return 0
            }
        }
    }

    override fun like(postId: Long) {
        db.execSQL("""
            Update $TABLE set
            $COLUMN_LIKESCOUNT = ifnull($COLUMN_LIKESCOUNT,0) + case when ifnull($COLUMN_LIKED,0)=0 Then 1 else -1 end, 
            $COLUMN_LIKED = case when ifnull($COLUMN_LIKED,0)=0 Then 1 else 0 end  
            where $COLUMN_ID = ?""".trimIndent(),
            arrayOf(postId.toString()))
    }

    override fun share(postId: Long) {
        db.execSQL("""
            Update $TABLE set 
            $COLUMN_SHARECOUNT = ifnull($COLUMN_SHARECOUNT,0) + 1
            where $COLUMN_ID = ?""".trimIndent(),
            arrayOf(postId.toString()))
    }

    override fun delete(postId: Long) {
        db.execSQL("""
            Delete from $TABLE
            where $COLUMN_ID = ?""".trimIndent(),
            arrayOf(postId.toString()))
    }

    override fun new(post: Post) {
        val values = ContentValues().apply {
            put(COLUMN_AUTHOR, "author")
            put(COLUMN_POSTDATE, Date().toString())
            put(COLUMN_POSTTEXT, post.postText)
            put(COLUMN_URLVIDEO, post.urlVideo)
        }
        db.insert(TABLE, null, values)
    }

    override fun edit(postId: Long, text: String) {
        db.execSQL("""
            Update $TABLE set
            $COLUMN_POSTTEXT = ?
            where $COLUMN_ID = ?""".trimIndent(),
            arrayOf(text,postId.toString()))
    }

}