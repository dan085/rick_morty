package com.betterfly.test.ui.initial

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.betterfly.test.R
import com.betterfly.test.ui.home.MainActivityHome
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivityInitial : AppCompatActivity() {
    val TAG = "MainActivityInitial"
    private  val RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth
    //  private lateinit var dbReference: FirebaseDatabase
    private lateinit var buttonNotLogin :Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var  buttonLoginGoogle :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_initial)
        // Configuración  Google Sign In
        /**  Firebase.auth.signOut() cerrar sesión de la cuenta que inicializo**/

        /**  Inicializar la instancia para la base de datos y guardar los atributos del usuario
            dbReference = FirebaseDatabase.getInstance()
             dbReference.reference.child("User")**/
        auth = Firebase.auth

        buttonLoginGoogle = findViewById(R.id.buttonLoginGoogle)
        buttonNotLogin = findViewById(R.id.buttonNotLogin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        buttonNotLogin = findViewById(R.id.buttonNotLogin)
        buttonNotLogin.setOnClickListener {
            Toast.makeText(it.context, it.resources.getString(R.string.login_without_account), Toast.LENGTH_SHORT).show()
            val mainIntent = Intent( it.context, MainActivityHome::class.java)
            it.context.startActivity(mainIntent)
        }
        buttonLoginGoogle.setOnClickListener {
           signIn()
         }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                Log.d("email", account.email!!)

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            Toast.makeText(this, this.resources.getString(R.string.login_with)+ user.email, Toast.LENGTH_SHORT).show()

            val mainIntent = Intent( this@MainActivityInitial, MainActivityHome::class.java)
            this@MainActivityInitial.startActivity(mainIntent)
            /**
             * Permitia guardar el usuario  los atributos del usuario que se deseen tener
             * dbReference = FirebaseDatabase.getInstance()
            dbReference.reference.child("User")
            auth = Firebase.auth
            val currentUser = auth.currentUser
            auth.createUserWithEmailAndPassword(user?.email,user.uid).addOnCompleteListener(this){
            if(it.isComplete){

            val user: FirebaseUser? = auth.currentUser

            val userDB = dbReference.reference.child(user?.uid.toString())
            userDB.child("email").setValue("email")

            val mainIntent = Intent( this@MainActivityInitial, MainActivity::class.java)
            this@MainActivityInitial.startActivity(mainIntent)

            }else{

            Toast.makeText(this, "Error al enviar", Toast.LENGTH_SHORT).show()
            }


            }**/

            }else{
            Toast.makeText(this, "Inicio sesión con :"+user?.email, Toast.LENGTH_SHORT).show()

            val snackbar = Snackbar.make(this.findViewById(android.R.id.content), this.resources.getString(R.string.issue_login), Snackbar.LENGTH_LONG)
            snackbar.setAction(this.getString(R.string.ok)) { snackbar.dismiss() }
            snackbar.setActionTextColor(getColor(this, R.color.white01))
            val sbView = snackbar.view
            sbView.setBackgroundColor(getColor(this, R.color.black_elegant))
            val textView = sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(getColor(this, R.color.white01))
            snackbar.show()
        }
    }
    ///Permite obtener el color especifico
    fun getColor(ctx: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(ctx, id)
        } else {
            ctx.resources.getColor(id)
        }
    }
    ///Intent para inicializar la cuenta con Google
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInClient.signOut()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

}