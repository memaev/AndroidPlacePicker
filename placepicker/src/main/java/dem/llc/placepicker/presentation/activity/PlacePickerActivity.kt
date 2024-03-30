package dem.llc.placepicker.presentation.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.rememberCameraPositionState
import dem.llc.placepicker.data.LocationManagerRepository
import dem.llc.placepicker.domain.entity.Point
import dem.llc.placepicker.presentation.screens.MainScreen
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel
import dem.llc.placepicker.ui.theme.PlacePickerTheme
import dem.llc.placepicker.util.namespace.LOCATION


class PlacePickerActivity : ComponentActivity() {

    private lateinit var placePickerViewModel: PlacePickerActivityViewModel

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ permissions ->
        permissions.forEach { (_, isGranted) ->
            if (!isGranted) return@forEach
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationManager = LocationManagerRepository(LocationServices.getFusedLocationProviderClient(this))
        placePickerViewModel = PlacePickerActivityViewModel(locationManager)
        checkPermission()
        


        setContent {
            PlacePickerTheme {
                MainScreen(placePickerViewModel)
            }
        }
    }

    private fun returnResult(point: Point){
        val intent = Intent()
        intent.putExtra(LOCATION, point)
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
