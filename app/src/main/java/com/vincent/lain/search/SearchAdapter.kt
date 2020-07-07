package com.vincent.lain.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vincent.lain.R
import com.vincent.lain.model.Menu

class SearchAdapter(var menuList: List<Menu>,
                    var context: Context,
                    var listener: SearchActivity.RecyclerItemListener) :
    RecyclerView.Adapter<SearchAdapter.SearchMenusHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMenusHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_details, parent, false)

        val viewHolder = SearchMenusHolder(view)
        view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchMenusHolder, position: Int) {

        holder.titleTextView.text = menuList[position].title

        if (menuList[position].image != null) {
            Picasso.get().load(menuList[position].image).into(holder.menuImageView)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun getItemAtPosition(pos: Int): Menu {
        return menuList[pos]
    }

    inner class SearchMenusHolder(v: View) : RecyclerView.ViewHolder(v) {

        var titleTextView: TextView = v.findViewById(R.id.title_textview)
        var menuImageView: ImageView = v.findViewById(R.id.menu_imageview)

        init {
            v.setOnClickListener { v: View ->
                listener.onItemClick(v, adapterPosition)
            }
        }
    }
}