package dem.llc.placepicker.presentation.components

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dem.llc.placepicker.R
import dem.llc.placepicker.util.bitmap.bitmapDescriptorFromVector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState
){
    val context = LocalContext.current

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = { },
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isTrafficEnabled = false,
            isMyLocationEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = cameraPositionState.position.target),
            icon = bitmapDescriptorFromVector(context,R.drawable.marker_icon)
        )
    }
}
