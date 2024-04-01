package dem.llc.placepicker.presentation.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dem.llc.placepicker.domain.entity.Point

@Composable
fun LocationBottomDetail(
    modifier: Modifier = Modifier,
    location: Point,
    city: String,
    onSelect: () -> Unit

){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp) ,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "%.5f,  %.5f".format(location.latitude, location.longitude),
                fontSize = 18.sp,
                color = Color(0xFF89aaef)
            )
            Text(
                text = city ?: "Unknown location!",
                fontSize = 22.sp,
                color = Color.Black
            )
            Button(
                modifier = Modifier.fillMaxWidth(0.6f),
                onClick = onSelect
            ) {
                Text(text = "Select")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LocationBottomSheetPreview() {
    /*LocationBottomDetail(
        modifier = Modifier.fillMaxWidth(),
        location = Location("Luanda", Point(0.0, 0.0))
    )*/
}