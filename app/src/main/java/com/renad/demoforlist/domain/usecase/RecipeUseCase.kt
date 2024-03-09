package com.renad.demoforlist.domain.usecase

import com.renad.demoforlist.domain.repos.RecipesRepository
import javax.inject.Inject

class RecipeUseCase
    @Inject
    constructor(private val repository: RecipesRepository) {
        operator fun invoke(id: Int) = repository.getRecipeById(id.toString())
    }
