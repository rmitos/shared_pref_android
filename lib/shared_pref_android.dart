import 'shared_pref_android_platform_interface.dart';

class SharedPrefAndroid {
  Future<String> getString(String key) {
    return SharedPrefAndroidPlatform.instance.getString(key);
  }

  Future setString(String key, String value) {
    return SharedPrefAndroidPlatform.instance.setString(key, value);
  }
}
