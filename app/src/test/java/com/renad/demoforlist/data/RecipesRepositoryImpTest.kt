package com.renad.demoforlist.data


import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.core.utils.Response
import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import com.renad.demoforlist.data.source.RecipesDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RecipesRepositoryImpTest {

    private lateinit var recipesDataSource: RecipesDataSource
    private lateinit var recipesRepositoryImp: RecipesRepositoryImp

    val fixture = kotlinFixture()
    val recipe = fixture<RecipeModel>().copy(
        vegetarian = false,
        vegan = false,
        glutenFree = true,
        id = 1,
        title = "Test Recipe",
        readyInMinutes = 30,
        servings = 4,
        image = "url",
        summary = "",
    )

    val recipes = fixture<RecipesModel>().copy(listOf(recipe))
    @Before
    fun setUp() {
        recipesDataSource = mockk(relaxed = true)
        recipesRepositoryImp = RecipesRepositoryImp(recipesDataSource)
    }

    @Test
    fun `getRandomRecipes calls data source and returns success response`() = runTest {
        coEvery { recipesDataSource.getRandomRecipes() } returns flow { emit(Response.Success((recipes))) }
        val result = recipesRepositoryImp.getRandomRecipes()

        result.collect { response ->
            val isCorrectResponse = when (response) {
                is Response.Success -> response.data?.let { it.recipes.isNotEmpty() } ?: false
                else -> false
            }
            assertTrue(isCorrectResponse)
        }


    }
}
