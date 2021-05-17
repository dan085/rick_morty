package com.betterfly.test.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.betterfly.test.R
import com.betterfly.test.ui.initial.MainActivityInitial
import com.google.firebase.messaging.FirebaseMessaging
///Clase que permite crear un SplashScreen para mostrar una imagen cuando se ingresa a la App
class MainActivitySplashScreen : AppCompatActivity() {
    // Duración
    private  val SPLASH_DISPLAY_LENGTH = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ////Obtener Token para generar los push no notificaciones, este se tiene que guardar en una DB
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newToken  = task.result
                    Log.e("Token para push notificaciones", newToken)
                }
            }

        setContentView(R.layout.activity_main_splash_screen)
            Handler(Looper.getMainLooper()).postDelayed({
                val mainIntent = Intent(
                    this@MainActivitySplashScreen,
                    MainActivityInitial::class.java
                )
                this@MainActivitySplashScreen.startActivity(mainIntent)
                this@MainActivitySplashScreen.finish()
            }, this.SPLASH_DISPLAY_LENGTH.toLong())

    }
}