package dev.olog.feature.app.shortcuts

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.olog.core.Classes

class ShortcutsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        handleIntent(intent!!)
        finish()
    }

    private fun handleIntent(intent: Intent) {
        val action = intent.action ?: return

        // forwards action to music service
        val serviceIntent = Intent(this, Class.forName(dev.olog.core.Classes.SERVICE_MUSIC))
        serviceIntent.action = action
        ContextCompat.startForegroundService(this, serviceIntent)
    }

}