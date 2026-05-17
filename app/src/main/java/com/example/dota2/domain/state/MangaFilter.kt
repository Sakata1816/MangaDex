package com.example.dota2.domain.state

data class MangaFilters(
    val status: List<Status>? = null,
    val authors: List<String>? = null,
    val artists: List<String>? = null,
    val contentRating: List<ContentRating>? = null,
    val includedTags: List<String>? = null,
    val excludedTags: List<String>? = null,
    val publicationDemographic: List<PublicationDemographic>? = null,
    val includes: List<MangaIncludeType>? = null,
    val year: Int? = null,
    val orderLatestUploadedChapter: SortOrder = SortOrder.DESC, // по умолчанию новые сверху
)

enum class SortOrder { ASC, DESC }

fun SortOrder.toString(): String {
    return when (this) {
        SortOrder.ASC -> "asc"
        SortOrder.DESC -> "desc"
    }
}

enum class MangaIncludeType {
    MANGA,
    COVER_ART,
    AUTHOR,
    ARTIST,
    TAG,
    CREATOR
}

fun MangaIncludeType.toString(): String {
    return when (this) {
        MangaIncludeType.MANGA -> "manga"
        MangaIncludeType.COVER_ART -> "cover_art"
        MangaIncludeType.AUTHOR -> "author"
        MangaIncludeType.ARTIST -> "artist"
        MangaIncludeType.TAG -> "tag"
        MangaIncludeType.CREATOR -> "creator"
    }
}

enum class ContentRating{
    ONGOING,
    COMPLETED,
    HIATUS,
    CANCELLED
}

fun ContentRating.toString(): String {
    return when (this) {
        ContentRating.ONGOING -> "ongoing"
        ContentRating.COMPLETED -> "completed"
        ContentRating.HIATUS -> "hiatus"
        ContentRating.CANCELLED -> "cancelled"
    }
}

enum class Status{
    SAFE,
    SUGGESTIVE,
    EROTICA,
    PORNOGRAPHIC
}

fun Status.toString(): String {
    return when (this) {
        Status.SAFE -> "safe"
        Status.SUGGESTIVE -> "suggestive"
        Status.EROTICA -> "erotica"
        Status.PORNOGRAPHIC -> "pornographic"
    }
}
enum class PublicationDemographic{
    SHOUJO,
    JOSEI,
    SEINEN,
    NONE
}

fun PublicationDemographic.toString(): String {
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

fun AvailableChapter.toString(): String {
    return when (this) {
        AvailableChapter.TRUE -> "true"
        AvailableChapter.FALSE -> "false"
    }
}

