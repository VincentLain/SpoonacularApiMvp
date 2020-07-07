package com.vincent.lain.add

import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.model.Menu

class AddMenuPresenter (private var viewInterface: AddMenuContract.ViewInterface,
private var dataSource: LocalDataSource) : AddMenuContract.PresenterInterface {

    override fun addMenu(title: String, posterPatch: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Menu title cannot be empty")
        } else {
            val menu = Menu(title, posterPatch)
            dataSource.insert(menu)
            viewInterface.returnToMain()
        }
    }

}