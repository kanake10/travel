package com.example.travel.presentation.listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.travel.models.Result
import com.example.travel.presentation.Screen
import com.example.travel.presentation.sign_in.UserData
import com.example.travel.presentation.viewmodel.PropertyViewModel

@Composable
fun ListingScreen(
    data: UserData,
    navController: NavController,
    viewModel: PropertyViewModel = hiltViewModel(),
) {
    val state = viewModel.property.value
    val amenities = listOf("TV",
        "Internet",
        "Kitchen",
        "Heating",
        "Iron",
        "Essentials",
        "Washer",
        "Dryer",
        "Shampoo",
        "Wireless Internet",
        "Air conditioning",
        )
    var selectedAmenities by remember { mutableStateOf<Set<String>>(emptySet()) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        ) {
            Row(
                modifier = Modifier
                    .clickable { selectedAmenities = emptySet() } // Click to clear selection
            ) {
                Text(
                    text = if (selectedAmenities.isNotEmpty()) {
                        selectedAmenities.joinToString(", ")
                    } else {
                        "Select Amenities"
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
//            Text(
//                text = "Hello " + data!!.username,
//                color = Color.Black,
//                fontSize = 15.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.End,
//                maxLines = 1,
//                modifier = Modifier
//                    .padding(4.dp)
//            )
            Spacer(modifier = Modifier.height(23.dp))
            Column {
                LazyRow {
                    items(amenities) { amenity ->
                        AmenityItem(
                            amenity = amenity,
                            isSelected = selectedAmenities.contains(amenity),
                            onAmenityClick = {
                                selectedAmenities = if (selectedAmenities.contains(amenity)) {
                                    selectedAmenities - amenity // Deselect amenity
                                } else {
                                    selectedAmenities + amenity // Select amenity
                                }
                            }
                        )
                    }
                }
                LazyColumn {
                    items(state.property.filter { property ->
                        selectedAmenities.isEmpty() || property.amenities?.intersect(selectedAmenities)
                            ?.isNotEmpty() == true
                    }) { result ->
                        PropertyItem(
                            property = result,
                            onItemClick = {
                                navController.navigate(Screen.PropertyDetailScreen.route + "/${result.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AmenityItem(amenity: String, isSelected: Boolean, onAmenityClick: () -> Unit) {
    Text(
        text = amenity,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        fontSize = 8.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(5.dp)
            .clickable { onAmenityClick() }
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
            .padding(6.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(4.dp))
            .padding(6.dp)
    )
}

@Composable
fun PropertyItem(property: Result, onItemClick: (Result) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(14.dp)
            .clickable { onItemClick(property) },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberImagePainter(property.photos!!.first()),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            }
            property.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = " Price: " + property.price.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
