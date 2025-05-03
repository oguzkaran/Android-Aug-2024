package org.csystem.app.android.postalcodesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.postalcodesearch.api.geonames.constant.STATUS_OK
import org.csystem.app.android.postalcodesearch.api.geonames.dto.PostalCode
import org.csystem.app.android.postalcodesearch.api.geonames.dto.PostalCodes
import org.csystem.app.android.postalcodesearch.api.geonames.service.IPostalCodeService
import org.csystem.app.android.postalcodesearch.constant.POSTAL_CODE
import org.csystem.app.android.postalcodesearch.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var postalCodeService: IPostalCodeService

    private fun postalCodeCallback() : Callback<PostalCodes> {
        return object: Callback<PostalCodes> {
            override fun onResponse(call: Call<PostalCodes?>, response: Response<PostalCodes?>) {
                Log.i("Response:", response.raw().toString())
                if (response.code() != STATUS_OK) {
                    Log.e("Status", response.code().toString())
                    Toast.makeText(this@MainActivity, "Unsuccessful operation", Toast.LENGTH_LONG).show()
                    return
                }

                //Log headers
                response.headers().names().forEach { Log.i("Header", it) }

                if (response.body()?.postalCodes == null) {
                    Toast.makeText(this@MainActivity, "Limit exhausted", Toast.LENGTH_LONG).show()
                    return
                }
                mBinding.adapter!!.clear()
                response.body()!!.postalCodes.forEach { mBinding.adapter!!.add(it) }
            }

            override fun onFailure(call: Call<PostalCodes?>, e: Throwable) {
                Log.e("onFailure", e.message.toString())
                Toast.makeText(this@MainActivity, "Error occurred while connection", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.activity = this
        mBinding.postalCode = ""
        mBinding.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<PostalCode>())
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onGetPlacesButtonClicked() {
        var call = postalCodeService.findByPostalCode(mBinding.postalCode!!, "csystem", "tr")

        call.enqueue(postalCodeCallback())
    }

    fun onPlaceClicked(pos: Int) {
        val postalCode = mBinding.adapter!!.getItem(pos)!!

        Intent(this, PlaceActivity::class.java).apply { putExtra(POSTAL_CODE, postalCode); startActivity(this) }
    }
}