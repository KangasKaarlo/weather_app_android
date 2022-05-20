package fi.tuni.tamk.tiko.weatherappcompose.dataclasses.currenweather

data class Main(
    val feels_like: Double = 0.00,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val temp: Double = 0.00,
    val temp_max: Double = 0.00,
    val temp_min: Double = 0.00
)