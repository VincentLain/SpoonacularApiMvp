package com.vincent.lain.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vincent.lain.R
import com.vincent.lain.model.MenuResponse
import com.vincent.lain.model.RemoteDataSource

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {
    private val TAG = SearchActivity::class.java.simpleName

    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMenusTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SearchPresenter
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview)
        noMenusTextView = findViewById(R.id.no_menus_textview)
        progressBar = findViewById(R.id.progress_bar)

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY)

        setupViews()
        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    private fun setupViews() {
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        presenter = SearchPresenter(this, dataSource)
    }

    override fun showToast(string: String) {
        Toast.makeText(this@SearchActivity, string, Toast.LENGTH_LONG).show()
    }

    override fun displayResult(menuResponse: MenuResponse) {
        progressBar.visibility = INVISIBLE
        if (menuResponse.totalMenuItems == null || menuResponse.totalMenuItems == 0) {
            searchResultsRecyclerView.visibility = INVISIBLE
            noMenusTextView.visibility = VISIBLE
        } else {
            adapter = SearchAdapter(menuResponse.menuItems ?: arrayListOf(), this@SearchActivity, itemListener)
            searchResultsRecyclerView.adapter = adapter

            searchResultsRecyclerView.visibility = VISIBLE
            noMenusTextView.visibility = INVISIBLE
        }
    }

    override fun displayError(string: String) {
        showToast(string)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val menu = adapter.getItemAtPosition(position)

            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, menu.title)
            replyIntent.putExtra(EXTRA_POSTER_PATH, menu.image)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(view: View, position: Int)
    }
}