
package com.renad.demoforlist.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.renad.demoforlist.ui.navigation.MainDestinations.RECIPE_DETAILS_ID
import com.renad.demoforlist.ui.recipe.details.DetailsScreen
import com.renad.demoforlist.ui.recipe.recipes.RecipesScreen

object MainDestinations {
    const val RECIPES_ROUTE = "recipe/recipes"
    const val RECIPE_DETAILS = "recipe/details"
    const val RECIPE_DETAILS_ID = "id"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.RECIPES_ROUTE,
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(MainDestinations.RECIPES_ROUTE) {
            BackHandler {
                finishActivity()
            }
            RecipesScreen(
                navigateToRecipeDetails = actions.detailsScreen,
            )
        }
        composable(
            "${MainDestinations.RECIPE_DETAILS}?$RECIPE_DETAILS_ID={$RECIPE_DETAILS_ID}",
            arguments =
                listOf(
                    navArgument(RECIPE_DETAILS_ID) { type = NavType.IntType },
                ),
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val currentId = arguments.getInt(RECIPE_DETAILS_ID)
            DetailsScreen(id = currentId)
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onPopBackStack: () -> Unit = {
        navController.popBackStack()
    }
    val detailsScreen = { recipeId: Int ->
        navController.navigate("${MainDestinations.RECIPE_DETAILS}?$RECIPE_DETAILS_ID=$recipeId")
    }
}
