package com.example.testweork.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumberFact::class], version = 2)
abstract class DataBaseNumberFacts: RoomDatabase() {
    abstract fun getDaoNumberFacts(): DaoNumberFact
}