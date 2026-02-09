package com.glover.template.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glover.template.data.model.Pokemon
import com.glover.template.data.repository.PokemonRepository
import com.glover.template.utils.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PokemonRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val isConnected = networkMonitor.observe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    suspend fun getAll() : MutableList<Pokemon>{
        return repository.getPokemonList()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading = true
            pokemons = repository.getPokemonList()
            isLoading = false
        }
    }

    var pokemons by mutableStateOf<List<Pokemon>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            pokemons = repository.getPokemonList()
            isLoading = false
        }
    }
}