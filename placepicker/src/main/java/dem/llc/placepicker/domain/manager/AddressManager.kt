package dem.llc.placepicker.domain.manager

import android.location.Geocoder
import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.util.Result

interface AddressManager {
    suspend fun getAddressName(point: Point): Result
    suspend fun getLocationByName(name: String): Result
}