package dem.llc.placepicker.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.manager.LocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
class PlacePickerActivityViewModel(
    locationManager: LocationManager
) : ViewModel() {

    private val _location: MutableStateFlow<Point> = MutableStateFlow(Point(0.0, 0.0))
    val location = _location.asStateFlow()

     var test = mutableStateOf("")

    init {
        viewModelScope.launch(Dispatchers.IO){
            locationManager.getCurrentLocation { point ->
                _location.value = point
            }
        }
    }

    fun updateLocation(location: LatLng) {
        _location.value = Point(location.latitude, location.longitude)
    }

    private fun updateAddress(latitude: Double, longitude: Double, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {

        }

    }

    fun generateFinalAddress(address: String): String {
        val parts = address.split(",")
        return if (parts.size >= 3) "${parts[1]}, ${parts[2]}" else if (parts.size == 2) parts[1] else parts[0]
    }

}
