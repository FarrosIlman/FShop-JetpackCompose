package com.parrosz.fshop.ui.screen.home
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parrosz.fshop.di.Injection
import com.parrosz.fshop.model.OrderFShop
import com.parrosz.fshop.ui.ViewModelFactory
import com.parrosz.fshop.ui.common.UiState
import com.parrosz.fshop.ui.components.FShopItem
import com.parrosz.fshop.ui.theme.FShopTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    var searchTerm by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)

    LaunchedEffect(viewModel) {
        viewModel.getAllFShop()
    }

    Surface(
        modifier = modifier
    ) {
        Column {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onSearchTermChange = { term ->
                    searchTerm = term
                }
            )

            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllFShop()
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is UiState.Success -> {
                    val filteredList = filterFShopList((uiState as UiState.Success<List<OrderFShop>>).data, searchTerm)
                    HomeContent(
                        orderFShop = filteredList,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
                is UiState.Error -> {
                    // Handle error state
                    Text("Error loading data")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTermChange: (String) -> Unit
) {
    var searchTerm by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchTerm,
        onValueChange = {
            searchTerm = it
            onSearchTermChange(it)
        },
        placeholder = { Text("Search") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        modifier = modifier
    )
}

@Composable
fun filterFShopList(
    orderFShop: List<OrderFShop>,
    searchTerm: String
): List<OrderFShop> {
    return orderFShop.filter {
        it.fshop.title.contains(searchTerm, ignoreCase = true)
    }
}

@Composable
fun HomeContent(
    orderFShop: List<OrderFShop>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("FShopList")
    ) {
        items(orderFShop) { data ->
            FShopItem(
                image = data.fshop.image,
                title = data.fshop.title,
                harga = data.fshop.harga,
                modifier = Modifier.clickable {
                    navigateToDetail(data.fshop.id)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    FShopTheme {
        HomeScreen(
            modifier = Modifier,
            viewModel = HomeViewModel(Injection.provideRepository())
        ) {}
    }
}

