package com.mlb.weatherapp.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun Toolbar(
    title: String,
    backButtonIcon: ImageVector? = null, // Nullable back button icon
    onBackButtonClick: (() -> Unit)? = null // Callback for back button click
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = backButtonIcon?.let {
            {
                IconButton(onClick = { onBackButtonClick?.invoke() }) {
                    Icon(
                        imageVector = it,
                        contentDescription = "Back"
                    )
                }
            }
        },
        backgroundColor = Color.Black,
        contentColor = Color.White
    )
}