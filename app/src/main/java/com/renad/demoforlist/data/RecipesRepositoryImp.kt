package com.renad.demoforlist.data

import com.renad.demoforlist.data.source.RecipesDataSource
import com.renad.demoforlist.domain.repos.RecipesRepository
import javax.inject.Inject

class RecipesRepositoryImp
    @Inject
    constructor(private val recipesDataSource: RecipesDataSource) : RecipesRepository {
        override fun getRandomRecipes() = recipesDataSource.getRandomRecipes()

        override fun getRecipeById(id: String) = recipesDataSource.getRecipeById(id)
    }
