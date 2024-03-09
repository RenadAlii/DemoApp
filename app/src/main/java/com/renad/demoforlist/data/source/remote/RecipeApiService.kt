package com.renad.demoforlist.data.source.remote

import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY = "d8244f44357a41c285d9f686ebe47ed2"

interface RecipeApiService {
    // Get Random list of Recipes.
    @GET("/recipes/random?number=10&apiKey=${API_KEY}")
    suspend fun getRandomRecipes(): RecipesModel

    // Get Recipe by id.
    @GET("/recipes/{id}/information?includeNutrition=false&apiKey=${API_KEY}")
    suspend fun getRecipe(
        @Path("id") id: String,
    ): RecipeModel
}
