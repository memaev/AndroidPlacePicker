package dem.llc.placepicker.data.managerImpl

import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.domain.manager.LocationManager
import dem.llc.placepicker.domain.util.Result

class LocationManagerImpl : LocationManager {
    override suspend fun getAddressName(point: Point): Result {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationByName(name: String): Result {
        TODO("Not yet implemented")
    }
}