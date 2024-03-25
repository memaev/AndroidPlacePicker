package dem.llc.placepicker.domain.manager

import android.location.Geocoder

typealias ShortAddress = String
typealias FullAddress = String

interface AddressManager {
    suspend fun getAddress(geocoder: Geocoder, latitude: Double, longitude: Double): Pair<ShortAddress, FullAddress>
}