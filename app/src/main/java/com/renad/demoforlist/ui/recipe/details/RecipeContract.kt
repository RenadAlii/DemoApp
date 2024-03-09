package com.renad.demoforlist.ui.recipe.details

import com.renad.demoforlist.core.utils.SingleEvent

data class DetailsState(
    val detailsLoaded: Boolean = false,
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val errorMsg: SingleEvent<String>? = null,
    val isLoading: Boolean = true,
)

sealed class DetailsScreenEvent {
    data class LoadDetails(val id: Int) : DetailsScreenEvent()
}
