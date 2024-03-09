package com.renad.demoforlist.ui.recipe.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renad.demoforlist.core.di.IoDispatchers
import com.renad.demoforlist.core.utils.SingleEvent
import com.renad.demoforlist.core.utils.handleNetworkThrowable
import com.renad.demoforlist.domain.usecase.RecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel
    @Inject
    constructor(
        private val useCase: RecipesUseCase,
        @IoDispatchers private val dispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(RecipesState())

        val uiState get() = _uiState.asStateFlow()

        fun onEvent(event: RecipesScreenEvent) {
            when (event) {
                RecipesScreenEvent.LoadRecipes -> loadRecipes()
            }
        }

        private fun loadRecipes() =
            useCase.invoke().onStart {
                _uiState.update { state ->
                    state.copy(isLoading = true, recipesLoaded = true)
                }
            }.map {
                _uiState.update { state ->
                    state.copy(recipes = it, isLoading = false)
                }
            }.catch { error ->
                _uiState.update { state ->
                    state.copy(errorMsg = SingleEvent(error.handleNetworkThrowable()), isLoading = false)
                }
            }.flowOn(dispatcher).launchIn(viewModelScope)
    }
