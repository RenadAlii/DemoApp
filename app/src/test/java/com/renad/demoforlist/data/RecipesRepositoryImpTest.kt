package com.renad.demoforlist.data

import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.data.mapper.RecipeMapper
import com.renad.demoforlist.data.mapper.RecipesMapper
import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.data.model.RecipesModel
import com.renad.demoforlist.data.source.RecipesDataSource
import com.renad.demoforlist.domain.enities.Recipe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class RecipesRepositoryImpTest {
    val fixture = kotlinFixture()

    private lateinit var recipesRepository: RecipesRepositoryImp
    private val recipesDataSource: RecipesDataSource = mockk()
    private val recipeMapper: RecipeMapper = mockk()
    private val recipesMapper: RecipesMapper = mockk()

    @Before
    fun setUp() {
        recipesRepository = RecipesRepositoryImp(recipesDataSource, recipeMapper, recipesMapper)
    }

    @Test
    fun `getRandomRecipes returns expected data`() =
        runTest {
            val dataSourceRecipes = fixture<List<RecipeModel>>()
            val domainRecipes = fixture<List<Recipe>>()
            coEvery { recipesDataSource.getRandomRecipes() } returns flow { emit(RecipesModel(dataSourceRecipes)) }
            coEvery { recipesMapper.map(RecipesModel(dataSourceRecipes)) } returns domainRecipes
            val result = recipesRepository.getRandomRecipes().first()
            assertEquals(domainRecipes, result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getRandomRecipes handles exceptions`() =
        runTest {
            coEvery { recipesDataSource.getRandomRecipes() } throws IllegalStateException()
            advanceUntilIdle()
            assertThrows(IllegalStateException::class.java) {
                runBlocking {
                    recipesRepository.getRandomRecipes().first()
                }
            }
        }

    @Test
    fun `getRecipeById returns expected data`() =
        runTest {
            val id = fixture<String>()
            val dataSourceRecipe = fixture<RecipeModel>()
            val domainRecipe = fixture<Recipe>()
            coEvery { recipesDataSource.getRecipeById(id) } returns flow { emit(dataSourceRecipe) }
            coEvery { recipeMapper.map(dataSourceRecipe) } returns domainRecipe
            advanceUntilIdle()
            val result = recipesRepository.getRecipeById(id).first()
            assertEquals(domainRecipe, result)
        }

    @Test
    fun `getRecipeById handles exceptions`() =
        runTest {
            val id = fixture<String>()
            coEvery { recipesDataSource.getRecipeById(id) } throws IllegalStateException()
            assertThrows(IllegalStateException::class.java) {
                runBlocking {
                    recipesRepository.getRecipeById(id).first()
                }
            }
        }
}
