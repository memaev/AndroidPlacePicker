package dem.llc.placepicker.util.location

import android.location.Geocoder
import android.os.Build

//using deprecation annotation because if the SDK<TIRAMISU we will use the old method
@Suppress("DEPRECATION")
object LocationRepository {
    fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
        address: (android.location.Address?) -> Unit
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
            return
        }

        try {
            address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
        } catch(e: Exception) {
            //will catch if there is an internet problem
            address(null)
        }
    }
}