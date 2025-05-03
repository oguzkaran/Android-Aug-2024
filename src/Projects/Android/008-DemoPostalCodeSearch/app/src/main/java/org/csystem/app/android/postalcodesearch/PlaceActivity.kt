package org.csystem.app.android.postalcodesearch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import org.csystem.android.util.map.MapUtil
import org.csystem.app.android.postalcodesearch.api.geonames.dto.PostalCode
import org.csystem.app.android.postalcodesearch.constant.POSTAL_CODE
import org.csystem.app.android.postalcodesearch.databinding.ActivityPlaceBinding

class PlaceActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPlaceBinding

    private fun initView() {
        val postalCode = if (android.os.Build.VERSION.SDK_INT >= 33)  intent.getSerializableExtra(POSTAL_CODE, PostalCode::class.java) else intent.getSerializableExtra(POSTAL_CODE) as PostalCode
        mBinding.placeName = postalCode?.placeName
        mBinding.location = "${postalCode?.lat}, ${postalCode?.lng}"

        //It can be used from intent later. This is used for demonstration purposes.
        mBinding.placeActivityTextViewLocation.setTag(R.id.latitude, postalCode?.lat)
        mBinding.placeActivityTextViewLocation.setTag(R.id.longitude, postalCode?.lng)
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place)
        mBinding.activity = this
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onLocationClicked() {
        val latitude = mBinding.placeActivityTextViewLocation.getTag(R.id.latitude) as Double
        val longitude = mBinding.placeActivityTextViewLocation.getTag(R.id.longitude) as Double

        MapUtil.chooseAndShowOnMap(this, latitude, longitude, R.string.map_chooser_title)
    }
}