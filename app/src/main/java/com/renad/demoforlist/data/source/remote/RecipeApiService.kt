package com.renad.demoforlist.data.source.remote

import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import retrofit2.http.GET
import retrofit2.http.Path


interface RecipeApiService {
    // Get Random list of Recipes.
    @GET("/recipes/random?number=10&apiKey=${BuildConfig.API_KEY}")
    suspend fun getRandomRecipes(): RecipesModel

    // Get Recipe by id.
    @GET("/recipes/{id}/information?includeNutrition=false&apiKey=${BuildConfig.API_KEY}")
    suspend fun getRecipe(
        @Path("id") id: String,
    ): RecipeModel
}
