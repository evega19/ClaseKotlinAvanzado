package com.bedu.clasekotlinavanzado.camara

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bedu.clasekotlinavanzado.databinding.ActivityCameraBinding

class ActivityCamera : AppCompatActivity() {

    companion object{
        val PERMISSION_ID =34
    }

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenCamera.setOnClickListener {
            if(checkCameraPermission()){
                //Si tienes permisos abre camara
                openCamera()
            }else{
                //Si no, solicitalos
                requestPermission()
            }
        }
    }

    private fun openCamera(){
        val intent = Intent(this, ActivityCameraActive::class.java)
        startActivity(intent)
    }

    private fun checkCameraPermission():Boolean{
        //Verificar si tenemos permisos
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        //Recuperar el permiso para la cámara
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_ID)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_ID){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openCamera()
            }
        }

    }

}