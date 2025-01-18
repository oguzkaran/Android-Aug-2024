package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.IProductPayment
import org.csystem.library.android.payment.product.Menu
import org.csystem.library.android.payment.product.constant.name.MENU_PAYMENT
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class MenuPaymentModule {
    @Binds
    @Named(MENU_PAYMENT)
    abstract fun bindMenuPayment(menu: Menu): IProductPayment
}
