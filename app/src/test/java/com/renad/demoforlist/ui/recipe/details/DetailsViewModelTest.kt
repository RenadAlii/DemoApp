package com.renad.demoforlist.ui.recipe.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.renad.demoforlist.core.di.IoDispatchers
import com.renad.demoforlist.core.utils.Response
import com.renad.demoforlist.core.utils.SingleEvent
import com.renad.demoforlist.data.model.RecipeModel
import com.renad.demoforlist.domain.enities.Recipe
import com.renad.demoforlist.domain.usecase.RecipeUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val fixture = kotlinFixture()
    @MockK
    private val recipeUseCase = mockk<RecipeUseCase>(relaxed = true)
    private lateinit var viewModel: DetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

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

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(recipeUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `load details with success response updates uiState correctly`()= runTest {
        coEvery { recipeUseCase.invoke(any()) } returns flowOf(Response.Success(recipe))
        viewModel.onEvent(DetailsScreenEvent.LoadDetails(1))
        advanceUntilIdle()

        val expectedState = viewModel.uiState.value.copy(
            title = "Test Recipe",
            description = "",
            imageUrl = "url",
            isLoading = false,
            detailsLoaded = true
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }


    @Test
    fun `load details with failure response updates uiState correctly`()= runTest {
        val errorMessage = "Error fetching details"
        coEvery { recipeUseCase.invoke(1) } returns flowOf(Response.Failure(errorMessage))
        viewModel.onEvent(DetailsScreenEvent.LoadDetails(1))
        advanceUntilIdle()

        val expectedState = viewModel.uiState.value.copy(
            errorMsg = SingleEvent(errorMessage),
            isLoading = false,
            detailsLoaded = true
        )

        assertEquals(expectedState.errorMsg?.value, errorMessage)
        assertEquals(expectedState.isLoading, viewModel.uiState.value.isLoading)
        assertEquals(expectedState.detailsLoaded, viewModel.uiState.value.detailsLoaded)
    }
}
