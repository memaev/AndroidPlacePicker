package dem.llc.placepicker.presentation.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
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
import kotlinx.coroutines.runBlocking
import java.util.Locale

class PlacePickerActivityViewModel : ViewModel() {

    private var addresses: List<Address>? = null
    private var shortAddress = ""
    private var fullAddress = ""
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

    fun getName(context: Context,latitude: Double,
                longitude: Double) = viewModelScope.launch (Dispatchers.IO){
        currLocation.value.name = null

        Geocoder(context, Locale("en"))
            .getAddress(latitude, longitude){address ->
                address?.let{
                    currLocation.value.name = address.countryName.toString()
                } ?: run{
                    currLocation.value.name = "Unknown place"
                }
            }
    }

    fun setAddress(
        context: Context,
        latitude: Double,
        longitude: Double
    ) {
        val geoCoder = Geocoder(context, Locale.getDefault())
        try {


            val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            this.addresses= addresses
            return if (addresses != null && addresses.size != 0) {
                fullAddress = addresses[0].getAddressLine(
                    0
                ) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                currLocation.value.name = generateFinalAddress(fullAddress).trim()
            } else {
                shortAddress = ""
                fullAddress = ""
            }
        } catch (e: Exception) {
            //Time Out in getting address
            e.message?.let { Log.e("TAG", it) }
            shortAddress = ""
            fullAddress = ""
            addresses = null
        }
    }
    fun generateFinalAddress(
        address: String
    ): String {
        val s = address.split(",")
        return if (s.size >= 3) s[1] + "," + s[2] else if (s.size == 2) s[1] else s[0]
    }
}
