package dem.llc.placepicker.ui.state

import androidx.compose.runtime.mutableStateOf

class SearchBarState (val onSearchCallback: (String) -> Unit) {
    private val _query = mutableStateOf("")
    val query : String
        get() = _query.value

    fun onQueryChange(newQuery: String){
        _query.value = newQuery
        onSearchCallback(newQuery)
    }
}