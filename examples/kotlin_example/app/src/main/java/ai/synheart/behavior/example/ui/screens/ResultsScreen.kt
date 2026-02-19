package ai.synheart.behavior.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ai.synheart.behavior.example.UiState
import ai.synheart.behavior.example.ui.components.EventCard
import ai.synheart.behavior.example.ui.theme.*

@Composable
fun ResultsScreen(
    uiState: UiState,
) {
    if (uiState.lastSessionSummary == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "End a session to see results",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        }
        return
    }

    val summary = uiState.lastSessionSummary

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Session Info
        item {
            SectionCard(title = "Session Info") {
                MetricRow("Session ID", summary.sessionId)
                MetricRow("Start", summary.startAt)
                MetricRow("End", summary.endAt)
                MetricRow("Duration", "${summary.durationMs}ms")
                MetricRow("Micro Session", summary.microSession.toString())
                MetricRow("OS", summary.os)
                summary.appId?.let { MetricRow("App ID", it) }
                summary.appName?.let { MetricRow("App Name", it) }
                MetricRow("Session Spacing", "${summary.sessionSpacing}ms")
            }
        }

        // Motion State
        item {
            summary.motionState?.let { motion ->
                Spacer(modifier = Modifier.height(12.dp))
                SectionCard(title = "Motion State") {
                    MetricRow("Major State", motion.majorState)
                    MetricRow("Confidence", "%.1f%%".format(motion.majorStatePct * 100))
                    MetricRow("ML Model", motion.mlModel)
                    MetricRow("States", motion.state.joinToString(", "))
                }
            }
        }

        // Behavior Metrics
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionCard(title = "Behavior Metrics") {
                val m = summary.behavioralMetrics
                MetricRow("Interaction Intensity", "%.3f".format(m.interactionIntensity))
                MetricRow("Task Switch Rate", "%.3f".format(m.taskSwitchRate))
                MetricRow("Task Switch Cost", "${m.taskSwitchCost}ms")
                MetricRow("Idle Time Ratio", "%.3f".format(m.idleTimeRatio))
                MetricRow("Active Time Ratio", "%.3f".format(m.activeTimeRatio))
                MetricRow("Notification Load", "%.3f".format(m.notificationLoad))
                MetricRow("Burstiness", "%.3f".format(m.burstiness))
                MetricRow("Distraction Score", "%.3f".format(m.behavioralDistractionScore))
                MetricRow("Focus Hint", "%.3f".format(m.focusHint))
                MetricRow("Fragmented Idle Ratio", "%.3f".format(m.fragmentedIdleRatio))
                MetricRow("Scroll Jitter Rate", "%.3f".format(m.scrollJitterRate))
                MetricRow("Deep Focus Blocks", "${m.deepFocusBlocks.size}")
            }
        }

        // Typing Summary
        item {
            summary.typingSessionSummary?.let { typing ->
                Spacer(modifier = Modifier.height(12.dp))
                SectionCard(title = "Typing Summary") {
                    MetricRow("Session Count", "${typing.typingSessionCount}")
                    MetricRow("Avg Keystrokes/Session", "%.1f".format(typing.averageKeystrokesPerSession))
                    MetricRow("Avg Duration", "%.1f".format(typing.averageTypingSessionDuration))
                    MetricRow("Avg Speed", "%.1f".format(typing.averageTypingSpeed))
                    MetricRow("Cadence Stability", "%.3f".format(typing.typingCadenceStability))
                    MetricRow("Burstiness", "%.3f".format(typing.burstinessOfTyping))
                    MetricRow("Total Duration", "${typing.totalTypingDuration}ms")
                    MetricRow("Active Ratio", "%.3f".format(typing.activeTypingRatio))
                    MetricRow("Deep Typing Blocks", "${typing.deepTypingBlocks}")
                    MetricRow("Fragmentation", "%.3f".format(typing.typingFragmentation))
                    MetricRow("Correction Rate", "%.3f".format(typing.correctionRate))
                }
            }
        }

        // Notification Summary
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionCard(title = "Notification Summary") {
                val n = summary.notificationSummary
                MetricRow("Count", "${n.notificationCount}")
                MetricRow("Ignored", "${n.notificationIgnored}")
                MetricRow("Ignore Rate", "%.1f%%".format(n.notificationIgnoreRate * 100))
                MetricRow("Clustering Index", "%.3f".format(n.notificationClusteringIndex))
                MetricRow("Call Count", "${n.callCount}")
                MetricRow("Call Ignored", "${n.callIgnored}")
            }
        }

        // System State
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionCard(title = "System State") {
                val s = summary.systemState
                MetricRow("Internet", if (s.internetState) "Connected" else "Disconnected")
                MetricRow("Do Not Disturb", if (s.doNotDisturb) "On" else "Off")
                MetricRow("Charging", if (s.charging) "Yes" else "No")
            }
        }

        // Device Context
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionCard(title = "Device Context") {
                val d = summary.deviceContext
                MetricRow("Avg Brightness", "%.1f%%".format(d.avgScreenBrightness * 100))
                MetricRow("Start Orientation", d.startOrientation)
                MetricRow("Orientation Changes", "${d.orientationChanges}")
            }
        }

        // Activity Summary
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionCard(title = "Activity Summary") {
                val a = summary.activitySummary
                MetricRow("Total Events", "${a.totalEvents}")
                MetricRow("App Switch Count", "${a.appSwitchCount}")
            }
        }

        // Events Timeline
        if (uiState.sessionEvents.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Events Timeline (${uiState.sessionEvents.size})",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(uiState.sessionEvents) { event ->
                EventCard(event = event, modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SynheartIndigo.copy(alpha = 0.05f),
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = SynheartIndigo,
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            content()
        }
    }
}

@Composable
private fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
        )
    }
}
