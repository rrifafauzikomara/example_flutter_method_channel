package com.example.example_flutter_method_channel

import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val tag = "MainActivity"
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        val eventChannelHandler = EventChannelHandler(context = applicationContext)

        eventChannelHandler.startListening(flutterEngine, CHARGING_CHANNEL, tag)
        MethodChannel(
            flutterEngine.dartExecutor,
            BATTERY_CHANNEL
        ).setMethodCallHandler { call, result ->
            if ((call.method == "startNewActivity")) {
                goHomeActivity()
            } else {
                result.notImplemented()
            }
        }
    }

    companion object {
        private const val BATTERY_CHANNEL = "samples.flutter.io/game"
        private const val CHARGING_CHANNEL = "samples.flutter.io/report"
    }

    private fun goHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java).apply {
            // put data here
        }
        startActivity(intent)
    }
}