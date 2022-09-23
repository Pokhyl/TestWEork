package com.example.testweork.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object ApiHelper {
    val baseUrl = "http://numbersapi.com/"
    fun getInstance(): NumbersAPI {
        return Retrofit
            .Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(NumbersAPI::class.java)


    }

}