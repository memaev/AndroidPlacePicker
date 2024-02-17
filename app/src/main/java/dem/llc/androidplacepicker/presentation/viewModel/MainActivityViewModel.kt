package dem.llc.androidplacepicker.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val result = mutableStateOf("No result")
}