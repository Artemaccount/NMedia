package ru.netology.nmedia.dto

enum class Action(val key: String) {
    Like("LIKE"), NEW_POST("NEW_POST");

    companion object{
        const val KEY = "action"
    }
}