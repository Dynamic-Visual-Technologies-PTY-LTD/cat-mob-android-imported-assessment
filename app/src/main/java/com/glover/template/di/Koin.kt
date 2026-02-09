package com.glover.template.di

import androidx.room.Room
import com.glover.template.data.local.room.PokemonDatabase
import com.glover.template.data.remote.PokemonApi
import com.glover.template.data.repository.PokemonRepository
import com.glover.template.ui.detail.DetailViewModel
import com.glover.template.ui.home.HomeViewModel
import com.glover.template.utils.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Provide API
    single<PokemonApi> {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }

    // Provide Room database
    single {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "pokemon_db"
        ).build()
    }
    single { get<PokemonDatabase>().pokemonDao() }
    single { NetworkMonitor(get()) }
    single { PokemonRepository(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }

}

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(appModule)
    }
}