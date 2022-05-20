package fi.tuni.tamk.tiko.weatherappcompose.dataclasses.forecast

data class Forecast(
    val clouds: Clouds = Clouds(),
    val dt: Int = 0,
    val dt_txt: String = "",
    val main: Main = Main(),
    val pop: Double = 0.00,
    val sys: Sys = Sys(),
    val visibility: Int = 0,
    val weather: List<Weather> = listOf(Weather()),
    val wind: Wind = Wind()
)