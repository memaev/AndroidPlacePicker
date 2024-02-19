package dem.llc.placepicker.entity

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable{
    fun toLatLng() : LatLng{
        return LatLng(latitude, longitude)
    }
}
