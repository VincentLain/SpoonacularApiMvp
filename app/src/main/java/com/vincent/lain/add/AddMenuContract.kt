package com.vincent.lain.add

class AddMenuContract {
    interface PresenterInterface {
        fun addMenu(title: String, posterPatch: String)
        fun onDestroy()
    }
    interface ViewInterface {
        fun returnToMain()
        fun showToast(string: String)
        fun displayError(string: String)
    }
}