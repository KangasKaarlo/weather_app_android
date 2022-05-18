package fi.tuni.tamk.tiko.weatherappcompose.dataclasses

data class Weather(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)