package com.vincent.lain.add

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.vincent.lain.R
import com.vincent.lain.model.LocalDataSource
import com.vincent.lain.search.SearchActivity

class AddMenuActivity : AppCompatActivity(), AddMenuContract.ViewInterface {

    private lateinit var titleEditText: EditText
    private lateinit var menuImageView: ImageView
    private lateinit var addMenuPresenter: AddMenuContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        setupPresenter()
        setupViews()
    }

    fun setupPresenter() {
        val dataSource = LocalDataSource(application)
        addMenuPresenter = AddMenuPresenter(this, dataSource)
    }

    fun setupViews() {
        titleEditText = findViewById(R.id.menu_title)
        menuImageView = findViewById(R.id.menu_imageview)
    }

    override fun returnToMain() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showToast(string: String) {
        Toast.makeText(this@AddMenuActivity, string, Toast.LENGTH_LONG).show()
    }

    override fun displayError(string: String) {
        showToast(string)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@AddMenuActivity.runOnUiThread {
            titleEditText.setText(data?.getStringExtra(SearchActivity.EXTRA_TITLE))
            menuImageView.tag = data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)
            Picasso.get().load(data?.getStringExtra(SearchActivity.EXTRA_POSTER_PATH)).into(menuImageView)
        }
    }

    //search onClick
    fun goToSearchMovieActivity(v: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this@AddMenuActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MENU_ACTIVITY_REQUEST_CODE)
    }

    fun onClickAddMenu(view: View) {
        val title = titleEditText.text.toString()
        val posterPath = if (menuImageView.tag != null) menuImageView.tag.toString() else ""

        addMenuPresenter.addMenu(title, posterPath)

    }


    companion object {
        const val SEARCH_MENU_ACTIVITY_REQUEST_CODE = 2
    }
}