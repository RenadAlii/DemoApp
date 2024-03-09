package com.renad.demoforlist.domain.usecase

import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.domain.enities.Recipe
import com.renad.demoforlist.domain.repos.RecipesRepository
import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipesUseCaseTest {
    @MockK
    private lateinit var recipesRepository: RecipesRepository

    private lateinit var recipesUseCase: RecipesUseCase
    val fixture = kotlinFixture()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        recipesUseCase = RecipesUseCase(recipesRepository)
    }

    val recipe =
        fixture<Recipe>().copy(
            vegetarian = false,
            vegan = false,
            glutenFree = true,
            id = 1,
            title = "Test Recipe",
            readyInMinutes = 30,
            image = "url",
            summary = "",
        )

    @Test
    fun `invoke calls repository's getRandomRecipes`() =
        runTest {
            val fakeRecipes = listOf(recipe)
            coEvery { recipesRepository.getRandomRecipes() } returns flowOf(fakeRecipes)
            val result = recipesUseCase.invoke().first()
            assertEquals(fakeRecipes, result)
        }

    @Test
    fun `invoke calls repository's exceptions`() =
        runTest {
            coEvery { recipesRepository.getRandomRecipes() } throws MockKException("error")
            Assert.assertThrows(MockKException::class.java) {
                runBlocking {
                    recipesUseCase.invoke().first()
                }
            }
        }
}
