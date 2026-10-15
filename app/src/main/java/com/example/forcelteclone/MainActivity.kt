package com.example.forcelteclone

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val navy = Color.rgb(18, 32, 56)
    private val blue = Color.rgb(37, 99, 235)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = navy
        window.navigationBarColor = navy

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(32), dp(24), dp(24))
            setBackgroundColor(Color.rgb(245, 247, 251))
        }
        val title = TextView(this).apply {
            text = "Engineering R.V"
            textSize = 26f
            setTextColor(navy)
            gravity = Gravity.CENTER
        }
        root.addView(title, matchWrap())
        val subtitle = TextView(this).apply {
            text = "Choose a method to open Android's network controls"
            textSize = 14f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(0, dp(10), 0, dp(24))
        }
        root.addView(subtitle, matchWrap())

        addMethod(root, "METHOD1") { launchMethod1() }
        addMethod(root, "METHOD2") { launchMethod2() }
        addMethod(root, "METHOD3") { launchMethod3() }

        val note = TextView(this).apply {
            text = "Note: Android and phone manufacturers restrict direct network-mode changes. These methods open system screens where supported; they cannot guarantee LTE-only mode on every device."
            textSize = 13f
            setTextColor(Color.rgb(90, 98, 110))
            setPadding(0, dp(24), 0, 0)
        }
        root.addView(note, matchWrap())
        setContentView(root)
    }

    private fun addMethod(root: LinearLayout, label: String, action: () -> Unit) {
        val button = Button(this).apply {
            text = label
            textSize = 16f
            isAllCaps = false
            setTextColor(Color.WHITE)
            setBackgroundColor(blue)
            setOnClickListener { action() }
        }
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(56))
        params.bottomMargin = dp(12)
        root.addView(button, params)
    }

    private fun launchMethod1() {
        // Standard Android mobile-network settings; availability varies by OEM.
        tryOpen(Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS))
    }

    private fun launchMethod2() {
        // General wireless settings fallback.
        tryOpen(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    private fun launchMethod3() {
        // Android's phone information screen is hidden/privileged on many builds.
        val candidates = listOf(
            Intent("android.intent.action.MAIN").setClassName("com.android.phone", "com.android.phone.settings.RadioInfo"),
            Intent("android.intent.action.MAIN").setClassName("com.android.phone", "com.android.phone.MobileNetworkSettings"),
            Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
        )
        for (intent in candidates) {
            try {
                startActivity(intent)
                return
            } catch (_: Exception) {
                // Try the next system-supported route.
            }
        }
        Toast.makeText(this, "This phone does not expose the advanced network screen to third-party apps.", Toast.LENGTH_LONG).show()
    }

    private fun tryOpen(intent: Intent) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, "This settings screen is not available on this phone.", Toast.LENGTH_LONG).show()
        } catch (_: SecurityException) {
            Toast.makeText(this, "Android blocked access to this settings screen.", Toast.LENGTH_LONG).show()
        }
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
    private fun matchWrap() = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}
