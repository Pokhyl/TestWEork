package com.example.testweork

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testweork.databinding.ActivityMain2Binding
import com.example.testweork.databinding.ActivityMainBinding
import com.example.testweork.db.NumberFact

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        var numberFact = intent?.getParcelableExtra<NumberFact>("numberFact")


        val numberFact = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("numberFact", NumberFact::class.java)
        } else {
            intent.getParcelableExtra<NumberFact>("numberFact")
        }
       //binding.idFact.text =   numberFact?.id.toString()
       binding.contentFact.text = numberFact?.fact

        binding.buttonBack.setOnClickListener{

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}