package com.example.testweork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweork.db.NumberFact
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val numberFactRepository: NumberFactRepository): ViewModel() {

    val listLiveData: MutableLiveData<List<NumberFact>> = MutableLiveData()
    var numberFact: MutableLiveData<String> = MutableLiveData()
    val handler = CoroutineExceptionHandler {
            context, exception -> println("Error ${exception.message}")
    }

   fun getNumberFactsDb() {

       viewModelScope.launch(Dispatchers.Main+handler) {
           var list: List<NumberFact> = numberFactRepository.getNumberFactsDb()
           listLiveData.value = list
       }

   }
    fun getNumberFactApi(number:Int) {
        viewModelScope.launch(Dispatchers.Main + handler) {
        var response = numberFactRepository.getNumberFactApi(number)
            if (response.isSuccessful) {


                val numberFactStr: String =response.body()?:""
                numberFact.value=numberFactStr
                numberFactRepository.createNumberFact(NumberFact(number, numberFactStr))


            }else{
                numberFact.value="Error"
            }
    }
    fun createNumberFact(numberFact: NumberFact) {
        viewModelScope.launch {
            numberFactRepository.createNumberFact(numberFact)
        }
    }

    }
     fun getRandomNumberFact() {
         viewModelScope.launch(Dispatchers.Main + handler) {
           var response  =  numberFactRepository.getRandomNumberFact()
             if (response.isSuccessful) {
                 if (!response.body().isNullOrEmpty()) {

                     var fact = response.body()?:"0"
                     var factSplit: String = fact.split(" ")[0]
                     var id = factSplit.toInt()
                     numberFact.value = fact
                     numberFactRepository.createNumberFact(NumberFact(id, fact))

                 } else {
                     numberFact.value = "Error"
                 }
             }
         }
     }
}