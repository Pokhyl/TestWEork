package com.example.testweork

import com.example.testweork.api.NumbersAPI
import com.example.testweork.db.DaoNumberFact
import com.example.testweork.db.NumberFact

class NumberFactRepository(var numbersApi: NumbersAPI, var daoNumberFact: DaoNumberFact) {
    suspend fun getNumberFactsDb() = daoNumberFact.getNumberFacts()
    suspend fun getNumberFactApi(number:Int) =  numbersApi.getNumberFact(number)
    suspend fun createNumberFact(numberFact: NumberFact) = daoNumberFact.createNumberFacts(numberFact)
    suspend fun getRandomNumberFact() = numbersApi.getRandomNumberFact()
}