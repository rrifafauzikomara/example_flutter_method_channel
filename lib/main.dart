import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class PlatformChannel extends StatefulWidget {
  const PlatformChannel({Key? key}) : super(key: key);

  @override
  State<PlatformChannel> createState() => _PlatformChannelState();
}

class _PlatformChannelState extends State<PlatformChannel> {
  static const MethodChannel methodChannel =
      MethodChannel('samples.flutter.io/game');
  static const EventChannel eventChannel =
      EventChannel('samples.flutter.io/report');

  String _reportStatus = 'Game status: unknown.';

  Future<void> _startNewActivity() async {
    try {
      eventChannel.receiveBroadcastStream().listen(_onEvent, onError: _onError);
      await methodChannel.invokeMethod('startNewActivity');
    } on PlatformException catch (e) {
      debugPrint("Failed to Invoke: '${e.message}'.");
    }
  }

  void _onEvent(Object? event) {
    setState(() {
      _reportStatus =
          "Game status: $event";
    });
    debugPrint(_reportStatus);
  }

  void _onError(Object error) {
    setState(() {
      _reportStatus = 'Game status: unknown.';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              const Text('Open Native Activity'),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: ElevatedButton(
                  onPressed: _startNewActivity,
                  child: const Text('Start New Activity'),
                ),
              ),
            ],
          ),
          Text(_reportStatus),
        ],
      ),
    );
  }
}

void main() {
  runApp(const MaterialApp(home: PlatformChannel()));
}
