package com.example.myverbs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 1)
abstract class MyVerbsAppDataBase: RoomDatabase( ){

    abstract  fun wordDao(): WordDao

    companion object{
        private const val  DATABASE_NAME = "app_database"

        @Volatile
        private  var INSTANCE: MyVerbsAppDataBase? = null

        fun  getInstance(context: Context): MyVerbsAppDataBase?{
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyVerbsAppDataBase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            return INSTANCE

        }
    }
}