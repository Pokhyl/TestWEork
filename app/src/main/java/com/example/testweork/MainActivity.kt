package com.example.testweork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweork.api.ApiHelper
import com.example.testweork.databinding.ActivityMainBinding
import com.example.testweork.db.DbHelper
import com.example.testweork.db.NumberFact
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

        var adapter = RvAdapter(mutableListOf())

        binding.rvNumberFact.adapter = adapter

        binding.rvNumberFact.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.Main).launch{
            adapter.updateList(daoNumberFact.getNumberFacts())
        }
        binding.buttonFact.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val numbersApi= ApiHelper.getInstance()
                val numberFact = numbersApi.getNumberFact(binding.editTextNumber.text.toString().toInt())
                println(numberFact)
                daoNumberFact.createNumberFacts(
                    NumberFact(
                        binding.editTextNumber.text.toString().toInt(), numberFact
                    )
                )

                launch(Dispatchers.Main){
                    adapter.updateList(daoNumberFact.getNumberFacts())
                }
            }
        }
        binding.buttonRandomFact.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val randomNumberFact = ApiHelper.getInstance().getRandomNumberFact()
                var id = randomNumberFact.split(" ")[0].toInt()
                daoNumberFact.createNumberFacts(NumberFact(id, randomNumberFact))

                println(randomNumberFact)
                launch(Dispatchers.Main){
                    adapter.updateList(daoNumberFact.getNumberFacts())
                }

            }

        }



}
}







