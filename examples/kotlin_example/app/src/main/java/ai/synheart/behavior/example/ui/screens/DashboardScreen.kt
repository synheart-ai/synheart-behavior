package ai.synheart.behavior.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ai.synheart.behavior.example.UiState
import ai.synheart.behavior.example.ui.components.ActionButton
import ai.synheart.behavior.example.ui.components.StatusCard
import ai.synheart.behavior.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(
    uiState: UiState,
    onStartSession: () -> Unit,
    onEndSession: () -> Unit,
    onRefreshStats: () -> Unit,
    onRequestNotificationPermission: () -> Unit,
    onRequestCallPermission: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Status cards in a 2x2 grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatusCard(
                title = "SDK Status",
                value = if (uiState.sdkInitialized) "Ready" else "Not Init",
                icon = Icons.Default.CheckCircle,
                color = if (uiState.sdkInitialized) SynheartGreen else SynheartOrange,
                modifier = Modifier.weight(1f),
            )
            StatusCard(
                title = "Session",
                value = if (uiState.sessionActive) "Active" else "Idle",
                icon = Icons.Default.Sensors,
                color = if (uiState.sessionActive) SynheartGreen else SynheartGrey,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatusCard(
                title = "Notifications",
                value = if (uiState.notificationPermission) "Granted" else "Pending",
                icon = Icons.Default.Notifications,
                color = if (uiState.notificationPermission) SynheartGreen else SynheartRed,
                modifier = Modifier.weight(1f),
            )
            StatusCard(
                title = "Call Access",
                value = if (uiState.callPermission) "Granted" else "Pending",
                icon = Icons.Default.Phone,
                color = if (uiState.callPermission) SynheartGreen else SynheartRed,
                modifier = Modifier.weight(1f),
            )
        }

        // Current stats card
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Current Stats",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = SynheartIndigo.copy(alpha = 0.05f),
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    StatItem(label = "Scroll Velocity", value = uiState.scrollVelocity)
                    StatItem(label = "App Switches/min", value = uiState.appSwitchesPerMin)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    StatItem(label = "Stability Index", value = uiState.stabilityIndex)
                    StatItem(label = "Fragmentation", value = uiState.fragmentationIndex)
                }
            }
        }

        // Action buttons
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Actions",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ActionButton(
                label = "Start Session",
                icon = Icons.Default.PlayArrow,
                color = SynheartGreen,
                onClick = onStartSession,
            )
            ActionButton(
                label = "End Session",
                icon = Icons.Default.Stop,
                color = SynheartRed,
                onClick = onEndSession,
            )
            ActionButton(
                label = "Refresh Stats",
                icon = Icons.Default.Refresh,
                color = SynheartBlue,
                onClick = onRefreshStats,
            )
            ActionButton(
                label = "Notification Permission",
                icon = Icons.Default.Notifications,
                color = SynheartOrange,
                onClick = onRequestNotificationPermission,
            )
            ActionButton(
                label = "Call Permission",
                icon = Icons.Default.Phone,
                color = SynheartIndigo,
                onClick = onRequestCallPermission,
            )
        }

        if (uiState.statusMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.statusMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = SynheartIndigo,
        )
    }
}
