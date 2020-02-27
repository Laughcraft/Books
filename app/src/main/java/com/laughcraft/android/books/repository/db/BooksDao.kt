package com.laughcraft.android.books.repository.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.laughcraft.android.books.repository.entity.BookEntity
import io.reactivex.Maybe

@Dao
abstract class BooksDao {

    @Query("SELECT * FROM books")
    abstract fun getAll(): Maybe<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<BookEntity>)

    @Delete
    abstract fun deleteAll(users: List<BookEntity>)

    @Query("DELETE FROM books")
    abstract fun deleteAll()

    @RawQuery
    abstract fun rawQuery(query: SupportSQLiteQuery): Maybe<List<BookEntity>>
}