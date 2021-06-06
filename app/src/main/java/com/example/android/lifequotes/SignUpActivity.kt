package com.example.android.lifequotes;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var name: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        name = findViewById(R.id.name)
        emailEt = findViewById(R.id.email_edt_text)
        passwordEt = findViewById(R.id.pass_edt_text)

        loginBtn = findViewById(R.id.login_btn)
        signUpBtn = findViewById(R.id.signup_btn)


        signUpBtn.setOnClickListener{
            val name: String = name.text.toString().trim()
            val email: String = emailEt.text.toString()
            val password: String = passwordEt.text.toString()

            if(name.isEmpty()||TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this,"Please fill all the fields !", Toast.LENGTH_LONG).show()
            }

            else{
                if (password.length < 6) {
                    Toast.makeText(
                        this,
                        "Password too short! , enter minimum 6 characters",
                        Toast.LENGTH_LONG
                    ).show()
                }
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                            saveInfo(name)

                    }else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun saveInfo(name:String) {
        val emailId: String
        val myRef = FirebaseDatabase.getInstance().getReference("profile") // making reference for the object of profile
        val user = FirebaseAuth.getInstance().currentUser
        // add username, email to database
        userId = user!!.uid
        emailId = user.email.toString()
        val subEmail = emailId.substringBefore("@")  //abc123@gmail.com -> abc123(substring of email id)
        val profileId = myRef.push().key //generating random key
        val profileInfo = profileId?.let { ProfileModel(subEmail,name,emailId)
        } //passing taken information to a class constructor of ProfileModel
        if (profileId != null) {
            //set the taken information
            myRef.child(userId!!).setValue(profileInfo).addOnCompleteListener {
                Toast.makeText(this, "Your profile is saved successfully ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}