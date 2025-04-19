package org.csystem.app.android.randomusers

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.randomusers.api.me.dto.RandomUser
import org.csystem.app.android.randomusers.databinding.ActivityRandomUserDetailsBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ExecutorService
import javax.inject.Inject

@AndroidEntryPoint
class RandomUserDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRandomUserDetailsBinding

    @Inject
    lateinit var threadPool: ExecutorService

    private fun loadPictureCallback(urlStr: String) {
        try {
            val url = URL(urlStr)
            val connection = url.openConnection()

            connection.connect()
            var inputStream = connection.inputStream
            var bufferedInputStream = BufferedInputStream(inputStream)
            var bitmap = BitmapFactory.decodeStream(bufferedInputStream)

            runOnUiThread { mBinding.randomUserDetailsActivityImageViewPicture.setImageBitmap(bitmap) }
        } catch (ex: MalformedURLException) {
            Log.e("url-error", ex.message!!)
        }
        catch (ex: IOException) {
            Log.e("io-error", ex.message!!)
            runOnUiThread { finish() }
        }
    }

    private fun setAndDownloadImage(urlStr: String) {
        threadPool.execute { loadPictureCallback(urlStr) }
    }

    private fun initUI() {
        val randomUser = intent.getSerializableExtra("RANDOM_USER") as RandomUser

        setAndDownloadImage(randomUser.picture.large)
    }

    private fun iniBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_random_user_details)
    }

    private fun initialize() {
        enableEdgeToEdge()
        iniBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}