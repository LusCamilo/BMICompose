package br.senai.sp.jandira.bmicompose.utils

import androidx.compose.ui.graphics.Color

fun BMIDescriptionColor(bmi: Double): Color {

    if (bmi < 18.5)
       return Color(255, 87, 34, 255)
    else if (bmi > 18.5 && bmi < 25)
        return Color.Green
    else if (bmi > 24.9 && bmi < 30)
        return Color(255, 87, 34, 255)
    else
        return Color.Red

}
fun BMIDescriptionText(bmi: Double): String {

    if (bmi < 18.5)
        return "underweight"
    else if (bmi > 18.5 && bmi < 25)
        return "normal weight"
    else if (bmi > 24.9 && bmi < 30)
        return "overweight"
    else
        return "obesity"

}