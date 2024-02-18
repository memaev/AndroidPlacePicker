package dem.llc.androidplacepicker.presentation.activity

import android.content.Intent
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
import dem.llc.androidplacepicker.presentation.viewModel.MainActivityViewModel
import dem.llc.androidplacepicker.ui.theme.AndroidPlacePickerTheme
import dem.llc.placepicker.entity.Location
import dem.llc.placepicker.presentation.activity.PlacePickerActivity
import dem.llc.placepicker.util.intent.PlacePickerIntent
import dem.llc.placepicker.util.namespace.API_KEY
import dem.llc.placepicker.util.namespace.LOCATION
import dem.llc.placepicker.util.parce.getParcelable

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val pickerActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        val address = result.data?.getParcelable<Location>(LOCATION)
        if (result.resultCode== RESULT_OK && address!=null){
            viewModel.result.value = address.latitude.toString()
        }else{
            viewModel.result.value = "Result FAILED"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidPlacePickerTheme {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Result()

                    Button(onClick = { openPicker() }) {
                        Text(text = "Open activity for result")
                    }
                }
            }
        }
    }

    private fun openPicker(){
        val intent = PlacePickerIntent.Builder()
            .setApiKey("your-api-key")
            .build(this@MainActivity)
        pickerActivityResult.launch(intent)
    }

    @Composable
    fun Result(){
        Text(text = viewModel.result.value)
    }
}

