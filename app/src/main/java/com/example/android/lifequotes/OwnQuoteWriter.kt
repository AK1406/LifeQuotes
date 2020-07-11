package com.example.android.lifequotes;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OwnQuoteWriter : AppCompatActivity() {
    private lateinit var back:Button
    private lateinit var msg:EditText
    private lateinit var submitBtn:Button
    private var userId: String? = null
    private val quoteList:MutableList<String> = mutableListOf()
    private val messageObj :MutableList<WriteQuoteModel> = mutableListOf()
    var i =0
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
        quoteList.add(msgText)
        val position =quoteList.size
        val emailId: String
        if (msgText.isEmpty() ) {
            Toast.makeText(this, "Please enter your Quote", Toast.LENGTH_LONG).show()
        }else {
            val myRef = FirebaseDatabase.getInstance().getReference("quotes")
            val user = FirebaseAuth.getInstance().currentUser
            userId = user!!.uid
            emailId = user.email.toString()
            val subEmail = emailId.substringBefore("@")  //abc123@gmail.com -> abc123(substring of email id)
            val quoteId = myRef.push().key
             val message =  WriteQuoteModel(subEmail,msgText)
            messageObj.add(message)
                i += 1
            if (quoteId != null) {
                myRef.child(userId!!).child(quoteId).setValue(messageObj[messageObj.size-1]).addOnCompleteListener {
                    Toast.makeText(this, "Quote is saved successfully ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }
}

