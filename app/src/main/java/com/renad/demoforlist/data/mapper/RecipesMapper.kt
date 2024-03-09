package com.renad.demoforlist.data.mapper

import com.renad.demoforlist.data.model.RecipesModel
import com.renad.demoforlist.domain.enities.Recipe
import javax.inject.Inject

class
RecipesMapper@Inject
    constructor() {
        fun map(recipes: RecipesModel): List<Recipe> =
            recipes.recipes.map {
                with(it) {
                    Recipe(
                        id = id,
                        vegetarian = vegetarian,
                        vegan = vegan,
                        glutenFree = glutenFree,
                        title = title,
                        summary = summary,
                        readyInMinutes = readyInMinutes,
                        image = image,
                    )
                }
            }
    }
