package com.betterfly.test.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.messaging.RemoteMessage

class FirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
         Log.d("FirebaseMessagingg", "+++++")
          Log.i("PUSHH", "Received message " + remoteMessage.data)
    }

   override fun onNewToken(refreshedToken: String) {
        Log.d("TAG", "Refreshed token: " + refreshedToken);

        Handler(Looper.getMainLooper()).post { registerToken(refreshedToken) }
    }

    private fun registerToken(fcm: String) {


    }
}
