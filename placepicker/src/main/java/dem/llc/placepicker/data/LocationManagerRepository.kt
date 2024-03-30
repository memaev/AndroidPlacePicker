package dem.llc.placepicker.data

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.manager.LocationManager
import dem.llc.placepicker.domain.util.Result

class LocationManagerRepository(
   private var  locationClient: FusedLocationProviderClient
) : LocationManager {
    override suspend fun getAddressName(location: LatLng): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationsByName(name: String): Result {
        TODO("Not yet implemented")
    }
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(onSuccess: (Point) -> Unit) {
        locationClient.lastLocation
            .addOnFailureListener {
                it.printStackTrace()
            }
            .addOnCompleteListener { location ->
                val result = location.result
                onSuccess(Point(result.latitude, result.longitude))
            }
    }
}