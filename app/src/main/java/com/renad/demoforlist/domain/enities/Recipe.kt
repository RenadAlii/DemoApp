package com.renad.demoforlist.domain.enities

data class Recipe(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val image: String,
    val summary: String,
)
