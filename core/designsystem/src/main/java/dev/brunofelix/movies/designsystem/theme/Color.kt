package dev.brunofelix.movies.designsystem.theme

import androidx.compose.ui.graphics.Color

// Palette (mapped into the color scheme in Theme.kt)
val BlackPrimary = Color(0xFF000000)
val BlackSecondary = Color(0xFF121212)
val White = Color(0xFFFFFFFF)
val RedPrimary = Color(0xFFED202C)
val LightGray = Color(0xFFA8A8A8)
val DarkGray = Color(0xFF1E1D1D)
val ErrorRed = Color(0xFFFF0000)

// Brand tokens without a Material role
val DarkRed = Color(0xFF1D0103)
val RatingStar = Color(0xFFFFFF00)
val IconSilver = Color(0xFFCCCCCC)
val ShimmerBase = Color(0xFF2A2B2B)
val ShimmerHighlight = Color(0xFF3E3F3F)

// Translucent fills and borders
val SurfaceGlass = White.copy(alpha = 0.08f)
val SurfaceGlassStrong = White.copy(alpha = 0.1f)
val OutlineSubtle = White.copy(alpha = 0.1f)
val OutlineMedium = White.copy(alpha = 0.2f)
val ScrimLight = BlackPrimary.copy(alpha = 0.1f)
val ScrimMedium = BlackPrimary.copy(alpha = 0.4f)
val ScrimBar = BlackPrimary.copy(alpha = 0.85f)
val ScrimHeavy = BlackPrimary.copy(alpha = 0.9f)
