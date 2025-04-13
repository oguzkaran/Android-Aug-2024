package org.csystem.app.android.postalcodesearch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.postalcodesearch.api.geonames.dto.PostalCodes
import org.csystem.app.android.postalcodesearch.api.geonames.service.IPostalCodeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var postalCodeService: IPostalCodeService

    private fun postalCodeCallback() : Callback<PostalCodes> {
        return object: Callback<PostalCodes> {
            override fun onResponse(call: Call<PostalCodes?>, response: Response<PostalCodes?>) {
                Log.i("Response:", response.raw().toString())
                if (response.code() != 200) {
                    Log.e("Status", response.code().toString())
                    Toast.makeText(this@MainActivity, "Unsuccessful operation", Toast.LENGTH_LONG).show()
                    return
                }

                response.headers().names().forEach { Log.i("Header", it) }
                if (response.body()?.postalCodes == null) {
                    Toast.makeText(this@MainActivity, "Limit exhausted", Toast.LENGTH_LONG).show()
                    return
                }
                response.body()!!.postalCodes.forEach { Toast.makeText(this@MainActivity, it.placeName,
                    Toast.LENGTH_LONG).show() }
            }

            override fun onFailure(call: Call<PostalCodes?>, e: Throwable) {
                Log.e("onFailure", e.message.toString())
                Toast.makeText(this@MainActivity, "Error occurred while connection", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val call = postalCodeService.findByPostalCode("67000", "csystem", "tr")

        call.enqueue(postalCodeCallback())
    }
}