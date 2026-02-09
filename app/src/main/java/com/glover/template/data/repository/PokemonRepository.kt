package com.glover.template.data.repository

import androidx.lifecycle.viewmodel.CreationExtras
import com.glover.template.data.local.room.PokemonDao
import com.glover.template.data.remote.PokemonApi
import com.glover.template.data.model.Pokemon
import com.glover.template.data.model.PokemonDetail
import com.glover.template.data.remote.PokemonDetailResponse

class PokemonRepository(
    private val api: PokemonApi
    //,private val dao: PokemonDao
) {

    companion object {
        private var pokemons : MutableList<Pokemon> = mutableListOf()
    }

    suspend fun getPokemonList(): MutableList<Pokemon> {
        return try {
            if(pokemons.isEmpty()){
                val response = api.getPokemonList(100)
                val mapped = response.results.map {
                    Pokemon(
                        name = it.name,
                        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                            it.url.split(
                                "/"
                            ).dropLast(1).last()
                        }.png"
                    )
                }.toMutableList()
                //dao.insertAll(mapped)
                pokemons = mapped
                mapped
            } else{
                pokemons
            }
        } catch (e: Exception) {
            //dao.getAll()
            pokemons
        }
    }

    suspend fun getPokemonDetail(name : String): PokemonDetailResponse? {
        return try {
            val response = api.getPokemonDetail(name)
            response
        } catch (e: Exception) {
            null
        }
    }

}
