package dem.llc.placepicker.ui.components

import android.graphics.Paint.Align
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dem.llc.placepicker.R
import dem.llc.placepicker.ui.state.SearchBarState
import dem.llc.placepicker.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(searchBarState: SearchBarState){
    var searchFieldActive by remember {
        mutableStateOf(false)
    }
    Row (
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.search_logo),
            contentDescription = "Search logo",
            modifier =
             if (searchFieldActive) Modifier
                .size(45.dp)
                .shadow(4.dp, shape = RoundedCornerShape(10.dp))
            else Modifier
                 .size(45.dp)
        )

        Spacer(Modifier.width(15.dp))

        SearchBar(query = searchBarState.query,
            onQueryChange = {searchBarState.onQueryChange(it)},
            onSearch = {searchBarState.onSearchCallback(it)},
            active = false,
            onActiveChange = {searchFieldActive=true},
            colors = SearchBarDefaults.colors(
                containerColor = Gray
            ),
            modifier = Modifier.padding(bottom = 5.dp),
            placeholder = {Text("Search place...")}
            ){
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomSearchBarPreview(){
    CustomSearchBar(SearchBarState{})
}
