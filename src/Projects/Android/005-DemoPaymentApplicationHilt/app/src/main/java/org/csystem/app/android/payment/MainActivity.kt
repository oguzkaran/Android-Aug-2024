package org.csystem.app.android.payment

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.android.library.util.datetime.module.annotation.CurrentLocalDateTimeInterceptor
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import org.csystem.app.android.payment.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinging: ActivityMainBinding

    @Inject
    @CurrentLocalDateTimeInterceptor
    lateinit var dateTime: LocalDateTime

    @Inject
    @DateTimeFormatterTRInterceptor
    lateinit var dateTimeFormatter: DateTimeFormatter

    private fun initBinding() {
        mBinging = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinging.activity = this
        mBinging.dateTime = dateTimeFormatter.format(dateTime)
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

    fun onLoginButtonClicked() {
        Intent(this, LoginActivity::class.java).apply { startActivity(this) }
    }
}