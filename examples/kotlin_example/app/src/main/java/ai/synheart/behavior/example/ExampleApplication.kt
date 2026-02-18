package ai.synheart.behavior.example

import android.app.Application
import ai.synheart.behavior.SynheartBehavior
import ai.synheart.behavior.BehaviorConfig

class ExampleApplication : Application() {

    companion object {
        lateinit var sdk: SynheartBehavior
            private set
    }

    override fun onCreate() {
        super.onCreate()
        sdk = SynheartBehavior.create(
            context = this,
            config = BehaviorConfig(
                enableInputSignals = true,
                enableAttentionSignals = true,
                enableMotionLite = false,
            ),
        )
        sdk.initialize()
    }
}
