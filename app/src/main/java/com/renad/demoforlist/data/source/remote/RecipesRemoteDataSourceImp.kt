package com.renad.demoforlist.data.source.remote

import com.renad.demoforlist.core.utils.flowCall
import com.renad.demoforlist.data.source.RecipesDataSource
import javax.inject.Inject

class RecipesRemoteDataSourceImp
    @Inject
    constructor(private val recipeApiService: RecipeApiService) : RecipesDataSource {
        override fun getRandomRecipes() =
            flowCall {
                recipeApiService.getRandomRecipes()
            }

        override fun getRecipeById(id: String) =
            flowCall {
                recipeApiService.getRecipe(id)
            }
    }
