package dem.llc.placepicker.util.location

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dem.llc.placepicker.entity.Location
import dem.llc.placepicker.entity.Point

class DefaultLocationClient(
    private val client: FusedLocationProviderClient
){
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: (Location) -> Unit) {
        client.lastLocation.addOnSuccessListener {
            if (it==null) return@addOnSuccessListener
            onSuccess(Location(position = Point(it.latitude, it.longitude)))
        }
    }
}