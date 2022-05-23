package fi.tuni.tamk.tiko.weatherappcompose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.google.gson.Gson
import fi.tuni.tamk.tiko.weatherappcompose.dataclasses.currenweather.Main
import fi.tuni.tamk.tiko.weatherappcompose.dataclasses.currenweather.WeatherObject
import fi.tuni.tamk.tiko.weatherappcompose.dataclasses.forecast.ForecastObject
import kotlin.math.roundToInt

@Composable
@Preview(showSystemUi = true)
fun ForecastLayout() {
    val currentWeather = remember { mutableStateOf<WeatherObject>(WeatherObject()) }
    val foreCast = remember { mutableStateOf<ForecastObject>(ForecastObject()) }


    //Fetching the forecast
    downloadUrlAsync("https://api.openweathermap.org/data/2.5/forecast?q=tampere&appid=ea7cbf93a213ae48e163cf692b5dfa54&units=metric") {
        Log.d("main", it)
        val gson = Gson()
        val weather: ForecastObject = gson.fromJson(it, ForecastObject::class.java)
        foreCast.value = weather
    }

    //Fetching current weather
    downloadUrlAsync("https://api.openweathermap.org/data/2.5/weather?q=tampere&appid=ea7cbf93a213ae48e163cf692b5dfa54&units=metric") {
        Log.d("main", it)
        val gson = Gson()
        val weather: WeatherObject = gson.fromJson(it, WeatherObject::class.java)
        currentWeather.value = weather
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFD16BA5),
                        Color(0xFF86A8E7),
                        Color(0xFFCEFFFC)
                    )
                )
            )
    )
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Location(currentWeather.value.name)
        CurrentWeather(currentWeather.value)
        CurrentDetails(currentWeather.value)
        Forecast(forecast = foreCast.value)
    }
}



//Box containing the forecast for the three following days
@Composable
fun Forecast(forecast : ForecastObject) {

    Surface(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        color = Color.White.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "3 Day Forecast",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .size(15.dp),
            )
            Divider(color = Color.White.copy(alpha = 0.8f))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                var listSize = 0
                forecast.list.forEach {
                    //Making sure we are displaying the weather at noon and dismissing all the unnecessary data given by the API
                    if (it.dt_txt.contains("12:00:00") && listSize < 4) {
                        if (listSize == 0) {
                            listSize++
                        } else {
                            ForecastDay(img = it.weather[0].icon, degree = "${it.main.temp.roundToInt()}°C", day = "asd")
                            listSize++
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ForecastDay(img : String, degree : String , day : String) {
    Column(
        modifier = Modifier
                .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${img}@2x.png"),
            contentDescription = img,
            modifier = Modifier
               .size(65.dp))
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = degree,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(25.dp))
        Text(
            text = day,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }
}

@Composable
fun CurrentDetails(data: WeatherObject) {
    val directions = listOf<String>("N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW","N");

    Surface(
        modifier = Modifier
            .width(350.dp)
            .height(125.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        color = Color.White.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Detail(data = "${data.wind.speed.toString()} m/s", text = "Wind")
            //+180 % 360 cause OpenWeatherMap gives the direction the wind is coming from
            //and we want to show the direction it's going to
            Detail(data = directions[((data.wind.deg  + 180) % 360  / 22.5).roundToInt()], text = "Dir")
            Detail(data = data.main.humidity.toString(), text = "Humidity")

        }
    }
}


@Composable
fun Location(header : String) {
    Text(
        text = header,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        color = Color.White
    )
}

@Composable
fun Detail(data : String, text : String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = data,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
        Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = Color.White
        )
    }
}

@Composable
fun CurrentWeather(weather : WeatherObject) {
    val spacerSize = 75.dp
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(spacerSize))
        Row() {
        Text(
           text = weather.main.temp.roundToInt().toString(),
           textAlign = TextAlign.Center,
           style = MaterialTheme.typography.h1,
           color = Color.White
        )
        Image(
           painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"),
           contentDescription = "weatherImg",
           modifier = Modifier.size(85.dp))
        }
        Text(
        text = "Feels like ${weather.main.feels_like.roundToInt()}",
        style = MaterialTheme.typography.subtitle1,
        color = Color.White
        )
        Spacer(modifier = Modifier.size(spacerSize))
    }
}

private fun downloadUrlAsync(url: String, function: (String) -> Unit) {
    thread {
        val json = getUrl(url)
            if (json != null) {
                function(json)
            }
    }
}

fun getUrl(input: String) : String? {
    val url = URL(input)
    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
    BufferedReader(InputStreamReader(connection.getInputStream())).use {
        return it.readLine()
    }
}