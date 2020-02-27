package com.laughcraft.android.books.repository.api

import com.laughcraft.android.books.repository.DataSource
import com.laughcraft.android.books.repository.entity.BookEntity
import kotlin.reflect.KClass

object ApiData {

    fun <Entity : Any> of(clazz: KClass<*>): DataSource<Entity> {
        return when (clazz) {
            BookEntity::class -> BooksApiData()
            else -> throw IllegalArgumentException("Unsupported data type")
        } as DataSource<Entity>
    }
}