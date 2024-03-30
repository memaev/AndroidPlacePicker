package dem.llc.placepicker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dem.llc.placepicker.R
import dem.llc.placepicker.ui.state.SearchBarState
import dem.llc.placepicker.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
){
    val focusManager = LocalFocusManager.current
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = modifier
            .background(color = Color.White,shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp,vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.search_logo),
            contentDescription = "Search logo",
        )

        TextField(
            modifier = Modifier
                .height(60.dp)
                .padding(vertical = 5.dp)
            ,
            value = searchQuery,
            onValueChange = { searchQuery = it },
            textStyle = TextStyle(fontSize = 14.sp),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Search place...", fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchQuery)
                focusManager.clearFocus()
            }),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Gray,
                focusedContainerColor = Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CustomSearchBarPreview(){
    MapSearchBar(onSearch = {})
}
