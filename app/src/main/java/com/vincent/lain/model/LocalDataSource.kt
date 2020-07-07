package com.vincent.lain.model

import android.app.Application
import io.reactivex.Observable
import kotlin.concurrent.thread

open class LocalDataSource(application: Application) {

    private val menuDao: MenuDao
    open val allMenus: Observable<List<Menu>>

    init {
        val db = LocalDatabase.getInstance(application)
        menuDao = db.menuDao()
        allMenus = menuDao.all
    }


    open fun insert(menu: Menu) {
        thread {
            menuDao.insert(menu)
        }
    }

    open fun delete(menu: Menu) {
        thread {
            menuDao.delete(menu.id)
        }
    }

}