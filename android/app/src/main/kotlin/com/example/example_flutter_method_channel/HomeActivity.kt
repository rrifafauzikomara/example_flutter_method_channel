package com.example.example_flutter_method_channel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    private val tag = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val eventChannelHandler = EventChannelHandler(context = applicationContext)

        val buttonClick = findViewById<Button>(R.id.buttonClick)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        buttonClick.setOnClickListener {
            Log.i(tag, "Clicked Button")
            eventChannelHandler.onReportGameClicked(applicationContext, tag)
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }
}