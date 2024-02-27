package dem.llc.placepicker.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dem.llc.placepicker.ui.state.SearchBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(searchBarState: SearchBarState){
    SearchBar(query = searchBarState.query,
        onQueryChange = {searchBarState.onQueryChange(it)},
        onSearch = {searchBarState.onSearchCallback(it)},
        active = false,
        onActiveChange = {}) {


    }
}

@Composable
@Preview(showBackground = true)
fun CustomSearchBarPreview(){
    CustomSearchBar(SearchBarState{})
}