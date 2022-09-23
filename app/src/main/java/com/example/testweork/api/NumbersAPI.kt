package com.example.testweork.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersAPI {
  //  http://numbersapi.com/10
    @GET("{number}")
    suspend fun getNumberFact(@Path("number") number: Int):Response<String>
    @GET("random/math")
    suspend fun getRandomNumberFact():Response<String>
}