package com.renad.demoforlist.ui.recipe.recipes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.domain.enities.Recipe
import com.renad.demoforlist.domain.usecase.RecipesUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class RecipesViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val fixture = kotlinFixture()

    @RelaxedMockK
    private val useCase = mockk<RecipesUseCase>(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RecipesViewModel

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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RecipesViewModel(useCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadRecipes emits success state`() =
        runTest {
            val fakeRecipes = listOf(recipe) // Use actual recipe objects
            coEvery { useCase.invoke() } returns flowOf(fakeRecipes)
            viewModel.onEvent(RecipesScreenEvent.LoadRecipes)
            advanceUntilIdle()

            val uiState = viewModel.uiState.value
            assertTrue(uiState.recipesLoaded)
            assertFalse(uiState.isLoading)
            assertEquals(fakeRecipes, uiState.recipes)
            assertNull(uiState.errorMsg?.value)
        }

    @Test
    fun `loadRecipes emits failure state`() =
        runTest {
            val errorMessage = "An unknown error occurred."
            every { useCase.invoke() } returns flow { throw Exception(errorMessage) }
            viewModel.onEvent(RecipesScreenEvent.LoadRecipes)
            advanceUntilIdle()

            val uiState = viewModel.uiState.value
            assertTrue(uiState.recipesLoaded)
            assertFalse(uiState.isLoading)
            assertTrue(uiState.recipes.isEmpty())
            assertEquals(errorMessage, uiState.errorMsg?.value)
        }
}
