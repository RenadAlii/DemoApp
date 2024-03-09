package com.renad.demoforlist.data.remote

import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import com.renad.demoforlist.data.source.remote.RecipeApiService
import com.renad.demoforlist.data.source.remote.RecipesRemoteDataSourceImp
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RecipesRemoteDataSourceImpTest {
    private lateinit var recipesRemoteDataSource: RecipesRemoteDataSourceImp
    private val recipeApiService: RecipeApiService = mockk()
    val fixture = kotlinFixture()

    @Before
    fun setUp() {
        recipesRemoteDataSource = RecipesRemoteDataSourceImp(recipeApiService)
    }

    @Test
    fun `getRandomRecipes returns expected data`() =
        runTest {
            // Arrange
            val expectedRecipes = fixture<RecipesModel>()
            coEvery { recipeApiService.getRandomRecipes() } returns expectedRecipes

            // Act
            val result = recipesRemoteDataSource.getRandomRecipes().toList().first()

            // Assert
            assertEquals(expectedRecipes, result)
        }

    @Test
    fun `getRandomRecipes handles exceptions`() =
        runTest {
            // Arrange
            val exception = Exception("Network error")
            coEvery { recipeApiService.getRandomRecipes() } throws exception

            // Act & Assert
            try {
                recipesRemoteDataSource.getRandomRecipes().toList()
                assertTrue("This line should not be executed", false)
            } catch (e: Exception) {
                assertEquals(exception.message, e.message)
            }
        }

    @Test
    fun `getRecipeById returns expected data`() =
        runTest {
            // Arrange
            val id = fixture<String>()
            val expectedRecipe = fixture<RecipeModel>()
            coEvery { recipeApiService.getRecipe(id) } returns expectedRecipe

            // Act
            val result = recipesRemoteDataSource.getRecipeById(id).toList().first()

            // Assert
            assertEquals(expectedRecipe, result)
        }

    @Test
    fun `getRecipeById handles exceptions`() =
        runTest {
            // Arrange
            val id = fixture<String>()
            val exception = Exception("Network error")
            coEvery { recipeApiService.getRecipe(id) } throws exception

            // Act & Assert
            try {
                recipesRemoteDataSource.getRecipeById(id).toList()
                assertTrue("This line should not be executed", false)
            } catch (e: Exception) {
                assertEquals(exception.message, e.message)
            }
        }
}
