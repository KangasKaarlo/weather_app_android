package fi.tuni.tamk.tiko.weatherappcompose

import android.os.Bundle
import android.telecom.Call
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.tuni.tamk.tiko.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.intellij.lang.annotations.JdkConstants

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
            backgroundColor = Color.White.copy(alpha = 0.2f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
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
            backgroundColor = Color.White.copy(alpha = 0.2f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
        ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                    Detail(img = "weatherImg", text = "Wind")
                    Detail(img = "weatherImg", text = "Dir")
                    Detail(img = "weatherImg", text = "Humidity")
                    Detail(img = "weatherImg", text = "Rain")

                }
            }
}

@Composable
fun Location() {
    Text(
        text = "Location Here",
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
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
    }
}

@Composable
fun CurrentWeather() {
    val spacerSize = 100.dp
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
    }
    Spacer(modifier = Modifier.size(spacerSize))
}