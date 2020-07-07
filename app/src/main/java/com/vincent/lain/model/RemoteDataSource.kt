package com.vincent.lain.model

import com.vincent.lain.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    open fun searchResultsObservable(query: String): Observable<MenuResponse> {
        return RetrofitClient.menusApi
            .searchMenu(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}