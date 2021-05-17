package com.betterfly.test.ui.showItem
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.betterfly.test.ui.model.Item
import com.betterfly.test.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class MainActivityShowItem : AppCompatActivity() {
    lateinit var itemShow: Item

    lateinit var progressBarImage:ProgressBar
    lateinit var name:TextView
    lateinit var avatar:ImageView
    lateinit var imageViewStatus:ImageView

    lateinit var textViewGender:TextView
    lateinit var textViewOrigin:TextView
    lateinit var status:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_show_item)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        progressBarImage = findViewById(R.id.progressBarImage)
        name = findViewById(R.id.name)
        avatar = findViewById(R.id.avatar)
        imageViewStatus = findViewById(R.id.imageViewStatus)
        status = findViewById(R.id.status)


        textViewOrigin = findViewById(R.id.textViewOrigin)
        textViewGender = findViewById(R.id.textViewGender)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        supportActionBar!!.title = this.resources.getString(R.string.information)

        this.itemShow = intent.getSerializableExtra("data") as Item  ///Obtener Objeto de la vistaHome
        println( this.itemShow.image)

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(true)
        progressBarImage.visibility = View.VISIBLE
        Glide.with(this)
            .asBitmap().load(this.itemShow.image)

            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                    progressBarImage.visibility = View.INVISIBLE
                    return false
                }
                override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    progressBarImage.visibility = View.INVISIBLE
                    return false
                }
            })
            .apply(requestOptions)
            .into(avatar)

        name.setText(this.itemShow.name)
        textViewGender.setText(this.itemShow.gender)
        status.setText(this.itemShow.status)
        textViewOrigin.setText(this.itemShow.origin.name)

        ///permite diferenciar los distintos estados de los personajes y  asociarles una imagen
        when (this.itemShow.status) {
            "Alive" -> {print("Alive")
                Glide.with(this)
                    .load(R.drawable.ic_dot_green)
                    .into(imageViewStatus)
            }
            "Dead" -> {
                print("Dead")
                Glide.with(this)
                    .load(R.drawable.ic_dot_red)
                    .into(imageViewStatus);
            }
            else -> {
                print("unknown")
                Glide.with(this)
                    .load(R.drawable.ic_dot_grey)
                    .into(imageViewStatus);
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}