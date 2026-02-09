package com.glover.template.data.local.room

import com.glover.template.data.model.Pokemon
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

private var _pokemons : List<Pokemon> = mutableListOf()

@Dao
interface PokemonDao {
    @Query("SELECT * FROM Pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE name like :name")
    suspend fun getByName(name: String): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(list: List<Pokemon>)

}