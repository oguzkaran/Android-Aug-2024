package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.Drink
import org.csystem.library.android.payment.product.IProductPayment
import org.csystem.library.android.payment.product.constant.name.DRINK_PAYMENT
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DrinkPaymentModule {
    @Binds
    @Named(DRINK_PAYMENT)
    abstract fun bindDrinkPayment(drink: Drink): IProductPayment
}
