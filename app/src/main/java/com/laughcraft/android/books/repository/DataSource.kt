package com.laughcraft.android.books.repository

import io.reactivex.Completable
import io.reactivex.Observable

abstract class DataSource<T : Any> {
    
    abstract var onError: ((t: Throwable)-> Unit)?
    
    abstract fun getAll(): Observable<List<T>>

    abstract fun getAll(query: Query<T>): Observable<List<T>>

    abstract fun saveAll(list: List<T>): Observable<List<T>>

    abstract fun removeAll(list: List<T>): Completable

    abstract fun removeAll(): Completable
    
    fun query(): Query<T> {
        return Query(this)
    }

    class Query<T : Any> constructor(private val dataSource: DataSource<T>) {

        val params: MutableMap<String, String> = mutableMapOf()

        fun has(property: String): Boolean {
            return params[property] != null
        }

        fun get(property: String): String? {
            return params[property]
        }

        fun where(property: String, value: String): Query<T> {
            params[property] = value
            return this
        }

        fun findAll(): Observable<List<T>> {
            return dataSource.getAll(this)
        }

        fun findFirst(): Observable<T> {
            return dataSource.getAll(this)
                    .filter { it.isNotEmpty() }
                    .map { it.first() }
        }
    }
}