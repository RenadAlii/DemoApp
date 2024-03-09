package com.renad.demoforlist.ui.recipe.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renad.demoforlist.core.di.IoDispatchers
import com.renad.demoforlist.core.utils.Response
import com.renad.demoforlist.core.utils.SingleEvent
import com.renad.demoforlist.core.utils.handleNetworkThrowable
import com.renad.demoforlist.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
    @Inject
    constructor(
        private val useCase: RecipeUseCase,
        @IoDispatchers private val dispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(DetailsState())

        val uiState get() = _uiState.asStateFlow()

        fun onEvent(event: DetailsScreenEvent) {
            when (event) {
                is DetailsScreenEvent.LoadDetails -> loadDetails(event.id)
            }
        }

        private fun loadDetails(id: Int) = useCase.invoke(id).onStart {
                    _uiState.update { state ->
                        state.copy(isLoading = true, detailsLoaded = true)
                    }
                }.map {
                val recipe = it
                _uiState.update { state ->
                    state.copy(
                        title = recipe.title ?: "",
                        description = recipe.summary ?: "",
                        imageUrl = recipe.image ?: "",
                        isLoading = false,
                    )
                }
            }.catch {error ->
                _uiState.update { state ->
                    state.copy(errorMsg = SingleEvent(error.handleNetworkThrowable()), isLoading = false)
                }
            }.flowOn(dispatcher).launchIn(viewModelScope)

    }
