package thedebug.dev.openpoc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import thedebug.dev.openpoc.presentation.theme.OpenPocTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenPocTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ListScreen(modifier: Modifier = Modifier) {

    val viewModel: MainViewModel = hiltViewModel()
    val viewState = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { viewModel.recalculateOdds() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val listState = rememberLazyListState()
        when (val state = viewState.value) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val bets = state.data
                LazyColumn(state = listState) {
                    items(bets.size) { index ->
                        val bet = bets[index]
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text("Type: ${bet.type}", style = MaterialTheme.typography.bodyLarge)
                            Text("Sell In: ${bet.sellIn}")
                            Text("Odds: ${bet.odds}")
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }

            is UiState.Error -> {
                Text(
                    "Error: ${state.message ?: "Unknown error"}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

    }
}


