package org.csystem.app.android.app.counter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.app.android.app.counter.data.service.CounterDataService
import org.csystem.app.android.app.counter.databinding.ActivityLimitConfigurationBinding
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class LimitConfigurationActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLimitConfigurationBinding

    @Inject
    @Named("threadPool")
    lateinit var threadPool: ExecutorService

    @Inject
    lateinit var counterDataService: CounterDataService

    private fun saveCallback(limit: Int) {
        try {
            counterDataService.setLimit(limit)
            finish()
        } catch (_: NumberFormatException) {
            runOnUiThread { Toast.makeText(this, R.string.message_invalid_value, Toast.LENGTH_LONG).show() }
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_limit_configuration)
        mBinding.limitValue = "0"
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun onSaveButtonClicked() = threadPool.execute { saveCallback(mBinding.limitValue!!.toInt()) }

    fun onNoLimitButtonClicked() = threadPool.execute { saveCallback(-1) }

    fun onCloseButtonClicked() = finish()
}