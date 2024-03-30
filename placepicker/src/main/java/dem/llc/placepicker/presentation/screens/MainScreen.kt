package dem.llc.placepicker.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalMaterial3Api::class,DelicateCoroutinesApi::class)
@Composable
fun MainScreen(
    viewModel: PlacePickerActivityViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val uiState = viewModel.location.value

    LaunchedEffect(key1 = cameraPositionState.position.target) {

    }


    Box(
        Modifier.fillMaxSize()
    ) {
        MapView(
            cameraPositionState = cameraPositionState
        )
        MapSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(22.dp)
                .fillMaxWidth(),
            onSearch = { }
        )
        LocationBottomDetail(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            location = uiState,
            city = viewModel.test.value
        )
    }
}

