package com.example.android.lifequotes;

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class OwnQuoteWriter : AppCompatActivity() {

    private lateinit var mic: ImageView
    private val  REQUEST_CODE_SPEECH_INPUT = 1
    private lateinit var back:Button
    private lateinit var msg:EditText
    private lateinit var submitBtn:Button
    private var userId: String? = null
    private val quoteList:MutableList<String> = mutableListOf()
    private val messageObj :MutableList<WriteQuoteModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_quote)

        submitBtn=findViewById(R.id.submit)
        msg=findViewById(R.id.textMessage)
        back=findViewById(R.id.back)
        mic = findViewById(R.id.micImage)
        back.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
        mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(this, " " + e.message,
                        Toast.LENGTH_SHORT)
                    .show()
            }
        }
        submitBtn.setOnClickListener {
            saveInfo()
        }
    }

    private fun saveInfo() {

        val msgText=msg.text.toString().trim()
        quoteList.add(msgText)
        val emailId: String
        if (msgText.isEmpty() ) {
            Toast.makeText(this, "Please enter your Quote", Toast.LENGTH_LONG).show()
        }else {
            val myRef = FirebaseDatabase.getInstance().getReference("quotes")
            val user = FirebaseAuth.getInstance().currentUser
            userId = user!!.uid
            emailId = user.email.toString()
            val quoteId = myRef.push().key
             val message = quoteId?.let { WriteQuoteModel(it,msgText) }
            messageObj.add(message!!)
            myRef.child(userId!!).child(quoteId).setValue(messageObj[messageObj.size-1]).addOnCompleteListener {
                Toast.makeText(this, "Quote is saved successfully ", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS)
                val text = Objects.requireNonNull(result)[0]
                msg.setText(text)
            }
        }
    }
}

