package com.glover.template.ui.home

import com.glover.template.MainDispatcherRule
import com.glover.template.data.model.Pokemon
import com.glover.template.data.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: PokemonRepository = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        coEvery { repository.getPokemonList() } returns mutableListOf(
            Pokemon("pikachu", ""),
            Pokemon("bulbasaur", "")
        )

        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `init loads pokemons and sets loading false`() = runTest {
        // Advance coroutine launched in init
        advanceUntilIdle()

        assert(viewModel.isLoading == false)
        assert(viewModel.pokemons.size == 2)
        assert(viewModel.pokemons[0].name == "pikachu")
    }

    @Test
    fun `getAll returns repository data`() = runTest {
        val result = viewModel.getAll()

        assert(result.size == 2)
        assert(result[1].name == "bulbasaur")
    }
}