package com.wesleyelliott.weather.ui.weather

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.WeatherChoice

private const val backgroundShapeOffset = 20f
private val backgroundShape = GenericShape { size, _ ->
    moveTo(0f, backgroundShapeOffset)
    cubicTo(
        x1 = size.width * 0.2f,
        y1 = -backgroundShapeOffset,
        x2 = size.width * 0.75f,
        y2 = backgroundShapeOffset,
        x3 = size.width,
        y3 = size.height * 0.33f,
    )
    lineTo(
        size.width,
        size.height
    )
    lineTo(
        0f,
        size.height
    )
    close()
}

@Composable
fun WeatherScreen(
    weatherChoice: WeatherChoice
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .align(Alignment.TopCenter)
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                data = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg",
                contentScale = ContentScale.FillBounds,
                contentDescription = "My content description"
            )
        }

        Box(
            modifier = Modifier
                .padding(top = maxHeight / 3)
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .clip(backgroundShape)
                .background(Color(0xFF212F3D))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = 40.dp,
                        bottom = 30.dp,
                        start = 40.dp,
                        end = 40.dp
                    ),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Column {
                    Text("Tokyo", style = MaterialTheme.typography.h2)
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.PinDrop,
                            contentDescription = ""
                        )
                        Text(
                            text = "183km away",
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = "14°",
                        style = MaterialTheme.typography.h1
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .alignBy(LastBaseline)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_sun),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = "Sunny",
                                style = MaterialTheme.typography.caption
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_sun),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = "Hgh pollen",
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ForecastBox(
                        time = "19:00",
                        temperature = "12°",
                        icon = R.drawable.ic_sun
                    )

                    ForecastBox(
                        time = "21:00",
                        temperature = "11°",
                        icon = R.drawable.ic_sun
                    )

                    ForecastBox(
                        time = "23:00",
                        temperature = "10°",
                        icon = R.drawable.ic_sun
                    )
                    ForecastBox(
                        time = "01:00",
                        temperature = "8°",
                        icon = R.drawable.ic_sun
                    )
                }
            }
        }
    }
}

@Composable
private fun ForecastBox(
    time: String,
    temperature: String,
    @DrawableRes icon: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.subtitle1,
        )
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = temperature
        )
        Text(
            text = temperature,
            style = MaterialTheme.typography.subtitle1,
        )
    }
}