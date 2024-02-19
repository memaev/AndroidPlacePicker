package dem.llc.placepicker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dem.llc.placepicker.entity.Location
import dem.llc.placepicker.entity.Point
import dem.llc.placepicker.util.location.DefaultLocationClient

class PlacePickerActivityViewModel : ViewModel() {
    val defaultPlace = mutableStateOf(
        Location (
            name = "Default place",
            position = Point(0.0, 0.0)
        )
    )

    fun loadLocation(locationClient: DefaultLocationClient){
        locationClient.getCurrentLocation {
            defaultPlace.value = it
        }
    }
}