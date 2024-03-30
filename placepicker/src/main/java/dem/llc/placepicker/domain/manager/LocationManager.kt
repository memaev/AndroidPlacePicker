package dem.llc.placepicker.domain.manager

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import dem.llc.placepicker.domain.entity.Location
import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.util.Result

interface LocationManager {
    suspend fun getAddressName(location: LatLng): Result
    suspend fun getLocationsByName(name: String): Result
    suspend fun getCurrentLocation(onSuccess: (Point) -> Unit)
}