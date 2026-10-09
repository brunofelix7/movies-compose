package dev.brunofelix.movies.domain.model

data class Cast(
    val id: Long = 0L,
    val name: String = "",
    val character: String = "",
    val profilePath: String = ""
)
