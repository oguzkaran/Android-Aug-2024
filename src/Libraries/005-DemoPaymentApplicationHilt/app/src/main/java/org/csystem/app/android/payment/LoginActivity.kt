package org.csystem.app.android.payment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.android.library.util.datetime.module.annotation.CurrentLocalDateInterceptor
import org.csystem.android.library.util.datetime.module.annotation.DateFormatterTRInterceptor
import org.csystem.app.android.payment.application.module.datetime.annotation.DateFormatterInterceptor
import org.csystem.app.android.payment.application.module.datetime.annotation.DateTimeFormatterInterceptor
import org.csystem.app.android.payment.databinding.ActivityLoginBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    @Inject
    @CurrentLocalDateInterceptor
    lateinit var date: LocalDate

    @Inject
    @DateFormatterTRInterceptor
    lateinit var dateFormatter: DateTimeFormatter

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.date = dateFormatter.format(date)
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
}