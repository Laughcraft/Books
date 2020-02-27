package com.laughcraft.android.books.repository

import com.laughcraft.android.books.repository.api.ApiData
import com.laughcraft.android.books.repository.db.DbData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object Repository {
    
    inline fun <reified Entity : Any> of(): Repo<Entity> {
        return Repo(ApiData.of(Entity::class), DbData.of(Entity::class))
    }
    
    fun clearDatabase(): Completable {
        return Completable.fromCallable { DbData.clearDb() }.subscribeOn(Schedulers.io())
    }
}

class Repo<Entity : Any>(val api: DataSource<Entity>, val db: DataSource<Entity>) : DataSource<Entity>() {
    
    override var onError: ((t: Throwable) -> Unit)? = null
        set(value) {
            api.onError = value
            db.onError = value
            field = value
        }
    
    override fun getAll(): Observable<List<Entity>> {
        return Observable.concatArrayEager(db.getAll().subscribeOn(Schedulers.io()),
                                           Observable.defer {
                                               api.getAll().subscribeOn(
                                                       Schedulers.io()).flatMap { l ->
                                                   db.removeAll().andThen(db.saveAll(l))
                                               }
                                           }.subscribeOn(Schedulers.io()))
    }
    
    override fun saveAll(list: List<Entity>): Observable<List<Entity>> {
        return Observable.defer {
            api.saveAll(list).subscribeOn(Schedulers.io()).flatMap { db.saveAll(list) }
        }
    }
    
    override fun removeAll(list: List<Entity>): Completable {
        return Completable.defer {
            api.removeAll(list).subscribeOn(Schedulers.io()).andThen(db.removeAll(list))
        }
    }
    
    override fun removeAll(): Completable {
        return Completable.defer {
            api.removeAll().subscribeOn(Schedulers.io()).andThen(db.removeAll())
        }
    }
    
    override fun getAll(query: Query<Entity>): Observable<List<Entity>> {
        return Observable.concatArrayEager(db.getAll(query).subscribeOn(Schedulers.io()),
                                           Observable.defer {
                                               api.getAll(query).subscribeOn(
                                                       Schedulers.io()).flatMap { l ->
                                                   db.getAll(query).flatMapCompletable { old ->
                                                       db.removeAll(old)
                                                   }.andThen(db.saveAll(l))
                                               }
                                           }.subscribeOn(Schedulers.io()))
    }
}