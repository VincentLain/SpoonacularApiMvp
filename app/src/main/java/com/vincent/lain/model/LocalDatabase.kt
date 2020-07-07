package com.vincent.lain.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Menu::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun menuDao(): MenuDao

    companion object {

        // --- SINGLETON ---
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "my_menu_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}