package dem.llc.placepicker.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val name: String? = null,
    val latitude: Double,
    val longitude: Double
) : Parcelable
