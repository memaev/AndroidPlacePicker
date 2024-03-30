package dem.llc.placepicker.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.rememberCameraPositionState
import dem.llc.placepicker.presentation.bottomSheet.LocationBottomDetail
import dem.llc.placepicker.presentation.components.MapView
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel
import dem.llc.placepicker.ui.components.MapSearchBar
import kotlinx.coroutines.DelicateCoroutinesApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition

@Composable
fun MainScreen(
    viewModel: PlacePickerActivityViewModel,
) {
    val uiState = viewModel.location.collectAsState().value
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.toLatLng(), 11f)
    }

    LaunchedEffect(uiState) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(uiState.toLatLng(), 15f), 600)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            viewModel.updateLocation(cameraPositionState.position.target)
        }
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        MapView(cameraPositionState = cameraPositionState)
        MapSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(22.dp)
                .fillMaxWidth(),
            onSearch = { TODO() }
        )
        LocationBottomDetail(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            location = uiState,
            city = viewModel.test.value,
            onSelect = { TODO() }
        )
    }
}

