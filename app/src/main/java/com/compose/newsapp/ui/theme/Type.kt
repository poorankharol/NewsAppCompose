package com.compose.newsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.compose.newsapp.R

val ubuntuRegular = FontFamily(Font(R.font.ubuntu_regular, FontWeight.Normal))
val ubuntuBold = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ubuntuRegular,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    //Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = ubuntuRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ubuntuRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = ubuntuRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)