package dem.llc.placepicker.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val name: String? = null,
    val position: Point = Point(0.0, 0.0)
) : Parcelable
