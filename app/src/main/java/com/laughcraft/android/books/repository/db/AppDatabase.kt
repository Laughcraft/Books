package com.laughcraft.android.books.repository.db

import android.content.Context
import androidx.room.*
import com.laughcraft.android.books.repository.entity.BookEntity

@Database(
        entities = [
            BookEntity::class
        ],
        version = 1,
        exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getCommentDao(): BooksDao

    companion object {

        private const val DB_NAME = "books.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE
                ?: synchronized(this) {
            INSTANCE
                    ?: buildDatabase(
                            context.applicationContext).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, AppDatabase::class.java,
                                     DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }
}