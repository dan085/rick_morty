package com.betterfly.test.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
///Integración Push notificaciones de Firebase
open class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    override fun onDeletedMessages() {
        super.onDeletedMessages()
         Log.d("onDeletedMessages", "onDeletedMessages")
    }

    override fun onMessageSent(s: String) {
        super.onMessageSent(s)
         Log.d("onMessageSent", s)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "Refreshed token: $token")
        Handler(Looper.getMainLooper()).post { registerToken(token) }
    }

    private fun registerToken(fcm: String) {

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("message_Received","++++++")

    }

}
