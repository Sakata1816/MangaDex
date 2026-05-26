package com.example.dota2.presentation.screens.extensions

import android.util.Log
import com.example.dota2.domain.model.server.MangaModel

fun MangaModel.getCoverUrl(): String? {
    val coverRelationship = relationships
        ?.firstOrNull { it.type == "cover_art" }

    Log.d("CoverUrl", "relationships: ${relationships?.map { it.type }}")
    Log.d("CoverUrl", "coverRelationship: $coverRelationship")
    Log.d("CoverUrl", "attributes: ${coverRelationship?.attributes}")

    val fileName = coverRelationship
        ?.attributes
        ?.get("fileName") as? String

    Log.d("CoverUrl", "fileName: $fileName")
    Log.d("CoverUrl", "id: $id")

    return if (id != null && fileName != null) {
        "https://uploads.mangadex.org/covers/$id/$fileName"
    } else null
}