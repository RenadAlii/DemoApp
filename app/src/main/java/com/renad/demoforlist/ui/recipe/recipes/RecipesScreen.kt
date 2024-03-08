package com.renad.demoforlist.ui.recipe.recipes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.renad.demoforlist.ui.components.ErrorSnackbar
import com.renad.demoforlist.ui.components.Loader
import kotlinx.coroutines.launch

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel = hiltViewModel(), navigateToRecipeDetails: (id: String) -> Unit) {
    val state by recipesViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMsg) {
        state.errorMsg?.handel {
            launch {
            snackbarHostState.showSnackbar(
                message = it,
                withDismissAction = true,
                duration = SnackbarDuration.Short,
            )
            }
        }
    }
    LaunchedEffect(state.isLoading) {
       recipesViewModel.onEvent(RecipesScreenEvent.LoadRecipes)
    }
    Scaffold(
        snackbarHost = {
            Box(modifier = Modifier.fillMaxSize()) {
                ErrorSnackbar(
                    snackbarHostState = snackbarHostState,
                    onDismiss = {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        },
        content = { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {


            AnimatedVisibility(visible = state.isLoading) {
                Loader()
            }
            AnimatedVisibility(visible = state.recipes.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = androidx.viewpager2.R.dimen.fastscroll_default_thickness)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(items = state.recipes, key = { it.id }){
                        RecipeItem(title = it.title, imageUrl = it.image)
                    }
                }
            }
        } },
    )


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeItem(imageUrl: String, title: String){
        Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiaryContainer), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        GlideImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier.padding(dimensionResource(id = androidx.viewpager2.R.dimen.fastscroll_default_thickness)),
        )
        Text(text = title, style = MaterialTheme.typography.titleSmall.copy(textAlign = TextAlign.Center))
    }
    }

