package dem.llc.placepicker.domain.manager

import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.util.Result

interface LocationManager {
    suspend fun getAddressName(point: Point): Result
    suspend fun getLocationByName(name: String): Result
}