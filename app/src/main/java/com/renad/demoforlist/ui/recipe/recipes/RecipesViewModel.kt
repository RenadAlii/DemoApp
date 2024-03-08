package com.renad.demoforlist.ui.recipe.recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renad.demoforlist.core.di.IoDispatchers
import com.renad.demoforlist.core.utils.Response
import com.renad.demoforlist.core.utils.SingleEvent
import com.renad.demoforlist.domain.usecase.RecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(private val useCase: RecipesUseCase,
                                           @IoDispatchers private val dispatcher: CoroutineDispatcher): ViewModel() {


    private val _uiState = MutableStateFlow(RecipesState())

    val uiState get() = _uiState.asStateFlow()

fun onEvent(event: RecipesScreenEvent){
    when(event){
        RecipesScreenEvent.LoadRecipes -> loadRecipes()
    }
}

    private fun loadRecipes(){
        useCase.invoke().map {
                when(it){
                    is Response.Failure -> {
                       _uiState.update { state ->
                           state.copy(errorMsg = SingleEvent(it.message), isLoading = false)
                       }
                    }
                    is Response.Success -> {
                        _uiState.update { state ->
                            state.copy(recipes = it.data?.recipes ?: emptyList(), isLoading = false)
                        }
                    }
                    is Response.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }

                    }
                }
            }.flowOn(dispatcher).launchIn(viewModelScope)
    }

}