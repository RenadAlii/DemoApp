package com.renad.demoforlist.domain.enities

data class Recipe(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val image: String,
    val imageType: String,
    val summary: String,
    val cuisines: List<String>,
    val occasions: List<String>,
    val instructions: String,
)
