package ai.synheart.behavior.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import ai.synheart.behavior.example.ui.screens.DashboardScreen
import ai.synheart.behavior.example.ui.screens.InteractionScreen
import ai.synheart.behavior.example.ui.screens.ResultsScreen
import ai.synheart.behavior.example.ui.theme.SynheartBehaviorExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SynheartBehaviorExampleTheme {
                SynheartBehaviorApp()
            }
        }
    }
}

private data class TabItem(
    val title: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynheartBehaviorApp(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        TabItem("Dashboard", Icons.Default.Dashboard),
        TabItem("Interaction", Icons.Default.TouchApp),
        TabItem("Results", Icons.Default.List),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Synheart Behavior") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(tab.title) },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                    )
                }
            }

            when (selectedTab) {
                0 -> DashboardScreen(
                    uiState = uiState,
                    onStartSession = viewModel::startSession,
                    onEndSession = viewModel::endSession,
                    onRefreshStats = viewModel::refreshStats,
                    onRequestNotificationPermission = viewModel::requestNotificationPermission,
                    onRequestCallPermission = viewModel::requestCallPermission,
                )
                1 -> InteractionScreen(
                    uiState = uiState,
                )
                2 -> ResultsScreen(
                    uiState = uiState,
                )
            }
        }
    }
}
