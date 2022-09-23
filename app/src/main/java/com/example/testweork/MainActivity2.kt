package com.example.testweork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testweork.db.NumberFact

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var numberFact = intent?.getParcelableExtra<NumberFact>("numberFact")
        println(numberFact?.id)
        println(numberFact?.fact)
    }
}