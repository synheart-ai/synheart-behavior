package ai.synheart.behavior.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ai.synheart.behavior.example.UiState
import ai.synheart.behavior.example.ui.components.EventCard
import ai.synheart.behavior.example.ui.theme.*

@Composable
fun InteractionScreen(
    uiState: UiState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Typing test card
        item {
            Text(
                text = "Typing Test",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = SynheartPurple.copy(alpha = 0.05f),
                ),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Type below to generate typing events:",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    var typingText by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = typingText,
                        onValueChange = { typingText = it },
                        placeholder = { Text("Start typing here...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Test items (scrollable list for generating scroll/tap events)
        item {
            Text(
                text = "Test Items (Scroll & Tap)",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        itemsIndexed((1..20).toList()) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { /* tap generates event via SDK view attachment */ },
                colors = CardDefaults.cardColors(
                    containerColor = if (index % 2 == 0) {
                        SynheartBlue.copy(alpha = 0.05f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Test Item #$item",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "Tap or scroll through this list to generate behavioral events",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }
            }
        }

        // Live event feed
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Live Event Feed",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.recentEvents.isEmpty()) {
                Text(
                    text = "No events yet — interact with the app to generate events",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }
        }

        items(uiState.recentEvents) { event ->
            EventCard(event = event, modifier = Modifier.padding(vertical = 2.dp))
        }
    }
}
