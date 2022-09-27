package com.example.testweork

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
//        val handler = CoroutineExceptionHandler {
//                context, exception -> println("Error ${exception.message}")
//        }
        val daoNumberFact = DbHelper.getDaoDataBase(this).getDaoNumberFacts()
        val numbersApi = ApiHelper.getInstance()
        val repository = NumberFactRepository(numbersApi, daoNumberFact)
        val mainViewModelFactory  = MainViewModelFactory(repository)
        val mainViewModel: MainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        val adapter = RvAdapter(mutableListOf())
        binding.rvNumberFact.adapter = adapter
        binding.rvNumberFact.layoutManager = LinearLayoutManager(this)

        mainViewModel.listLiveData.observe(this){
            adapter.updateList(it)
        }
        mainViewModel.getNumberFactsDb()

        mainViewModel.numberFact.observe(this){

            Toast.makeText(this,it, Toast.LENGTH_LONG).show()
        }
        binding.buttonFact.setOnClickListener {

            var number: Int = if (binding.editTextNumber.text.toString().isNullOrEmpty()) {
                0
            } else {
                binding.editTextNumber.text.toString().toInt()
            }
           mainViewModel.getNumberFactApi(number)



            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editTextNumber.windowToken, 0)
        }
        binding.buttonRandomFact.setOnClickListener {


            mainViewModel.getRandomNumberFact()


        }



}

}







