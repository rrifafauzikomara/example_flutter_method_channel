package com.example.example_flutter_method_channel

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class EventChannelHandler(val context: Context) {
    fun startListening(flutterEngine: FlutterEngine, channel: String, tag: String) {
        EventChannel(flutterEngine.dartExecutor, channel).setStreamHandler(
            object : EventChannel.StreamHandler {
                val receiver = BroadcastReceiverHandler()
                override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink) {

                    receiver.setListener(object : BroadcastReceiverHandlerListener() {
                        override fun onReportGame(detail: String?) {
                            Log.i(tag, "Success Reported Game: $detail")
                            eventSink.success(detail)
                        }
                    })

                    val filter = IntentFilter("action.REPORT_GAME")
                    context.registerReceiver(receiver, filter)
                }

                override fun onCancel(arguments: Any?) {
                    context.unregisterReceiver(receiver)
                }
            }
        )
    }

    fun onReportGameClicked(context: Context, tag: String) {
        Log.i(tag, "Report Game Clicked")
        Intent().also { intent ->
            intent.action = "action.REPORT_GAME"
            intent.putExtra("reportGame", "Game Reported")
            context.sendBroadcast(intent)
        }
    }
}