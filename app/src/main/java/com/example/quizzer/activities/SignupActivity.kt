package com.example.quizzer.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzer.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private var btnSignup: Button? = null
    private var btnLogin: TextView? = null
    private var etEmailAddress: EditText? = null
    private var etPassword: EditText? = null
    private var etConfPassword: EditText? = null

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()

        btnSignup = findViewById<Button>(R.id.btnSigninSU)
        btnLogin = findViewById<TextView>(R.id.btnLoginSU)
        etEmailAddress = findViewById<EditText>(R.id.etEmailAddressSU)
        etPassword = findViewById<EditText>(R.id.etPasswordSU)
        etConfPassword = findViewById<EditText>(R.id.etConfPasswordSU)

        btnSignup?.setOnClickListener {
            signUpUser()
        }

        btnLogin?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser() {
        val email: String = etEmailAddress?.text.toString()
        val password: String = etPassword?.text.toString()
        val confpassword: String = etConfPassword?.text.toString()

        if (email.isBlank() || password.isBlank() || confpassword.isBlank()) {
            Toast.makeText(this, "Email and password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confpassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
               }
           }
    }
}
