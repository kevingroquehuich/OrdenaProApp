package com.roque.ordenaproapp.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.roque.ordenaproapp.R

@Composable
fun RoundedImage(url: String) {
    val shape = RoundedCornerShape(16.dp)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "Imagen",
        placeholder = painterResource(R.drawable.ic_image),
        error = painterResource(R.drawable.ic_broken_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(60.dp)
            .clip(shape)
    )
}