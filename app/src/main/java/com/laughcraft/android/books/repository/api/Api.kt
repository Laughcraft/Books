package com.laughcraft.android.books.repository.api

import com.laughcraft.android.books.repository.entity.ChaptersRequest
import io.reactivex.Observable
import retrofit2.http.*

interface Api {
    
    @GET("1")
    fun getBooks(): Observable<ChaptersRequest>

}