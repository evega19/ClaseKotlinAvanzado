package com.bedu.clasekotlinavanzado.camara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import com.bedu.clasekotlinavanzado.R
import com.bedu.clasekotlinavanzado.databinding.ActivityCameraActiveBinding
import com.bedu.clasekotlinavanzado.databinding.ActivityCameraBinding
import java.io.File
import java.util.concurrent.Executors

class ActivityCameraActive : AppCompatActivity() {

    //execute es una interfaz para agregar tareas a un
    private val executor = Executors.newSingleThreadExecutor()

    private lateinit var binding: ActivityCameraActiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraActiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        starCamera()

    }
    private fun starCamera(){
        //Aquí podemos colocar varios factores para personalizar la cámara,
        //activar el focused o modificar la resolución
        val previewCofig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480)) //Usualmente aparece en 1080p
        }.build()
        val preview = Preview(previewCofig)
        //Aquí tenemos nuestro preview pero falta que se visualice
        //vamos a hacer un update listener para constantemente estar refresacando nuestra vista o textura

        preview.setOnPreviewOutputUpdateListener {
            //Primero obtenemos nuestro parent (Layout principal)
            val parent = binding.parent as ViewGroup

            parent.removeView(binding.cameraPreview)
            parent.addView(binding.cameraPreview,0)
            //Vamos a colocar la nueva textura que hay que renderizar
            binding.cameraPreview.setSurfaceTexture(it.surfaceTexture)
        }

        //Tomaremos la fotografía

        val imageCapture = captureConfig()
        //Atará el ciclo de vida de la cámara con la de la cámara, ya no tengo que pausar la cámara o demás
        CameraX.bindToLifecycle(this, preview,imageCapture)
    }

    private fun captureConfig(): ImageCapture {
        //Gestiomamos la captura de la imagen
        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()

        //Creamos el objeto de la captura
        val imageCapture = ImageCapture(imageCaptureConfig)

        //Colocamos el listener
        binding.captureButton.setOnClickListener {
            //Creamos el directorio del archivo
            var file = File(externalMediaDirs.first(),"${System.currentTimeMillis()}.jpg")
            //Colocamos un listener para saber que hacer si la imagen se guardó o si hubo un error
            imageCapture.takePicture(file, executor,object: ImageCapture.OnImageSavedListener{
                override fun onImageSaved(file: File) {
                    //Hacemos un post para el UI que estamos
                    binding.cameraPreview.post {
                        Toast.makeText(applicationContext, "Imagen guardada", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
                    binding.cameraPreview.post {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                    }
                }

            })

        }
        return imageCapture
    }

}