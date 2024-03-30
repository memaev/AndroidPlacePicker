package dem.llc.placepicker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dem.llc.placepicker.R
import dem.llc.placepicker.util.bitmap.bitmapDescriptorFromVector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = {
            scope.launch{ cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(it, 15f),600)
            }
        },
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isTrafficEnabled = false,
            isMyLocationEnabled = true
        )
    ) {
        Marker(
            state = MarkerState(position = cameraPositionState.position.target),
            icon = bitmapDescriptorFromVector(context,R.drawable.marker_icon)
        )
    }
}
