import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'shared_pref_android_platform_interface.dart';

/// An implementation of [SharedPrefAndroidPlatform] that uses method channels.
class MethodChannelSharedPrefAndroid extends SharedPrefAndroidPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('shared_pref_android');

  @override
  Future<String> getString(String key) async {
    final args = {'key': key};
    final result =
        await methodChannel.invokeMethod<String>('getString', args) ?? "";
    return result;
  }

  @override
  Future setString(String key, String value) async {
    final args = {
      'key': key,
      'value': value,
    };
    await methodChannel.invokeMethod<String>('setString', args);
  }
}
