package fi.tuni.tamk.tiko.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.tuni.tamk.tiko.weatherappcompose.ui.theme.WeatherAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Layout()
                }
            }
        }
    }
}

@Composable
fun Layout() {
    Column(modifier = Modifier
        .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Location()
        CurrentWeather()
        CurrentDetails()
        Forecast()

    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppComposeTheme {
        Layout()
    }
}

@Composable
fun Forecast() {
    Column() {
        Card(
            modifier = Modifier
                .width(350.dp)
                .height(200.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        ) {

        }
    }

}

@Composable
fun CurrentDetails() {
        Card(
            modifier = Modifier
                .width(350.dp)
                .height(125.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        ) {

        }
}

@Composable
fun Location() {
    Text(
        text = "Location Here",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4
    )
}

@Composable
fun CurrentWeather() {
    val spacerSize = 125.dp
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(spacerSize))
        Row() {
            Text(
                text = "27°C",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h2
            )
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "weatherImg",
                modifier = Modifier.size(35.dp))
        }
    }
    Spacer(modifier = Modifier.size(spacerSize))
}