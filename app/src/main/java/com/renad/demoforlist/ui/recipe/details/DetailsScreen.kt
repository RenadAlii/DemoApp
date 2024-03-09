package com.renad.demoforlist.ui.recipe.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.viewpager2.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.renad.demoforlist.ui.components.ErrorSnackbar
import com.renad.demoforlist.ui.components.HtmlText
import com.renad.demoforlist.ui.components.Loader
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
    id: Int,
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
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
    LaunchedEffect(!state.detailsLoaded) {
        viewModel.onEvent(DetailsScreenEvent.LoadDetails(id))
    }
    Scaffold(
        snackbarHost = {
            Box(modifier = modifier.fillMaxSize()) {
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
                AnimatedVisibility(visible = !state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiaryContainer).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        GlideImage(
                            model = state.imageUrl,
                            contentDescription = state.title,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.fastscroll_default_thickness)),
                        )
                        Text(
                            text = state.title,
                            style =
                                MaterialTheme.typography.titleSmall.copy(
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                        )
                        HtmlText(html = state.description, style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Start))
                    }
                }
            }
        },
    )
}
