package com.example.testweork

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweork.db.NumberFact
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        viewModelScope.launch {
            var job: Job = viewModelScope.launch(Dispatchers.Main + handler) {
                var response = numberFactRepository.getNumberFactApi(number)
                if (response.isSuccessful) {


                    val numberFactStr: String = response.body() ?: ""
                    numberFact.value = numberFactStr
                    numberFactRepository.createNumberFact(NumberFact(number, numberFactStr))


                } else {
                    numberFact.value = "Error"
                }

            }
            job.join()
            getNumberFactsDb()
        }
    }
    fun createNumberFact(numberFact: NumberFact) {
        viewModelScope.launch {
            numberFactRepository.createNumberFact(numberFact)
        }
    }


     @SuppressLint("SuspiciousIndentation")
     fun getRandomNumberFact() {
         viewModelScope.launch {
            var job:Job =   viewModelScope.launch(Dispatchers.Main + handler) {
                 var response = numberFactRepository.getRandomNumberFact()
                 if (response.isSuccessful) {
                     if (!response.body().isNullOrEmpty()) {
                         var s: Int = ((response.body() ?: "0").split(" ")[0]).toInt()
//                     var fact = response.body()?:"0"
//                     var factSplit: String = fact.split(" ")[0]
//                     var id = factSplit.toInt()
                         numberFact.value = response.body() ?: ""
                         numberFactRepository.createNumberFact(NumberFact(s, response.body() ?: ""))

                     } else {
                         numberFact.value = "Error"
                     }
                 }
             }
             job.join()
             getNumberFactsDb()
         }
     }
}