package com.renad.demoforlist.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.renad.demoforlist.R

@Composable
fun Loader(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadinganimation))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever, isPlaying = true)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        alignment = Alignment.Center

    )
}