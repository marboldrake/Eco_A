/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.theme

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.score.Score

val DefaultThemeColor = Color(0xFFFFFFFF)

// Nocturne Audio base palette (monochromatic, AMOLED)
val NocturneCanvas = Color(0xFF000000)
val NocturneSurfaceLowest = Color(0xFF050505)
val NocturneSurface = Color(0xFF0A0A0A)
val NocturneSurfaceElevated = Color(0xFF121214)
val NocturneSurfaceContainer = Color(0xFF201F1F)
val NocturneSurfaceContainerHigh = Color(0xFF2A2A2A)
val NocturneSurfaceContainerHighest = Color(0xFF353534)
val NocturneOnSurface = Color(0xFFE5E2E1)
val NocturneOnSurfaceVariant = Color(0xFFC4C7C8)
val NocturneSecondary = Color(0xFFA1A1AA)
val NocturneMuted = Color(0xFF71717A)
val NocturneGlassFill = Color(0xBF0A0A0C)
val NocturneBorderSubtle = Color(0x12FFFFFF)
val NocturneHairline = Color(0x1FFFFFFF)

@Composable
fun MetrolistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    // Nocturne Audio: fixed monochromatic AMOLED scheme in dark mode.
    // Light mode keeps the previous dynamic/materialKolor behavior.
    val baseColorScheme = if (darkTheme) {
        darkColorScheme(
            primary = NocturneOnSurface,
            onPrimary = NocturneCanvas,
            primaryContainer = NocturneSurfaceElevated,
            onPrimaryContainer = NocturneOnSurface,
            inversePrimary = NocturneSecondary,
            secondary = NocturneSecondary,
            onSecondary = NocturneCanvas,
            secondaryContainer = NocturneSurfaceContainer,
            onSecondaryContainer = NocturneOnSurfaceVariant,
            tertiary = NocturneOnSurface,
            onTertiary = NocturneCanvas,
            tertiaryContainer = NocturneSurfaceElevated,
            onTertiaryContainer = NocturneOnSurfaceVariant,
            background = NocturneCanvas,
            onBackground = NocturneOnSurface,
            surface = NocturneSurface,
            onSurface = NocturneOnSurface,
            surfaceVariant = NocturneSurfaceElevated,
            onSurfaceVariant = NocturneOnSurfaceVariant,
            surfaceTint = NocturneOnSurface,
            inverseSurface = NocturneOnSurface,
            inverseOnSurface = NocturneCanvas,
            error = Color(0xFFFFB4AB),
            onError = Color(0xFF690005),
            errorContainer = Color(0xFF93000A),
            onErrorContainer = Color(0xFFFFDAD6),
            outline = NocturneMuted,
            outlineVariant = NocturneBorderSubtle,
            scrim = Color.Black,
            surfaceBright = NocturneSurfaceContainerHighest,
            surfaceDim = NocturneCanvas,
            surfaceContainerLowest = NocturneSurfaceLowest,
            surfaceContainerLow = NocturneSurface,
            surfaceContainer = NocturneSurfaceContainer,
            surfaceContainerHigh = NocturneSurfaceContainerHigh,
            surfaceContainerHighest = NocturneSurfaceContainerHighest,
        )
    } else {
        val useSystemDynamicColor = (themeColor == DefaultThemeColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        if (useSystemDynamicColor) {
            dynamicLightColorScheme(context)
        } else {
            rememberDynamicColorScheme(
                seedColor = themeColor,
                isDark = false,
                specVersion = ColorSpec.SpecVersion.SPEC_2025,
                style = PaletteStyle.TonalSpot
            )
        }
    }

    // Apply pureBlack modification if needed, similar to original logic
    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme) {
        if (darkTheme && pureBlack) {
            baseColorScheme.pureBlack(true)
        } else {
            baseColorScheme
        }
    }

    // Use standard MaterialTheme instead of MaterialExpressiveTheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}

fun Bitmap.extractThemeColor(): Color = Color(
    Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .rankedColors(1, DefaultThemeColor.toArgb())
        .first()
)

internal fun Palette.rankedColors(
    desiredColorCount: Int,
    fallbackColor: Int,
): List<Int> = Score.score(
    swatches.associate { it.rgb to it.population },
    desiredColorCount,
    fallbackColor,
    true,
)

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
