package dem.llc.placepicker.util.location

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import dem.llc.placepicker.entity.Location

class DefaultLocationClient(
    private val client: FusedLocationProviderClient
){
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: (Location) -> Unit) {
        client.lastLocation.addOnSuccessListener {
            if (it==null) return@addOnSuccessListener
            onSuccess(Location(latitude = it.latitude, longitude = it.longitude))
        }
    }
}