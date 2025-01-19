package com.sacada.data.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning

fun getIconResource(iconName: String) = when (iconName) {
    "add" -> Icons.Default.Add
    "edit" -> Icons.Default.Edit
    "delete" -> Icons.Default.Delete
    "done" -> Icons.Default.Done
    "arrow_back" -> Icons.AutoMirrored.Filled.ArrowBack
    "arrow_forward" -> Icons.AutoMirrored.Filled.ArrowForward
    "help" -> Icons.Default.Warning
    "menu" -> Icons.Default.Menu
    "home" -> Icons.Default.Home
    "search" -> Icons.Default.Search
    "person" -> Icons.Default.Person
    "settings" -> Icons.Default.Settings
    "account_circle" -> Icons.Default.AccountCircle
    else -> Icons.Default.Warning
}
