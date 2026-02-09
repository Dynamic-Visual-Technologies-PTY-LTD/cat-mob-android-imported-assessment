package com.glover.template.ui.detail

import android.R
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
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.glover.template.data.remote.PokemonDetailResponse
import com.glover.template.ui.card.ItemDetailCard
import com.glover.template.ui.home.PokemonScreen
import com.glover.template.ui.navigation.Loader
import com.glover.template.ui.navigation.NoData
import com.glover.template.ui.navigation.NoInternetBanner
import org.koin.androidx.compose.koinViewModel
import java.util.Locale.getDefault

@Composable
fun DetailScreen(
    name : String,
    viewModel: DetailViewModel = koinViewModel()
) {

    var pokemon by remember {
        mutableStateOf<PokemonDetailResponse?>(null)
    }

    //val isConnected by viewModel.isConnected.collectAsState()

    //if (isConnected) {
        LaunchedEffect(Unit) {
            viewModel.getPokemonDetail(name)
            pokemon = viewModel.pokemon
        }
    //}

    if (pokemon==null) {
        Loader()
    } else {
        PokemonDetailNav(pokemon!!)
    }
}

@Composable
fun PokemonDetailNav(pokemon : PokemonDetailResponse) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "detail"
    ) {
        composable("home") { PokemonScreen() }
        composable("detail") {
            PokemonDetail(pokemon)
        }
    }
}

@Composable
fun PokemonDetail(pokemon : PokemonDetailResponse) {
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
                        append("Pokemon")
                    }
                    withStyle(SpanStyle(color = Color(0xFF66CCFF))) {
                        append(" " +pokemon?.name?.uppercase(getDefault()))
                    }
                },
                fontSize = 22.sp
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon?.sprites?.frontDefault)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_secure),
                error = painterResource(R.drawable.ic_secure),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                    append("Abilities")
                }
            },
            fontSize = 28.sp,
            modifier = Modifier.padding(23.dp),
        )

        if(pokemon!=null) {
            // Attributes list
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                items(pokemon.abilities.filter {
                    it.ability.name.contains(searchText, ignoreCase = true)
                }) { item ->
                    ItemDetailCard(
                        item = item
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else{
            NoData()
        }
    }
}