package dem.llc.placepicker.presentation.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel
import dem.llc.placepicker.ui.theme.PlacePickerTheme
import dem.llc.placepicker.util.location.DefaultLocationClient
import dem.llc.placepicker.util.namespace.LOCATION

class PlacePickerActivity : ComponentActivity() {

    private val viewModel: PlacePickerActivityViewModel by viewModels()

    private lateinit var locationClient: DefaultLocationClient


    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){permissions->
        permissions.forEach { (_, isGranted) ->
            if (!isGranted) return@forEach
        }
        viewModel.loadLocation(locationClient)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = DefaultLocationClient(LocationServices.getFusedLocationProviderClient(baseContext))
        checkPermission()

        setContent {
            PlacePickerTheme {
                val cameraPositionState = rememberCameraPositionState()

                LaunchedEffect(key1 = viewModel.defaultPlace.value.position){
                    cameraPositionState.centerOnLocation(viewModel.defaultPlace.value.position.toLatLng())
                }

                MainScreen(
                    cameraPositionState = cameraPositionState,
                    curLocation = viewModel.defaultPlace.value.position.toLatLng()
                )
            }
        }
    }

    private fun returnResult(){
        val intent = Intent()
        intent.putExtra(LOCATION, viewModel.defaultPlace.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun checkPermission(){
        val isFineLocationGranted = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseLocationGranted = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (isFineLocationGranted != PackageManager.PERMISSION_GRANTED || isCoarseLocationGranted != PackageManager.PERMISSION_GRANTED)
            permissionRequestLauncher.launch(arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        else
            viewModel.loadLocation(locationClient)
    }
}

@Composable
fun MainScreen(curLocation: LatLng, cameraPositionState: CameraPositionState){
    Map(
        cameraPositionState = cameraPositionState,
        curLocation = curLocation
    )
}

@Composable
fun Map(curLocation: LatLng, cameraPositionState: CameraPositionState){
    val markerPos = LatLng(curLocation.latitude, curLocation.longitude)
    GoogleMap (
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapType = MapType.HYBRID,
            isTrafficEnabled = true
        )
    ){
        Marker(
            state = MarkerState(position = markerPos),
            title = "My position",
            snippet = "This is my position description",
            draggable = true
        )
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1500
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PlacePickerTheme {
        val cameraPositionState = rememberCameraPositionState()
        MainScreen(curLocation = LatLng(0.0, 0.0), cameraPositionState = cameraPositionState)
    }
}