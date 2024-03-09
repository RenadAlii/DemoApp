package com.renad.demoforlist.data.mapper

import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.domain.enities.Recipe
import javax.inject.Inject

class RecipeMapper
    @Inject
    constructor() {
        fun map(recipeModel: RecipeModel): Recipe =
            with(recipeModel) {
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
