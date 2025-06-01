package com.roque.ordenaproapp.ui.screens.products.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roque.ordenaproapp.R

@Preview
@Composable
fun ProductDetailScreen(
    productsDetailViewModel: ProductsDetailViewModel,
    productId: Int
) {
    var spicyLevel by remember { mutableStateOf(2f) }
    var quantity by remember { mutableStateOf(2) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")

            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Burger",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Cheeseburger Wendy's Burger",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFF9800))
                Text(" 4.9", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.width(8.dp))
                Text("– 26 mins", color = Color.Gray)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "The Cheeseburger Wendy's Burger is a classic fast food burger that packs a punch of flavor in every bite...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(Modifier.height(24.dp))

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 44.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Box(
                        modifier = Modifier
                            .background(Color.Red, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Minus", tint = Color.White)
                    }
                }
                Text("$quantity", Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = { quantity++ }) {
                    Box(
                        modifier = Modifier
                            .background(Color.Red, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Plus", tint = Color.White)
                    }
                }
            }


            Spacer(Modifier.width(24.dp))

            Box(
                modifier = Modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3C2E25)) // dark brown
                    .clickable { }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
               Row {
                   Text("Agregar", color = Color.White, fontWeight = FontWeight.Bold)
                   Spacer(Modifier.width(24.dp))
                   Text(text = "$8.24", color = Color.White, fontWeight = FontWeight.Bold)
               }
            }
        }
    }
}
