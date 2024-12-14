package com.example.dozer.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = neutral_green,
    onPrimary = dark1,
    primaryContainer = neutral_green,
    onPrimaryContainer = dark1,
    inversePrimary = neutral_yellow,
    secondary = neutral_yellow,
    onSecondary = dark1,
    secondaryContainer = neutral_yellow,
    onSecondaryContainer = dark1,
    tertiary = neutral_orange,
    onTertiary = dark1,
    tertiaryContainer = neutral_orange,
    onTertiaryContainer = dark1,
    background = dark0,
    onBackground = light0,
    surface = dark2,
    onSurface = light2,
    surfaceVariant = dark3,
    onSurfaceVariant = light3,
    surfaceTint = dark4,
    inverseSurface = light0,
    inverseOnSurface = dark0,
    error = neutral_red,
    onError = neutral_aqua,
    errorContainer = neutral_red,
    onErrorContainer = neutral_aqua,
    outline = neutral_blue,
    outlineVariant = neutral_purple,
    surfaceBright = light1,
    surfaceContainer = dark2,
    surfaceContainerHigh = light3,
    surfaceContainerHighest = light4,
    surfaceContainerLow = dark3,
    surfaceContainerLowest = dark4,
    surfaceDim = dark0_soft
)

private val LightColorScheme = lightColorScheme(
    primary = neutral_green,
    onPrimary = dark1,
    primaryContainer = neutral_green,
    onPrimaryContainer = dark1,
    inversePrimary = neutral_yellow,
    secondary = neutral_yellow,
    onSecondary = dark1,
    secondaryContainer = neutral_yellow,
    onSecondaryContainer = dark1,
    tertiary = neutral_orange,
    onTertiary = dark1,
    tertiaryContainer = neutral_orange,
    onTertiaryContainer = dark1,
    background = dark0,
    onBackground = light0,
    surface = dark2,
    onSurface = light2,
    surfaceVariant = dark3,
    onSurfaceVariant = light3,
    surfaceTint = dark4,
    inverseSurface = light0,
    inverseOnSurface = dark0,
    error = neutral_red,
    onError = neutral_aqua,
    errorContainer = neutral_red,
    onErrorContainer = neutral_aqua,
    outline = neutral_blue,
    outlineVariant = neutral_purple,
    surfaceBright = light1,
    surfaceContainer = dark2,
    surfaceContainerHigh = light3,
    surfaceContainerHighest = light4,
    surfaceContainerLow = dark3,
    surfaceContainerLowest = dark4,
    surfaceDim = dark0_soft
)

@Composable
fun DozerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}