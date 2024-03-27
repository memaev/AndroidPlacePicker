package dem.llc.placepicker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.maps.android.compose.rememberCameraPositionState
import dem.llc.placepicker.entity.Point
import dem.llc.placepicker.presentation.bottomSheet.LocationBottomSheet
import dem.llc.placepicker.presentation.components.Map
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel
import dem.llc.placepicker.ui.components.CustomSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: PlacePickerActivityViewModel = viewModel()
) {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val point = Point(
        latitude = cameraPositionState.position.target.latitude,
        longitude = cameraPositionState.position.target.longitude
    )
    LaunchedEffect(mainViewModel) {
        mainViewModel.updateLocation(context, point)
    }

    BottomSheetScaffold(sheetContent = {
        LocationBottomSheet(mainViewModel.location.value)
    }) {
        Map(
            context = LocalContext.current,
            cameraPositionState = cameraPositionState
        )

        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 15.dp)
        ){
            CustomSearchBar(searchBarState = mainViewModel.searchBarState)
        }
    }
}