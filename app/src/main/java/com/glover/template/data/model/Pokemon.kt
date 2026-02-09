package com.glover.template.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey val name: String,
    val url: String?
)

data class Pokemons (
    val results : List<Pokemon>
)