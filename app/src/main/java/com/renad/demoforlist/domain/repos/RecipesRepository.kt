package com.renad.demoforlist.domain.repos

import com.renad.demoforlist.core.utils.Response
import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import com.renad.demoforlist.domain.enities.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRandomRecipes(): Flow<List<Recipe>>

    fun getRecipeById(id: String): Flow<Recipe>
}
