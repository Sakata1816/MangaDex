package com.example.dota2.presentation.navigation.mainRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.dota2.presentation.screens.CatalogScreen
import com.example.dota2.presentation.screens.FilterScreen
import com.example.dota2.presentation.screens.MainScreen
import com.example.dota2.presentation.screens.MangaDetailScreen
import com.example.dota2.presentation.screens.ReaderScreen
import com.example.dota2.presentation.screens.MangaFilterList
import com.example.dota2.presentation.screens.TagsListScreen
import com.example.dota2.presentation.viewModel.screens.CatalogScreenViewModel
import com.example.dota2.presentation.viewModel.screens.MainScreenViewModel

@Composable
fun AppNavGraph(navController: NavHostController,modifier: Modifier) {
    NavHost(modifier = modifier, navController=navController, startDestination = NavRoutes.Main.route) {

        composable(NavRoutes.Main.route){
            MainScreen(navController)
        }

        composable(NavRoutes.Search.route){

        }

        composable(NavRoutes.Favorites.route){

        }

        composable(NavRoutes.Settings.route){

        }

        navigation(
            startDestination = NavRoutes.Catalog.route,
            route = NavRoutes.CatalogGraph.route   // ← отдельное имя, не совпадающее с Catalog
        ) {
            composable(NavRoutes.Catalog.route) { backStackEntry: NavBackStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(NavRoutes.CatalogGraph.route)
                }
                val viewModel: CatalogScreenViewModel = hiltViewModel(parentEntry)

                CatalogScreen(navController, viewModel)
            }
            composable(NavRoutes.Filters.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(NavRoutes.CatalogGraph.route)
                }
                val viewModel: CatalogScreenViewModel = hiltViewModel(parentEntry)
                val currentFilters by viewModel.state.collectAsStateWithLifecycle()

                FilterScreen(
                    currentFilters = currentFilters.filter,
                    onOpenTags = { navController.navigate(NavRoutes.Tags.route) },
                    onApply = { viewModel.onFiltersChanged(it)
                              navController.popBackStack()},
                    onBack = { navController.popBackStack() }
                )
            }
            composable(NavRoutes.Tags.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(NavRoutes.CatalogGraph.route)
                }
                val viewModel: CatalogScreenViewModel = hiltViewModel(parentEntry)
                val tagState by viewModel.tagState.collectAsStateWithLifecycle()

                TagsListScreen(
                    state = tagState,
                    initialSelectedTagIds = viewModel.state.value.filter.includedTags ?: emptyList(),
                    onApply = { selectedIds ->
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }


        composable(NavRoutes.MangaDetail.route){backStackEntry->
            val id = backStackEntry.arguments?.getString("id").toString()
            MangaDetailScreen(id, navController)
        }

        composable(NavRoutes.Reader.route){backStackEntry->
            val id = backStackEntry.arguments?.getString("id").toString()
            ReaderScreen(
                onBack = {navController.popBackStack()},
                chapterId =id)
        }

        composable(NavRoutes.MangaFilterList.route){backStackEntry->
            val type = backStackEntry.arguments?.getString("type")
            val typeId = backStackEntry.arguments?.getString("type_id")
            val title = backStackEntry.arguments?.getString("title")
            MangaFilterList(
                type = type.toString(),
                typeId = typeId.toString(),
                title = title.toString(),
                navController = navController
            )
        }
    }
}