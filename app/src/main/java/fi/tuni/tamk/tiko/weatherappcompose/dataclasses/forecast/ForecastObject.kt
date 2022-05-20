package fi.tuni.tamk.tiko.weatherappcompose.dataclasses.forecast

data class ForecastObject(
    val city: City = City(),
    val cnt: Int = 0,
    val cod: String = "",
    val list: List<Forecast> = listOf(Forecast()),
    val message: Int = 0
)