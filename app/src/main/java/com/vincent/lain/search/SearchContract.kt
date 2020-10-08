package com.vincent.lain.search

import com.vincent.lain.model.MenuResponse

class SearchContract {

    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
        fun onDestroy()
    }

    interface ViewInterface {
        fun showToast(string: String)
        fun displayResult(menuResponse: MenuResponse)
        fun displayError(string: String)
    }
}