package com.renad.demoforlist.domain.repos

import com.renad.demoforlist.domain.enities.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRandomRecipes(): Flow<List<Recipe>>

    fun getRecipeById(id: String): Flow<Recipe>
}
