package dem.llc.placepicker.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dem.llc.placepicker.R
import dem.llc.placepicker.entity.Location
import dem.llc.placepicker.entity.Point
import dem.llc.placepicker.presentation.bottomSheet.LocationBottomSheet
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel
import dem.llc.placepicker.ui.components.CustomSearchBar
import dem.llc.placepicker.ui.theme.PlacePickerTheme
import dem.llc.placepicker.util.bitmap.bitmapDescriptorFromVector
import dem.llc.placepicker.util.location.DefaultLocationClient
import dem.llc.placepicker.util.namespace.LOCATION

class PlacePickerActivity : ComponentActivity() {

    private val viewModel: PlacePickerActivityViewModel by viewModels()

    private lateinit var locationClient: DefaultLocationClient


    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ permissions->
        permissions.forEach { (_, isGranted) ->
            if (!isGranted) return@forEach
        }
        viewModel.loadLocation(locationClient)
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = DefaultLocationClient(LocationServices.getFusedLocationProviderClient(baseContext))
        checkPermission()

        setContent {
            PlacePickerTheme {
                val cameraPositionState = rememberCameraPositionState()

                viewModel.currLocation.value = Location(
                    name = "Default place",
                    position = Point(
                        latitude = cameraPositionState.position.target.latitude,
                        longitude = cameraPositionState.position.target.longitude
                    )
                )
                viewModel.setAddress(baseContext, viewModel.currLocation.value.position.latitude,viewModel.currLocation.value.position.longitude)

                BottomSheetScaffold(sheetContent = {
                    LocationBottomSheet()
                }) {
                    MainScreen(
                        context = baseContext,
                        cameraPositionState = cameraPositionState
                    )
                }
            }
        }
    }

    private fun returnResult(){
        val intent = Intent()
        intent.putExtra(LOCATION, viewModel.currLocation.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun checkPermission(){
        val isFineLocationGranted = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseLocationGranted = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (isFineLocationGranted != PackageManager.PERMISSION_GRANTED || isCoarseLocationGranted != PackageManager.PERMISSION_GRANTED)
            permissionRequestLauncher.launch(arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.INTERNET
            ))
        else
            viewModel.loadLocation(locationClient)
    }
}

@Composable
fun MainScreen(
    context: Context,
    cameraPositionState: CameraPositionState,
    viewModel: PlacePickerActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    Map(
        context = context,
        cameraPositionState = cameraPositionState
    )

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 15.dp)
    ){
        CustomSearchBar(searchBarState = viewModel.searchBarState)
    }
}

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

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PlacePickerTheme {
        val cameraPositionState = rememberCameraPositionState()
//        MainScreen(curLocation = LatLng(0.0, 0.0), cameraPositionState = cameraPositionState)
    }
}
