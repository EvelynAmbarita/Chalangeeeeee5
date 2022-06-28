package com.Evelyndamayantiambarita.challengechapter5.data.api.tmdb.listgenre

data class ListGenreResponse(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}