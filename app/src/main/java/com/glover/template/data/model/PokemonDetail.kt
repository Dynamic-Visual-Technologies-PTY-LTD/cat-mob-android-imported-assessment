package com.glover.template.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonDetail (
    @PrimaryKey val name: String,

)