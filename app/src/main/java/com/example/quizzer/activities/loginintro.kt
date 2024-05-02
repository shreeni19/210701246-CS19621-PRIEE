package com.example.quizzer.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzer.R
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import com.google.firebase.FirebaseApp


class loginIntroActivity : AppCompatActivity() {
    private var btnGetStarted: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_loginintro)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
           Toast.makeText(this, "User is already logged in!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
       }

        btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        btnGetStarted?.setOnClickListener {
            redirect("LOGIN")
        }
    }

    private fun redirect(name: String) {
        val intent = when (name) {
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No path exists")
        }
        startActivity(intent)
        finish()
    }
}

