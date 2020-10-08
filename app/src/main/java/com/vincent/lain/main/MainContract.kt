package com.vincent.lain.main

import com.vincent.lain.model.Menu

class MainContract {

    interface PresenterInterface {
        fun getMyMenusList()
        fun onDelete(selectedMenus: HashSet<Menu>)
        fun stop()
        fun onDestroy()
    }

    interface ViewInterface {
        fun displayMenus(menuList: List<Menu>)
        fun displayNoMenus()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}