package com.vincent.lain.network

import com.vincent.lain.model.MenuResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/food/menuItems/search")
    fun searchMenu(@Query("apiKey") apiKey: String, @Query("query") q: String): Observable<MenuResponse>

}