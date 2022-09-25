package com.example.testweork.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "number_facts")
 class NumberFact(
    @PrimaryKey
    val id: Int =0,

    val fact: String
):Parcelable
