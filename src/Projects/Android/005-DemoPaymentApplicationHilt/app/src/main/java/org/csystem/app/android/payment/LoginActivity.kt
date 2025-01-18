package org.csystem.app.android.payment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import org.csystem.android.library.util.datetime.module.annotation.CurrentLocalDateInterceptor
import org.csystem.android.library.util.datetime.module.annotation.DateFormatterTRInterceptor
import org.csystem.app.android.payment.databinding.ActivityLoginBinding
import org.csystem.library.android.payment.product.IProductPayment
import org.csystem.library.android.payment.product.constant.name.DESERT_PAYMENT
import org.csystem.library.android.payment.product.constant.name.DRINK_PAYMENT
import org.csystem.library.android.payment.product.constant.name.FOOD_PAYMENT
import org.csystem.library.android.payment.product.constant.name.MENU_PAYMENT
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    @Inject
    @CurrentLocalDateInterceptor
    lateinit var date: LocalDate

    @Inject
    @DateFormatterTRInterceptor
    lateinit var dateFormatter: DateTimeFormatter

    @Inject
    @Named(FOOD_PAYMENT)
    lateinit var paymentFood: IProductPayment

    @Inject
    @Named(DRINK_PAYMENT)
    lateinit var paymentDrink: IProductPayment

    @Inject
    @Named(DESERT_PAYMENT)
    lateinit var paymentDesert: IProductPayment

    @Inject
    @Named(MENU_PAYMENT)
    lateinit var paymentMenu: IProductPayment

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

        Toast.makeText(this, "Food -> ${paymentFood.calculatePayment(200.9)}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Drink -> ${paymentDrink.calculatePayment(1.0)}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Desert -> ${paymentDesert.calculatePayment(200.9)}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Menu -> ${paymentMenu.calculatePayment(2.0)}", Toast.LENGTH_SHORT).show()
    }
}