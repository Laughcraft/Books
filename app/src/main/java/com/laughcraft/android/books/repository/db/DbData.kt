package com.laughcraft.android.books.repository.db

import androidx.sqlite.db.SimpleSQLiteQuery
import com.laughcraft.android.books.repository.DataSource
import com.laughcraft.android.books.repository.entity.BookEntity
import com.laughcraft.android.books.App

import kotlin.reflect.KClass

object DbData {

    val db: AppDatabase by lazy { AppDatabase.getInstance(App.appContext()) }

    fun <Entity : Any> of(clazz: KClass<*>): DataSource<Entity> {
        return when (clazz) {
            BookEntity::class -> BooksDbData(db.getCommentDao())
            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<Entity>
    }

    fun clearDb() {
        db.clearAllTables()
    }

    // util method for converting PARAMS MAP to sql QUERY with WHERE keyword
    fun sqlWhere(table: String, params: Map<String, String>): SimpleSQLiteQuery {
        var query = "SELECT * FROM $table"
        params.keys.forEachIndexed { i, s ->
            query += if (i == 0) " WHERE" else " AND"
            query += " $s = ?"
        }

        val args = params.values.toTypedArray()
        return SimpleSQLiteQuery(query, args)
    }
}