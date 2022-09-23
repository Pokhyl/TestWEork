package com.example.testweork.db

import android.content.Context
import androidx.room.Room

object DbHelper {
    fun getDaoDataBase(context: Context): DataBaseNumberFacts{
        return Room.databaseBuilder(context, DataBaseNumberFacts::class.java,"fact").build()
    }
}