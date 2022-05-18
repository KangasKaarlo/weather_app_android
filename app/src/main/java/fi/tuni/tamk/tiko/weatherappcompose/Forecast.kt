package fi.tuni.tamk.tiko.weatherappcompose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import fi.tuni.tamk.tiko.weatherappcompose.ui.theme.WeatherAppComposeTheme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import com.google.gson.Gson
import fi.tuni.tamk.tiko.weatherappcompose.dataclasses.WeatherObject

@Composable
fun ForecastLayout() {
    val data = remember { mutableStateOf<WeatherObject>(WeatherObject()) }

    downloadUrlAsync("https://api.openweathermap.org/data/2.5/weather?q=tampere&appid=ea7cbf93a213ae48e163cf692b5dfa54&units=metric") {
        Log.d("main", it)
        val gson = Gson()
        val weather: WeatherObject = gson.fromJson(it, WeatherObject::class.java)
        data.value = weather
        Log.d("main", weather.name)

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

        Location(data.value.name)
        CurrentWeather()
        CurrentDetails()
        Forecast()
    }
}




@Composable
fun Forecast() {

    Surface(
        modifier = Modifier
        .width(350.dp)
        .height(200.dp)
        .padding(12.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        color = Color.White.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
               .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ForecastDay(img = "sun", degree = "15°C", day = "Mon")
            ForecastDay(img = "sun", degree = "17°C", day = "Tue")
            ForecastDay(img = "sun", degree = "13°C", day = "Wen")
            ForecastDay(img = "sun", degree = "11°C", day = "Thu")
            ForecastDay(img = "sun", degree = "19°C", day = "Fri")
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
            painter = painterResource(id = R.drawable.sun),
            contentDescription = img,
            modifier = Modifier
               .size(25.dp))
        Spacer(modifier = Modifier.size(25.dp))
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
fun CurrentDetails() {
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
            Detail(img = "sun", text = "Wind")
            Detail(img = "sun", text = "Dir")
            Detail(img = "sun", text = "Humidity")
            Detail(img = "sun", text = "Rain")

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
fun Detail(img : String, text : String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Image(
        painter = painterResource(id = R.drawable.sun),
        contentDescription = img,
        modifier = Modifier.size(25.dp))
        Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = Color.White
        )
    }
}

@Composable
fun CurrentWeather() {
    val spacerSize = 75.dp
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(spacerSize))
        Row() {
        Text(
           text = "27°C",
           textAlign = TextAlign.Center,
           style = MaterialTheme.typography.h1,
           color = Color.White
        )
        Image(
           painter = painterResource(id = R.drawable.sun),
           contentDescription = "weatherImg",
           modifier = Modifier.size(35.dp))
        }
        Text(
        text = "Feels like 22°C",
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