package ai.synheart.behavior.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ai.synheart.behavior.BehaviorEvent
import ai.synheart.behavior.BehaviorSession
import ai.synheart.behavior.BehaviorSessionSummary
import ai.synheart.behavior.BehaviorStats
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val sdkInitialized: Boolean = false,
    val sessionActive: Boolean = false,
    val currentSessionId: String? = null,
    val notificationPermission: Boolean = false,
    val callPermission: Boolean = false,
    // Stats
    val scrollVelocity: String = "N/A",
    val appSwitchesPerMin: String = "0",
    val stabilityIndex: String = "N/A",
    val fragmentationIndex: String = "N/A",
    // Events
    val recentEvents: List<BehaviorEvent> = emptyList(),
    // Session results
    val lastSessionSummary: BehaviorSessionSummary? = null,
    val sessionEvents: List<BehaviorEvent> = emptyList(),
    val statusMessage: String = "",
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sdk = ExampleApplication.sdk

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var currentSession: BehaviorSession? = null
    private var eventCollectionJob: Job? = null
    private val collectedEvents = mutableListOf<BehaviorEvent>()

    init {
        _uiState.update {
            it.copy(
                sdkInitialized = sdk.isInitialized,
                notificationPermission = sdk.checkNotificationPermission(),
                callPermission = sdk.checkCallPermission(),
                statusMessage = if (sdk.isInitialized) "SDK initialized" else "SDK not initialized",
            )
        }
        startEventCollection()
    }

    override fun onCleared() {
        super.onCleared()
        eventCollectionJob?.cancel()
    }

    private fun startEventCollection() {
        eventCollectionJob = viewModelScope.launch {
            sdk.onEvent.collect { event ->
                collectedEvents.add(event)
                _uiState.update {
                    val recent = (listOf(event) + it.recentEvents).take(20)
                    it.copy(recentEvents = recent)
                }
            }
        }
    }

    fun startSession() {
        viewModelScope.launch {
            try {
                collectedEvents.clear()
                val session = sdk.startSession()
                currentSession = session
                _uiState.update {
                    it.copy(
                        sessionActive = true,
                        currentSessionId = session.sessionId,
                        lastSessionSummary = null,
                        sessionEvents = emptyList(),
                        statusMessage = "Session started: ${session.sessionId}",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(statusMessage = "Start session failed: ${e.message}")
                }
            }
        }
    }

    fun endSession() {
        viewModelScope.launch {
            try {
                val sessionId = currentSession?.sessionId ?: return@launch
                val summary = sdk.endSession(sessionId)
                currentSession = null
                _uiState.update {
                    it.copy(
                        sessionActive = false,
                        currentSessionId = null,
                        lastSessionSummary = summary,
                        sessionEvents = collectedEvents.toList(),
                        statusMessage = "Session ended — ${summary.activitySummary.totalEvents} events",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(statusMessage = "End session failed: ${e.message}")
                }
            }
        }
    }

    fun refreshStats() {
        viewModelScope.launch {
            try {
                val stats: BehaviorStats = sdk.getCurrentStats()
                _uiState.update {
                    it.copy(
                        scrollVelocity = stats.scrollVelocity?.let { v ->
                            "%.1f".format(v)
                        } ?: "N/A",
                        appSwitchesPerMin = stats.appSwitchesPerMinute.toString(),
                        stabilityIndex = stats.stabilityIndex?.let { v ->
                            "%.2f".format(v)
                        } ?: "N/A",
                        fragmentationIndex = stats.fragmentationIndex?.let { v ->
                            "%.2f".format(v)
                        } ?: "N/A",
                        notificationPermission = sdk.checkNotificationPermission(),
                        callPermission = sdk.checkCallPermission(),
                        statusMessage = "Stats refreshed",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(statusMessage = "Refresh stats failed: ${e.message}")
                }
            }
        }
    }

    fun requestNotificationPermission() {
        sdk.requestNotificationListenerAccess()
        _uiState.update {
            it.copy(statusMessage = "Notification listener settings opened")
        }
    }

    fun requestCallPermission() {
        _uiState.update {
            it.copy(statusMessage = "Call permission requires READ_PHONE_STATE — check runtime permissions")
        }
    }
}
