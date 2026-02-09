package com.glover.template.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

// Data Models
data class PokemonItem(
    val title: String,
    val subtitle: String,
    val date: String
)

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)