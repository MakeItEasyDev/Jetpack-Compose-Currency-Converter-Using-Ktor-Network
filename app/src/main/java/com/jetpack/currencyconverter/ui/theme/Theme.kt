package com.jetpack.currencyconverter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
    primary = BlackDark,
    primaryVariant = Black,
    secondary = White,
    secondaryVariant = WhiteDark,
    onPrimary = WhiteText,
    onSecondary = BlackText
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = WhiteLight,
    secondary = Black,
    secondaryVariant = BlackLight,
    onPrimary = BlackText,
    onSecondary = WhiteText
)

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}