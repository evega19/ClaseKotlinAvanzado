package com.bedu.clasekotlinavanzado

import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bedu.clasekotlinavanzado.databinding.ActivityMainCallBinding

class MainActivityCall : AppCompatActivity() {

    private val binding by lazy{ActivityMainCallBinding.inflate(layoutInflater)}

    private val ACTION_CALL = "android.intent.action.PHONE_STATE"

    private val callRecordReciever = CallRecordReciever()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if(hasPermission()){
                record()
            }else{
                askPermission()
            }
        }

    }

    private fun record() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (telephonyManager.callState == TelephonyManager.CALL_STATE_OFFHOOK) {
            IntentFilter().apply {
                addAction(ACTION_CALL)
            }.also { filter -> registerReceiver(callRecordReciever,filter) }
        } else {
            Toast.makeText(this, "No estás en llamada", Toast.LENGTH_SHORT).show()
        }
    }



    private fun hasPermission(): Boolean{
        return ContextCompat.checkSelfPermission(this, READ_PHONE_STATE ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(READ_PHONE_STATE), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Aqui va el código si aceptó el permiso
                record()
            } else {
                Toast.makeText(this, "No puedes acceder sin este permiso", Toast.LENGTH_SHORT).show()
            }
        }

    }

}