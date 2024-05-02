package com.example.quizzer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzer.R
import com.example.quizzer.models.Question
import com.example.quizzer.models.Quiz
import com.example.quizzer.adapters.OptionAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    private lateinit var btnprevious: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button
    private lateinit var description: TextView // Assuming description is a TextView
    private lateinit var optionList: RecyclerView

    var quizzes : MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFirestore()
        btnprevious = findViewById<Button>(R.id.btnPrevious)
        btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnNext = findViewById<Button>(R.id.btnNext)
        description = findViewById<TextView>(R.id.description)
        optionList = findViewById<RecyclerView>(R.id.optionList)



        setUpEventListner()
    }

    private fun setUpEventListner() {
        btnprevious.setOnClickListener {
            index--
            bindviews()
        }

        btnNext.setOnClickListener {
            index++
            bindviews()
        }

        btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ", questions.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore:FirebaseFirestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if(date!= null) {
            firestore.collection("quizzes").whereEqualTo("tittle", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindviews()
                    }
                }
        }
    }


    private fun bindviews() {
        btnprevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE
        if(index == 1){ //first question
            btnNext.visibility = View.VISIBLE
        }
        else if(index == questions!!.size) { // last question
            btnSubmit.visibility = View.VISIBLE
            btnprevious.visibility = View.VISIBLE
        }
        else{ // Middle
            btnprevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }


        val question = questions!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}