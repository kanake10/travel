@file:OptIn(ExperimentalPagerApi::class)

package com.example.travel.presentation.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.travel.R
import com.example.travel.core.toTitleCase
import com.example.travel.models.Result
import com.example.travel.presentation.viewmodel.PropertyViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.boguszpawlowski.composecalendar.StaticCalendar

@Composable
fun ListingsDetailScreen(
    id:String,
    viewModel: PropertyViewModel = hiltViewModel()
) {
    val state = viewModel.property.value
    val current = state.property.filter { it.id == id }
    LazyColumn {
        items(current) { data ->
            Column(modifier = Modifier.wrapContentSize()) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            HorizontalPager(
                                count = data.photos!!.size,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(190.dp)
                            ) { page ->
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    painter = rememberImagePainter(data = data.photos!![page]),
                                    contentDescription = "Image",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
            PropertyDetails(data = data)
            HostDetails(data = data)
            PropertyLocation(result = data)
            StaticCalendar()
        }
    }

}

@Composable
fun PropertyDetails(data: Result) {
    // First Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
    ) {
        FeatureRow(
            icon = R.drawable.baseline_person_24,
            value = "${data.accommodates} Accommodates"
        )

        Spacer(modifier = Modifier.weight(1f))

        FeatureRow(
            icon = R.drawable.baseline_shower_24,
            value = "${data.bathrooms} Bathrooms",
            endPadding = 30.dp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    // Second Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        FeatureRow(
            icon = R.drawable.baseline_single_bed_24,
            value = "${data.beds} Bed",
            endPadding = 30.dp
        )
    }
}

@Composable
fun FeatureRow(
    @DrawableRes icon: Int,
    value: String,
    endPadding: Dp = 0.dp
) {
    Row(
        modifier = Modifier
            .padding(end = endPadding)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun HostDetails(data: Result) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(text = "Host details")
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    // Host Image
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopEnd),
                        painter = rememberImagePainter(data = data.host_thumbnail_url),
                        contentDescription = "Host Image",
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Host Nam
                        Text(text = "Host Name: ${data.host_name}")
                        Text(text = "Location: ${data.host_location}")
                        Text(text = "Score Rating: ${data.review_scores_rating}")
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyLocation(result: Result) {
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    val location = result.geolocation?.let { LatLng(result.geolocation.lat, it.lon) }
    val cameraPositionState = rememberCameraPositionState {
        position = location?.let { CameraPosition.fromLatLngZoom(it, 16f) }!!
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val marker = location?.let {
        MarkerOptions()
        .position(it)
        .title(
            result.name?.let { toTitleCase(it) }
        )
        .snippet(result.name?.let { toTitleCase(it) })
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Property Location",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                }
            ) {
                if (marker != null) {
                    Marker(
                        state = MarkerState(
                            position = marker.position
                        ),
                        title = marker.title,
                        snippet = marker.snippet
                    ) {

                    }
                }
            }
        }
        result.city?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}


