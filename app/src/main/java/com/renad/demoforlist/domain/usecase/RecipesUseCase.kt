package com.renad.demoforlist.domain.usecase

import com.renad.demoforlist.domain.repos.RecipesRepository
import javax.inject.Inject

class RecipesUseCase @Inject constructor(private val repository: RecipesRepository) {
        operator fun invoke() = repository.getRandomRecipes()
    }
