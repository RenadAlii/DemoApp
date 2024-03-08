package com.renad.demoforlist.ui.recipe.recipes

import com.renad.demoforlist.core.utils.SingleEvent
import com.renad.demoforlist.data.model.RecipeModel

data class RecipesState(
    val recipesLoaded: Boolean = false,
    val recipes: List<RecipeModel> = emptyList(),
    val errorMsg: SingleEvent<String>? = null,
    val isLoading: Boolean = true,
)

sealed class RecipesScreenEvent {
    object LoadRecipes : RecipesScreenEvent()
}

