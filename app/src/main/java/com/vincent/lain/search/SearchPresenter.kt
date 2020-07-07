package com.vincent.lain.search

import com.vincent.lain.model.MenuResponse
import com.vincent.lain.model.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private var viewInterface: SearchContract.ViewInterface,
                      private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()

    val searchResultsObservable: (String) -> Observable<MenuResponse> = { query ->
        dataSource.searchResultsObservable(query) }

    val observer: DisposableObserver<MenuResponse>
        get() = object : DisposableObserver<MenuResponse>() {

            override fun onNext(@NonNull menuResponse: MenuResponse) {
                viewInterface.displayResult(menuResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                viewInterface.displayError("Error fetching Menus Data")
            }

            override fun onComplete() {}
        }

    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}