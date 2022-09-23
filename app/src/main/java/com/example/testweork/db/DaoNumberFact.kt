package com.example.testweork.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface DaoNumberFact {
    @Query("select * from number_facts")
    suspend fun getNumberFacts():List<NumberFact>
    @Insert(onConflict = REPLACE)
    suspend fun createNumberFacts(numberFact: NumberFact)

}