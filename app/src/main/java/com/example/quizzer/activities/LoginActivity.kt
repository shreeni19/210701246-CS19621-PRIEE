package com.example.quizzer.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizzer.R
import androidx.appcompat.app.AppCompatActivity

//import com.example.quizzer.activities.MainActivity
//import com.example.quizzer.activities.SignupActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var btnLogin: Button? = null
    private var btnSignUp: TextView? = null
    private var etEmailAddress: EditText? = null
    private var etPassword: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Toast.makeText(this, "HI", Toast.LENGTH_SHORT).show()



        firebaseAuth = FirebaseAuth.getInstance()
        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnSignUp = findViewById<TextView>(R.id.btnSignUp)
        etEmailAddress = findViewById<EditText>(R.id.etEmailAddress)
        etPassword = findViewById<EditText>(R.id.etPassword)

        btnLogin?.setOnClickListener {
            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
            login()
        }

        btnSignUp?.setOnClickListener {
           startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun login() {
        val email = etEmailAddress?.text.toString().trim()
        val password = etPassword?.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

       firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
