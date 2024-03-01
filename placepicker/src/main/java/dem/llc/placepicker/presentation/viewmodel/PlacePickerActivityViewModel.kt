package dem.llc.placepicker.presentation.viewmodel

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dem.llc.placepicker.entity.Location
import dem.llc.placepicker.entity.Point
import dem.llc.placepicker.ui.state.SearchBarState
import dem.llc.placepicker.util.location.DefaultLocationClient
import dem.llc.placepicker.util.location.LocationRepository
import dem.llc.placepicker.util.location.LocationRepository.getAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class PlacePickerActivityViewModel : ViewModel() {
    val currLocation = mutableStateOf(
        Location (
            name = "Default place",
            position = Point(0.0, 0.0)
        )
    )

    val searchBarState = SearchBarState{}

    fun loadLocation(locationClient: DefaultLocationClient){
        locationClient.getCurrentLocation {
            currLocation.value = it
        }
    }

    fun getName(context: Context, point: Point) = viewModelScope.launch (Dispatchers.IO){
        currLocation.value.name = null

        Geocoder(context, Locale("en"))
            .getAddress(point.latitude, point.longitude){address ->
                address?.let{
                    currLocation.value.name = address.getAddressLine(0)
                } ?: run{
                    currLocation.value.name = "Unknown place"
                }
            }
    }
}
