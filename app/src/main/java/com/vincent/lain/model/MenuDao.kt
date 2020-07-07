package com.vincent.lain.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface MenuDao {

    @get:Query("SELECT * FROM menu_table")
    val all: Observable<List<Menu>>

    @Insert(onConflict = REPLACE)
    fun insert(menu: Menu)

    @Query("DELETE FROM menu_table WHERE id = :id")
    fun delete(id: Int?)

    @Query("DELETE FROM menu_table")
    fun deleteAll()
}