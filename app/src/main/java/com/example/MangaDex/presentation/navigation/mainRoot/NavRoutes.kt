package com.example.MangaDex.presentation.navigation.mainRoot

sealed class NavRoutes(val route: String){
    object Main: NavRoutes("main_screen")
    object Search: NavRoutes("search_screen")
    object Favorites: NavRoutes("favorites_screen")
    object Settings: NavRoutes("settings_screen")
    object MangaDetail: NavRoutes("manga_detail_screen/{id}"){
        fun getPath(id: String) = "manga_detail_screen/$id"
    }
    object Reader: NavRoutes("reader_screen/{id}"){
        fun getPath(id: String) = "reader_screen/$id"
    }
    object MangaFilterList: NavRoutes("manga_filter_list_screen/{type}/{type_id}/{title}"){
        fun getPath(type: String, typeId: String, title: String) = "manga_filter_list_screen/${type}/${typeId}/${title}"
    }
    object Tags: NavRoutes("tags_screen")
    object Filters: NavRoutes("filters_screen")
    object Catalog : NavRoutes("catalog_screen")
    object CatalogGraph : NavRoutes("catalog_graph")
    object ChangeProfile: NavRoutes("change_profile_screen")


}
