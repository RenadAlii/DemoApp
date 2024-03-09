package com.renad.demoforlist.data.source

import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import kotlinx.coroutines.flow.Flow

interface RecipesDataSource {
    fun getRandomRecipes(): Flow<RecipesModel>

    fun getRecipeById(id: String): Flow<RecipeModel>
}
