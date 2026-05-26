package com.example.dota2.presentation.screens.extensions

fun List<Map<String, String>>?.getPreferredTitle(): String {
    if (this == null) return ""
    val priority = listOf("en", "ja-ro", "ru")
    return priority.firstNotNullOfOrNull { lang ->
        this.firstNotNullOfOrNull { it[lang] }
    } ?: this.firstNotNullOfOrNull { it.values.firstOrNull() } ?: ""
}

