package com.example.dota2.domain.state

enum class AuthorContentType {
    MANGA,
    USER,
    SCANLATION_GROUP
}



fun AuthorContentType.toApiValue(): String {
    return when(this) {
        AuthorContentType.MANGA -> "manga"
        AuthorContentType.USER -> "user"
        AuthorContentType.SCANLATION_GROUP -> "scanlation_group"
    }
}