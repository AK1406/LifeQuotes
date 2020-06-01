package com.example.android.lifequotes;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class OwnQuoteWriter : AppCompatActivity() {
    private lateinit var back:Button
    private lateinit var msg:EditText
    private lateinit var submitBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_quote)

        submitBtn=findViewById(R.id.submit)
        msg=findViewById(R.id.textMessage)
        back=findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        submitBtn.setOnClickListener {
            saveInfo()
        }
    }

    private fun saveInfo() {
        val msgText=msg.text.toString().trim()

        if (msgText.isEmpty() ) {
            Toast.makeText(this, "Please enter your Quote", Toast.LENGTH_LONG).show()
        }else {
            val myRef = FirebaseDatabase.getInstance().getReference("quotes")
            val quoteId = myRef.push().key
            val message = quoteId?.let { WriteQuoteModel(it, msgText) }
            if (quoteId != null) {
                myRef.child(quoteId).setValue(message).addOnCompleteListener {
                    Toast.makeText(this, "Quote is saved successfully ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}

