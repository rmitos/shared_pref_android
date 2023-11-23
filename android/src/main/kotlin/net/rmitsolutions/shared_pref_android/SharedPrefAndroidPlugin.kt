package net.rmitsolutions.shared_pref_android


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.AutoCompleteTextView
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** SharedPrefAndroidPlugin */
class SharedPrefAndroidPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private var activity: Activity? = null
    private var sharedPref: SharedPreferences? = null
    private val sharedPrefFileKey = "preference_file_key"

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "shared_pref_android")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getString" -> {
                val key = call.argument<String>("key") ?: ""
                result.success(getString(key))
            }
            "setString" -> {
                val key = call.argument<String>("key") ?: ""
                val value = call.argument<String>("value") ?: ""
                setString(key, value)
                result.success("")
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
        setSharedPrefFile()
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
        setSharedPrefFile()
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    private fun setSharedPrefFile() {
        val fileNameKey = activity!!.applicationContext.resources.getIdentifier(
            sharedPrefFileKey,
            "string",
            activity!!.packageName
        )
        if (fileNameKey == 0) {
            throw Exception("String resource with name 'preference_file_key' not found.")
        }

        val fileName = activity!!.applicationContext.resources.getString(fileNameKey)

        if (sharedPref == null) {
            sharedPref =
                activity?.applicationContext?.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
    }

    private fun getString(key: String): String {
        if (key.isBlank()) return ""
        return sharedPref?.getString(key, "") ?: ""
    }

    private fun setString(key: String, value: String) {
        with(sharedPref!!.edit()) {
            putString(key, value)
            apply()
        }
    }
}
