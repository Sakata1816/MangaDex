package com.example.dota2.domain.state

data class MangaFilters(
    val status: List<Status>? = null,
    val contentRating: List<ContentRating>? = null,
    val authorOrArtist: String? = null,
    val author: List<String>? = null,
    val artist: List<String>? = null,
    val includedTags: List<String>? = null,
    val excludedTags: List<String>? = null,
    val publicationDemographic: List<PublicationDemographic>? = null,
    val includes: List<IncludeType>? = listOf(IncludeType.COVER_ART),
    val year: Int? = null,
    val availableChapter: Boolean = true,
    val orderLatestUploadedChapter: SortOrder? = null,
    val orderFollowedCount: SortOrder? = null,
    val orderRating: SortOrder? = null
)




fun Boolean.toApiValue(): String {
    return if (this) "true" else "false"
}


enum class SortOrder { ASC, DESC }

fun SortOrder.toApiValue(): String {
    return when (this) {
        SortOrder.ASC -> "asc"
        SortOrder.DESC -> "desc"
    }
}

enum class IncludeType {
    MANGA,
    COVER_ART,
    AUTHOR,
    ARTIST,
    TAG,
    CREATOR
}

fun IncludeType.toApiValue(): String {
    return when (this) {
        IncludeType.MANGA -> "manga"
        IncludeType.COVER_ART -> "cover_art"
        IncludeType.AUTHOR -> "author"
        IncludeType.ARTIST -> "artist"
        IncludeType.TAG -> "tag"
        IncludeType.CREATOR -> "creator"
    }
}

enum class Status{
    ONGOING,
    COMPLETED,
    HIATUS,
    CANCELLED
}

fun Status.toApiValue(): String {
    return when (this) {
        Status.ONGOING -> "ongoing"
        Status.COMPLETED -> "completed"
        Status.HIATUS -> "hiatus"
        Status.CANCELLED -> "cancelled"
    }
}

enum class ContentRating{
    SAFE,
    SUGGESTIVE,
    EROTICA,
    PORNOGRAPHIC
}

fun ContentRating.toApiValue(): String {
    return when (this) {
        ContentRating.SAFE -> "safe"
        ContentRating.SUGGESTIVE -> "suggestive"
        ContentRating.EROTICA -> "erotica"
        ContentRating.PORNOGRAPHIC -> "pornographic"
    }
}
enum class PublicationDemographic{
    SHOUJO,
    JOSEI,
    SEINEN,
    NONE
}

fun PublicationDemographic.toApiValue(): String {
    return when (this) {
        PublicationDemographic.SHOUJO -> "shoujo"
        PublicationDemographic.JOSEI -> "josei"
        PublicationDemographic.SEINEN -> "seinen"
        PublicationDemographic.NONE -> "none"
    }
}

enum class AvailableChapter{
    TRUE,
    FALSE
}

fun AvailableChapter.toApiValue(): String {
    return when (this) {
        AvailableChapter.TRUE -> "true"
        AvailableChapter.FALSE -> "false"
    }
}


enum class TranslatedLanguage{
    ENGLISH,
    JAPANESE,
    CHINESE,
    FRENCH,
    SPANISH
}

fun TranslatedLanguage.toApiValue(): String {
    return when (this) {
        TranslatedLanguage.ENGLISH -> "en"
        TranslatedLanguage.JAPANESE -> "ja"
        TranslatedLanguage.CHINESE -> "zh"
        TranslatedLanguage.FRENCH -> "fr"
        TranslatedLanguage.SPANISH -> "es"
    }
}
