package com.vincent.lain.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vincent.lain.R
import com.vincent.lain.add.AddMenuActivity
import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.model.Menu

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private val TAG = MainActivity::class.simpleName
    private lateinit var presenter: MainContract.PresenterInterface


    private lateinit var menusRecyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var noMenusLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        presenter.getMyMenusList()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    private fun setupViews() {
        menusRecyclerView = findViewById(R.id.menus_recyclerview)
        menusRecyclerView.layoutManager = LinearLayoutManager(this)
        fab = findViewById(R.id.fab)
        noMenusLayout = findViewById(R.id.no_menus_layout)
        supportActionBar?.title = "Menus"
    }

    private fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        presenter = MainPresenter(this, dataSource)
    }

    override fun displayMenus(menuList: List<Menu>) {
        adapter = MainAdapter(menuList, this@MainActivity)
        menusRecyclerView.adapter = adapter

        menusRecyclerView.visibility = VISIBLE
        noMenusLayout.visibility = INVISIBLE
    }

    override fun displayNoMenus() {
        Log.d(TAG, "No menus to display")

        menusRecyclerView.visibility = INVISIBLE
        noMenusLayout.visibility = VISIBLE
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    fun goToAddMenuActivity(view: View) {
        val myIntent = Intent(this@MainActivity, AddMenuActivity::class.java)
        startActivityForResult(myIntent, ADD_MENU_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MENU_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            displayMessage("Menu successfully added.")
        } else {
            displayError("Menu could not be added.")
        }
    }

    companion object {
        const val ADD_MENU_ACTIVITY_REQUEST_CODE = 1
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            presenter.onDelete(adapter.selectedMenus)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}