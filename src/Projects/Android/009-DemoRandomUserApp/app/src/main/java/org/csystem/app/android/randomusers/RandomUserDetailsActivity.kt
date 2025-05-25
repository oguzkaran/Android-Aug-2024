package org.csystem.app.android.randomusers

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import org.csystem.app.android.randomusers.api.me.dto.RandomUser
import org.csystem.app.android.randomusers.api.me.service.IDataService
import org.csystem.app.android.randomusers.databinding.ActivityRandomUserDetailsBinding
import retrofit2.Callback
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import javax.inject.Inject

@AndroidEntryPoint
class RandomUserDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRandomUserDetailsBinding

    @Inject
    lateinit var imageService: IDataService

    private fun saveAndSetImage(imageInputStream: InputStream) {
        try {
            BufferedInputStream(imageInputStream).use {
                var bitmap = BitmapFactory.decodeStream(it)

                mBinding.randomUserDetailsActivityImageViewPicture.setImageBitmap(bitmap)
            }
        }
        catch (ex: IOException) {
            Log.e("io-error", ex.message!!)
            runOnUiThread { finish() }
        }
    }

    private fun downloadImageCallBack(urlStr: String): Callback<ResponseBody> {
        return object : Callback<ResponseBody> {
            override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                Log.i("Response:", response.raw().toString())

                if (response.code() != HttpURLConnection.HTTP_OK || response.body() == null) {
                    Log.e("Status", response.code().toString())
                    Toast.makeText(this@RandomUserDetailsActivity, "Unsuccessful operation while download image", Toast.LENGTH_LONG).show()
                    return
                }
                try {
                    saveAndSetImage(response.body()!!.byteStream())
                }
                catch (ex: Exception) {
                    Log.e("image-error", ex.message ?: "Unknown error for image")
                    finish()
                }
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, ex: Throwable) {
                Log.e("image-download-error", ex.message ?: "Unknown error for image download")
                finish()
            }
        }
    }

    private fun downloadAndSetImage(urlStr: String) {
        imageService.getData(urlStr).enqueue(downloadImageCallBack(urlStr))
    }

    private fun initUI() {
        val randomUser = intent.getSerializableExtra("RANDOM_USER") as RandomUser

        downloadAndSetImage(randomUser.picture.large)
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