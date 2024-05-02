package com.example.quizzer.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzer.R
import com.example.quizzer.activities.QuestionActivity
import com.example.quizzer.models.Quiz
import com.example.quizzer.utils.ColorPicker
import com.example.quizzer.utils.IconPicker

class QuizAdapter(val context: Context,val quizzes: List<Quiz>):
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizzes.size

    }


    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.textViewTitle.text = quiz.tittle
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context, quizzes[position].tittle, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE", quizzes[position].tittle)
            context.startActivity(intent)
        }
    }
    inner class QuizViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView = itemView.findViewById(R.id.quizTitle)
        var iconView: ImageView = itemView.findViewById(R.id.quizIcon)
        var cardContainer:CardView = itemView.findViewById(R.id.cardContainer)
    }
}