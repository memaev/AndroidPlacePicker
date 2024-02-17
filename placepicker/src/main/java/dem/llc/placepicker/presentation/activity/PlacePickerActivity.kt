package dem.llc.placepicker.presentation.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
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
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {returnResult()}) {
                        Text(text = "Return OK result")
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PlacePickerTheme {

    }
}