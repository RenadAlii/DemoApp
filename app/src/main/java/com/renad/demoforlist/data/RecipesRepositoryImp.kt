package com.renad.demoforlist.data

import com.renad.demoforlist.data.mapper.RecipeMapper
import com.renad.demoforlist.data.mapper.RecipesMapper
import com.renad.demoforlist.data.source.RecipesDataSource
import com.renad.demoforlist.domain.repos.RecipesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipesRepositoryImp @Inject constructor(private val recipesDataSource: RecipesDataSource
,private val recipeMapper: RecipeMapper, private val recipesMapper: RecipesMapper
) : RecipesRepository {
        override fun getRandomRecipes() = recipesDataSource.getRandomRecipes().map(recipesMapper::map)

        override fun getRecipeById(id: String) = recipesDataSource.getRecipeById(id).map(recipeMapper::map)
    }
