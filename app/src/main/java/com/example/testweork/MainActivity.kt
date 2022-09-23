package com.example.testweork

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweork.api.ApiHelper
import com.example.testweork.databinding.ActivityMainBinding
import com.example.testweork.db.DbHelper
import com.example.testweork.db.NumberFact
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var daoNumberFact = DbHelper.getDaoDataBase(this).getDaoNumberFacts()
        val handler = CoroutineExceptionHandler {
                context, exception -> println("Error ${exception.message}")
        }

        var adapter = RvAdapter(mutableListOf())

        binding.rvNumberFact.adapter = adapter

        binding.rvNumberFact.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch{
            adapter.updateList(daoNumberFact.getNumberFacts())
        }
        binding.buttonFact.setOnClickListener {

            CoroutineScope(Dispatchers.IO + handler).launch {

                    val numbersApi = ApiHelper.getInstance()
                    var number:Int = if(binding.editTextNumber.text.toString().isNullOrEmpty()){
                        0
                    }else{
                        binding.editTextNumber.text.toString().toInt()
                    }
                var response = numbersApi.getNumberFact(number)
                if (response.isSuccessful) {


                    val numberFact: String =response.body()?:""


                    println(numberFact)
                    daoNumberFact.createNumberFacts(
                        NumberFact(
                            number, numberFact
                        )
                    )
                }

                    launch(Dispatchers.Main+handler) {
                        adapter.updateList(daoNumberFact.getNumberFacts())

                    }


            }

            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editTextNumber.windowToken, 0)
        }
        binding.buttonRandomFact.setOnClickListener {
            CoroutineScope(Dispatchers.IO + handler).launch {

                val randomNumberFact = ApiHelper.getInstance().getRandomNumberFact()
                if (randomNumberFact.isSuccessful) {
                    if(!randomNumberFact.body().isNullOrEmpty()) {


                        var id:Int = (randomNumberFact.body()?:"0".split(" ")[0]).toInt()

                        daoNumberFact.createNumberFacts(NumberFact(id, randomNumberFact.body()?:""))


                        launch(Dispatchers.Main + handler) {
                            adapter.updateList(daoNumberFact.getNumberFacts().asReversed())

                        }
                    }
                }
            }
        }



}

}







