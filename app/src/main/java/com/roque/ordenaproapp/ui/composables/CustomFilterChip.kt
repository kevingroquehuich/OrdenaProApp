package com.roque.ordenaproapp.ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        modifier = Modifier.padding(horizontal = 6.dp).height(50.dp),
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontWeight = FontWeight.Medium,
                color = if (selected) Color.White else Color(0xFF666666)
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xFFF3F4F6),
            selectedContainerColor = Color(0xFFFF3B30),
            labelColor = Color(0xFF666666),
            selectedLabelColor = Color.White,
            disabledContainerColor = Color.Transparent,
        ),
        border = null
    )
}
