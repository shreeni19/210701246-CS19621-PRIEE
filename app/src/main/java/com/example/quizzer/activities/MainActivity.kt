package com.example.quizzer.activities

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzer.R
import com.example.quizzer.activities.ProfileActivity
import com.example.quizzer.activities.QuestionActivity
import com.example.quizzer.adapters.QuizAdapter
import com.example.quizzer.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity  : AppCompatActivity() {

    private lateinit var quizRecyclerView: RecyclerView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private  lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private var firestore: FirebaseFirestore?=null
    private var appBar: androidx.appcompat.widget.Toolbar?=null
    private var mainDrawer: DrawerLayout?=null
    private var navigationView: NavigationView?=null
    private  var btnDatePicker: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize views
        appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.appBar)
        mainDrawer = findViewById<DrawerLayout>(R.id.mainDrawer)
        navigationView = findViewById<NavigationView>(R.id.navigationView)
        quizRecyclerView = findViewById<RecyclerView>(R.id.quizRecyclerView)
        btnDatePicker = findViewById<FloatingActionButton>(R.id.btnDatePicker)

        setUpViews()
    }

    private fun setUpViews() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        btnDatePicker?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date:String = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore?.collection("quizzes")
        if (collectionReference != null) {
            collectionReference.addSnapshotListener { value, error ->
                if (value == null || error != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                quizList.clear()
                quizList.addAll(value.toObjects(Quiz::class.java))
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView?.setNavigationItemSelectedListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer?.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
