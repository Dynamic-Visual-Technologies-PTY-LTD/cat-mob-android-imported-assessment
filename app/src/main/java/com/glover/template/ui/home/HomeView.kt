package com.glover.template.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.glover.template.data.model.Pokemon
import com.glover.template.ui.Drawables
import com.glover.template.ui.card.ItemCard
import com.glover.template.ui.detail.DetailScreen
import com.glover.template.ui.navigation.BottomNavigationBar
import com.glover.template.ui.navigation.Loader
import com.glover.template.ui.navigation.NoData
import com.glover.template.ui.navigation.NoInternetBanner
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    var pokemonList by remember { mutableStateOf<List<Pokemon>?>(null) }

    //val isConnected by viewModel.isConnected.collectAsState()

    /*if (!isConnected) {
        NoInternetBanner()
    }*/

    LaunchedEffect(Unit) {
        pokemonList = viewModel.getAll()
    }

    if (pokemonList==null) {
        Loader()
    } else {
        PokemonList(pokemonList!!)
    }
}

@Composable
fun PokemonList(pokemons : List<Pokemon>) {
    val navController = rememberNavController()
    var ps : List<Pokemon>? = pokemons

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                PokemonList(navController, ps!!)
            }
            composable("detail/{title}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                DetailScreen(title ?: "")
            }
        }
    }
}

@Composable
fun PokemonList(navController: NavController, pokemons : List<Pokemon>) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001B44))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0077CC))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                        append("Pokemon's ")
                    }
                    withStyle(SpanStyle(color = Color(0xFF66CCFF))) {
                        append("")
                    }
                },
                fontSize = 22.sp
            )

            Image(
                painter = painterResource(Drawables.Home.res),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        // Search bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search", color = Color.LightGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF102060),
                unfocusedContainerColor = Color(0xFF102060),
                disabledContainerColor = Color(0xFF102060),
                focusedIndicatorColor = Color(0xFF66CCFF),
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        if(pokemons.isEmpty()) {
            NoData()
        } else {

            // Pokemon list
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                items(pokemons!!.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }) { item ->
                    ItemCard(
                        item = item,
                        onClick = { navController.navigate("detail/${item.name}") }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


