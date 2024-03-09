package com.renad.demoforlist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.renad.demoforlist.ui.navigation.NavGraph
import com.renad.demoforlist.ui.theme.DemoForListTheme

@Composable
fun RecipesApp(finishActivity: () -> Unit) {
    DemoForListTheme {
        val navController = rememberNavController()
        Scaffold { innerPaddingModifier ->
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier),
            )
        }
    }
}
