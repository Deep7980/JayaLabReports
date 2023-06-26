package com.jaya.app.labreports.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.jaya.app.labreports.R

// Set of Material typography styles to start with
object AppDownloadableFonts {
    @OptIn(ExperimentalTextApi::class)
    fun getGoogleFontFamily(
        vararg name: String,
        provider: GoogleFont.Provider = googleFontProvider,
        weights: List<FontWeight>,
    ): List<Font> {
        val fonts = mutableListOf<Font>()
        name.forEach { fName ->
            val byWeights = weights.map { weight ->
                Font(
                    googleFont = GoogleFont(fName),
                    fontProvider = provider,
                    weight = weight
                )
            }
            fonts.addAll(byWeights)
        }
        return fonts
    }

    private val googleFontProvider: GoogleFont.Provider by lazy {
        GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )
    }
}

val defaultFontFamily = FontFamily(
    fonts = AppDownloadableFonts.getGoogleFontFamily(
        name = arrayOf("Poppins", "Calibri"),
        weights = listOf(
            FontWeight.Normal,
            FontWeight.Medium,
            FontWeight.Bold
        )
    )
)

val DefaultTypography = Typography(
    defaultFontFamily = defaultFontFamily
)
