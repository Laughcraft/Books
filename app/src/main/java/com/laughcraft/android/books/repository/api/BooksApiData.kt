package com.laughcraft.android.books.repository.api

import android.util.Log
import com.laughcraft.android.books.App.Companion.isNetworkAvailable
import com.laughcraft.android.books.repository.DataSource
import com.laughcraft.android.books.repository.entity.BookEntity
import com.laughcraft.android.books.repository.entity.ChaptersRequest
import io.reactivex.Completable
import io.reactivex.Observable
import java.lang.Exception

class BooksApiData : DataSource<BookEntity>() {
    
    private val TAG = javaClass.simpleName
    
    override var onError: ((t: Throwable) -> Unit)? = null
    
    private val api: Api = ApiService.create(Api::class.java)
    
    override fun getAll(): Observable<List<BookEntity>>{
        if (!isNetworkAvailable()){
            val error = IllegalAccessError("You are offline")
            onError?.invoke(error)
            return Observable.empty()
        }
        
        return api.getBooks().doOnNext { checkResponse(it) }.map { it.data }
    }
    
    private fun checkResponse(response: ChaptersRequest){
        when {
            response.status == null -> onError?.invoke(Exception(response.errors?.detail))
        
            response.status.startsWith("2") -> Log.i(TAG, "It's OK")
            response.status == "401" -> onError?.invoke(IllegalAccessError("Unauthorized"))
            response.status == "403" -> onError?.invoke(IllegalAccessError("Forbidden"))
            response.status == "404" -> onError?.invoke(IllegalAccessError("Not Found"))
            response.status.startsWith("5") -> onError?.invoke(IllegalAccessError("Server Error"))
        }
    }
    
    override fun getAll(query: Query<BookEntity>): Observable<List<BookEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun saveAll(list: List<BookEntity>): Observable<List<BookEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun removeAll(list: List<BookEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun removeAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}