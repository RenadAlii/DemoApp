package com.renad.demoforlist.data.source.remote

import com.renad.demoforlist.data.source.RecipesDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipesRemoteDataSourceImp
    @Inject
    constructor(private val recipeApiService: RecipeApiService) : RecipesDataSource {
        override fun getRandomRecipes() = flow { emit(recipeApiService.getRandomRecipes()) }

        override fun getRecipeById(id: String) = flow { emit(recipeApiService.getRecipe(id)) }
    }
