package dem.llc.placepicker.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import dem.llc.placepicker.R
import dem.llc.placepicker.util.bitmap.bitmapDescriptorFromVector

@Composable
fun Map(
    context: Context,
    cameraPositionState: CameraPositionState
){
    val uiSettings by remember{ mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }

    GoogleMap (
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isTrafficEnabled = true,
            isMyLocationEnabled = false
        ),
        uiSettings = uiSettings
    ){
        Marker(
            state = MarkerState(position = cameraPositionState.position.target),
            title = "My position",
            snippet = "This is my position description",
            draggable = true,
            icon = bitmapDescriptorFromVector(context, R.drawable.marker_icon)
        )
    }
}