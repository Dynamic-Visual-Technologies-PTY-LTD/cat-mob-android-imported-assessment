package com.glover.template.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glover.template.data.remote.PokemonDetailResponse
import com.glover.template.data.repository.PokemonRepository
import com.glover.template.utils.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val repository: PokemonRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    val isConnected = networkMonitor.observe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    suspend fun getPokemonDetail(name : String) {
        pokemon = repository.getPokemonDetail(name)
        isLoading = false
    }

    var pokemon by mutableStateOf<PokemonDetailResponse?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

}