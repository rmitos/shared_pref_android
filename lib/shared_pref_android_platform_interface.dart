import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'shared_pref_android_method_channel.dart';

abstract class SharedPrefAndroidPlatform extends PlatformInterface {
  /// Constructs a SharedPrefAndroidPlatform.
  SharedPrefAndroidPlatform() : super(token: _token);

  static final Object _token = Object();

  static SharedPrefAndroidPlatform _instance = MethodChannelSharedPrefAndroid();

  /// The default instance of [SharedPrefAndroidPlatform] to use.
  ///
  /// Defaults to [MethodChannelSharedPrefAndroid].
  static SharedPrefAndroidPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SharedPrefAndroidPlatform] when
  /// they register themselves.
  static set instance(SharedPrefAndroidPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String> getString(String key) {
    throw UnimplementedError('getString(key: String) has not been implemented.');
  }

  Future setString(String key, String value) {
    throw UnimplementedError('setString(key: String, value: String) has not been implemented.');
  }
}
