package dev.brunofelix.movies.designsystem.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// Spacing (padding, gaps and offsets)
val spacing2 = 2.dp
val spacing4 = 4.dp
val spacing8 = 8.dp
val spacing12 = 12.dp
val spacing16 = 16.dp
val spacing20 = 20.dp
val spacing24 = 24.dp
val spacing32 = 32.dp
val spacing100 = 100.dp

// Sizes (widths, heights, icons and strokes)
val size1 = 1.dp
val size5 = 5.dp
val size16 = 16.dp
val size20 = 20.dp
val size24 = 24.dp
val size30 = 30.dp
val size32 = 32.dp
val size36 = 36.dp
val size40 = 40.dp
val size48 = 48.dp
val size60 = 60.dp
val size64 = 64.dp
val size72 = 72.dp
val size80 = 80.dp
val size88 = 88.dp
val size100 = 100.dp
val size120 = 120.dp
val size128 = 128.dp
val size150 = 150.dp
val size160 = 160.dp
val size180 = 180.dp
val size200 = 200.dp
val size288 = 288.dp

// Elevation
val elevation0 = 0.dp
val elevation4 = 4.dp

// Shapes
val shapeCircle: Shape = CircleShape
val shapeRounded2: Shape = RoundedCornerShape(spacing2)
val shapeRounded4: Shape = RoundedCornerShape(spacing4)
val shapeRounded8: Shape = RoundedCornerShape(spacing8)
val shapeRounded12: Shape = RoundedCornerShape(spacing12)
val shapeRounded16: Shape = RoundedCornerShape(spacing16)
val shapeRounded32: Shape = RoundedCornerShape(spacing32)

val AppShapes = Shapes(
    small = RoundedCornerShape(spacing4),
    medium = RoundedCornerShape(spacing8),
    large = RoundedCornerShape(spacing16)
)
