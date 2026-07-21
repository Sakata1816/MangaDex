package com.example.MangaDex.presentation.navigation.mainRoot

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
import com.example.MangaDex.presentation.screens.mangaScreens.CatalogScreen
import com.example.MangaDex.presentation.screens.mangaScreens.ChangeProfileScreen
import com.example.MangaDex.presentation.screens.mangaScreens.FavoriteMangaScreen
import com.example.MangaDex.presentation.screens.mangaScreens.FilterScreen
import com.example.MangaDex.presentation.screens.mangaScreens.MainScreen
import com.example.MangaDex.presentation.screens.mangaScreens.MangaDetailScreen
import com.example.MangaDex.presentation.screens.mangaScreens.ReaderScreen
import com.example.MangaDex.presentation.screens.mangaScreens.MangaFilterList
import com.example.MangaDex.presentation.screens.mangaScreens.SearchScreen
import com.example.MangaDex.presentation.screens.mangaScreens.SettingsScreen
import com.example.MangaDex.presentation.screens.mangaScreens.TagsListScreen
import com.example.MangaDex.presentation.viewModel.screens.CatalogScreenViewModel

@Composable
fun AppNavGraph(navController: NavHostController,modifier: Modifier) {
    NavHost(modifier = modifier, navController=navController, startDestination = NavRoutes.Main.route) {

        composable(NavRoutes.Main.route){
            MainScreen(navController)
        }

        composable(NavRoutes.Search.route){
            SearchScreen(navController)
        }

        composable(NavRoutes.Favorites.route){
            FavoriteMangaScreen(navController)
        }

        composable(NavRoutes.Settings.route){
            SettingsScreen(navController)
        }

        composable(NavRoutes.ChangeProfile.route){
            ChangeProfileScreen(navController)
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