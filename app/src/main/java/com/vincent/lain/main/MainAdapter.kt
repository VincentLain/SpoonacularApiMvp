package com.vincent.lain.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vincent.lain.R
import com.vincent.lain.model.Menu

import java.util.HashSet


class MainAdapter (internal var menuList: List<Menu>,
                   internal var context: Context):
        RecyclerView.Adapter<MainAdapter.MenusHolder>() {

    val selectedMenus = HashSet<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MenusHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu_main, parent, false)
        return MenusHolder(view)
    }

    override fun onBindViewHolder(holder: MenusHolder, position: Int) {
       holder.titleTextView.text = menuList[position].title
        if (menuList[position].image.equals("")) {
            holder.menuImageView.setImageDrawable(getDrawable(context, R.drawable.ic_baseline_room_service_24))
        } else {
            Picasso.get().load(menuList[position].image).into(holder.menuImageView)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MenusHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var titleTextView: TextView
        internal var menuImageView: ImageView
        internal var checkBox: CheckBox

        init {
            titleTextView = v.findViewById(R.id.title_textview)
            menuImageView = v.findViewById(R.id.menu_imageview)
            checkBox = v.findViewById(R.id.checkbox)
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMenus.contains(menuList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMenus.add(menuList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMenus.remove(menuList[adapterPosition])
                }
            }
        }
    }

}