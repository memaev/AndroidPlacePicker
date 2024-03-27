package dem.llc.placepicker.presentation.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dem.llc.placepicker.entity.Point
import dem.llc.placepicker.presentation.viewmodel.PlacePickerActivityViewModel

@Composable
fun LocationBottomSheet(
    location: Point
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = location.latitude.toString()
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = location.longitude.toString()
            )
        }

//        if (viewModel.currLocation.value.name==null)
//            CircularProgressIndicator()
//        else
//            Text(
//                text = viewModel.currLocation.value.name!!
//            )
    }
}

@Composable
@Preview(showBackground = true)
fun LocationBottomSheetPreview() {
    LocationBottomSheet(location = Point(0.0, 0.0))
}