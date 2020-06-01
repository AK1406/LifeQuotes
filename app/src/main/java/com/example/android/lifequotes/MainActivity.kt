package com.example.android.lifequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var startBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn=findViewById(R.id.start)

        startBtn.setOnClickListener {
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
