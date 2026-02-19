package ai.synheart.behavior.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ai.synheart.behavior.BehaviorEvent
import ai.synheart.behavior.BehaviorEventType
import ai.synheart.behavior.example.ui.theme.*

@Composable
fun EventCard(
    event: BehaviorEvent,
    modifier: Modifier = Modifier,
) {
    val badgeColor = when (event.eventType) {
        BehaviorEventType.SCROLL -> SynheartBlue
        BehaviorEventType.TAP -> SynheartGreen
        BehaviorEventType.SWIPE -> SynheartTeal
        BehaviorEventType.TYPING -> SynheartPurple
        BehaviorEventType.NOTIFICATION -> SynheartOrange
        BehaviorEventType.CALL -> SynheartRed
        BehaviorEventType.APP_SWITCH -> SynheartIndigo
        BehaviorEventType.CLIPBOARD -> SynheartGrey
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Type badge
            Text(
                text = event.eventType.name,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                modifier = Modifier
                    .background(badgeColor, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
                // Show key metric from event
                val metricSummary = event.metrics.entries
                    .take(2)
                    .joinToString(" | ") { (k, v) ->
                        val display = when (v) {
                            is Double -> "%.1f".format(v)
                            is Float -> "%.1f".format(v)
                            else -> v.toString()
                        }
                        "$k: $display"
                    }
                if (metricSummary.isNotEmpty()) {
                    Text(
                        text = metricSummary,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                        ),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
