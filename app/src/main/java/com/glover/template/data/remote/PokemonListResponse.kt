package com.glover.template.data.remote

data class PokemonListResponse(
    val results: MutableList<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)
