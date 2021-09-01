package com.bedu.clasekotlinavanzado

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//TODO: https://github.com/beduExpert/Kotlin-Avanzado-Santander-2021/blob/main/Sesion-03/Readme.md

class ActivityLocation : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Objeto que obtiene la localización
    private lateinit var mFocusedLocationClient: FusedLocationProviderClient


    //Es como buena práctica, ya que si quieres acceder desde otr activity es MainActivity.PERMITION_ID
    companion object{
        const val PERMISSION_ID = 33
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Crea un cliente que hace peticiones al servidor, este se conecta con el GPS
        //Instanciamos el cliente
        mFocusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnLocate.setOnClickListener {
            getLocation()
        }


    }

    @SuppressLint("MissingPermission")
    private fun location(){
        //Gestionamos cuando está prendido el gps y pedimos la localización
        mFocusedLocationClient.lastLocation.addOnSuccessListener(this){
            if (it != null){
                binding.tvLatitude.text = it.latitude.toString()
                binding.tvLongitude.text = it.longitude.toString()
            }else{
                Toast.makeText(this, "Son nulas las coordenadas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation(){
        if(checkPermission()){
            if(isLocationEnable()){
                location()
            }else{
                goToTurnLocation()
                location()
            }
        }else{
            requestPermision()
        }
    }

    private fun isLocationEnable():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //Vamos a regresar el valor si nuestra localización esta encendida o no, o si está la localización por IP
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }



    private fun checkPermission():Boolean{
        //Verificamos los permisos
        if ( checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) ){
            return true
        }
        return false
    }

    private fun checkGranted(permission:String):Boolean{
        //Con esto verificamos los permiso si está activdo
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    //TODO: Esto es en caso de no terner permisos

    /*
    * Puede notar que al solicitar permiso y después del resultado del permiso, utilizamos PERMISSION_ID,
    * es un valor intermedio que nos ayuda a identificar la acción del usuario con la que solicitamos permiso.
    * Puede proporcionar cualquier valor único aquí.
    * */
    private fun requestPermision(){
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    //Al aceptar los permisos en automático va a obtener la localización
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
        /*when(requestCode){
            PERMISSION_ID -> location()
        }*/
    }

    //Abre una ventana para poder activar la localizaci[on
    private fun goToTurnLocation(){
        Toast.makeText(this, "Debes prender el servicio de GPS", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }
}