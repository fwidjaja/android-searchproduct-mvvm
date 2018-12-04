package com.example.nakama.searchproduct

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import java.security.MessageDigest
import android.provider.SyncStateContract.Helpers.update
import java.math.BigInteger


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val deviceID = Settings.Secure.getString(this@MainActivity.contentResolver, Settings.Secure.ANDROID_ID)
        val md5 = getStringInMd5(deviceID)
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE)
        Log.d("TAG", "deviceID = $deviceID")
        Log.d("TAG", "md5 = $md5")
        Log.d("TAG", "tm.deviceID = $tm.getDeviceId")
    }

    fun getStringInMd5(str: String) : String {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(str.toByteArray(), 0, str.length)
        val i = BigInteger(1, md5.digest())
        return String.format("%1$032x", i)
    }
}
