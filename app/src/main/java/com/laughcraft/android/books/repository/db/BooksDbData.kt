package com.laughcraft.android.books.repository.db

import com.laughcraft.android.books.repository.DataSource
import com.laughcraft.android.books.repository.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Observable

class BooksDbData(val dao: BooksDao) : DataSource<BookEntity>() {

    private val TABLE_NAME = "books"
    
    override var onError: ((t: Throwable) -> Unit)? = null
    
    override fun getAll(): Observable<List<BookEntity>> {
        return try {
            dao.getAll().toObservable()
        }catch (t: Throwable){
            onError?.invoke(t)
            Observable.empty<List<BookEntity>>()
        }
    }
    
    override fun getAll(query: DataSource.Query<BookEntity>): Observable<List<BookEntity>> {
        return try {
            dao.rawQuery(DbData.sqlWhere(TABLE_NAME, query.params)).toObservable()
        }catch (t: Throwable){
            onError?.invoke(t)
            Observable.empty<List<BookEntity>>()
        }
    }
    
    override fun saveAll(list: List<BookEntity>): Observable<List<BookEntity>> {
        return Completable.fromCallable {
            try {
                dao.insertAll(list)
            }catch (t: Throwable){
                onError?.invoke(t)
            }
        }.andThen(Observable.just(list))
    }

    override fun removeAll(list: List<BookEntity>): Completable {
        return Completable.fromCallable {
            try {
                dao.deleteAll(list)
            }catch (t: Throwable){
                onError?.invoke(t)
            }
        }
    }

    override fun removeAll(): Completable {
        return Completable.fromCallable {
            try {
                dao.deleteAll()
            }catch (t: Throwable){
                onError?.invoke(t)
            }
        }
    }
}