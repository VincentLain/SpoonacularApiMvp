package com.vincent.lain.main

import android.util.Log
import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.model.Menu
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface?,
    private var dataSource: LocalDataSource
) : MainContract.PresenterInterface {

    private val TAG = MainPresenter::class.simpleName
    private val compositeDisposable = CompositeDisposable()

    val myMenusObservable: Observable<List<Menu>>
        get() = dataSource.allMenus

    val observer: DisposableObserver<List<Menu>>
    get() = object : DisposableObserver<List<Menu>>() {
        override fun onComplete() {
            Log.d(TAG, "Completed")
        }

        override fun onNext(menuList: List<Menu>) {
            if(menuList.isEmpty()) {
                viewInterface?.displayNoMenus()
            } else {
                viewInterface?.displayMenus(menuList)
            }
        }

        override fun onError(@NonNull e: Throwable)  {
            Log.d(TAG, "Error fetching menu list.", e)
            viewInterface?.displayError("Error fetching menu list.")
        }

    }

    override fun getMyMenusList() {
        val myMenusDisposable = myMenusObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(myMenusDisposable)
    }

    override fun onDelete(selectedMenus: HashSet<Menu>) {
        for (menu in selectedMenus) {
            dataSource.delete(menu)
        }
        if (selectedMenus.size == 1) {
            viewInterface?.displayMessage("Menu deleted")
        } else if(selectedMenus.size > 1){
            viewInterface?.displayMessage("Menus deleted")
        }
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        viewInterface = null
        compositeDisposable.dispose()
    }

}