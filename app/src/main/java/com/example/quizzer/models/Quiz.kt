package com.example.quizzer.models

data class Quiz(
    var id: String =" ",
    var tittle: String =" ",
    var questions: MutableMap<String,Question> = mutableMapOf()
)
