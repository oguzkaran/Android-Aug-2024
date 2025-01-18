package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.Desert
import org.csystem.library.android.payment.product.IProductPayment
import org.csystem.library.android.payment.product.constant.name.DESERT_PAYMENT
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DesertPaymentModule {
    @Binds
    @Named(DESERT_PAYMENT)
    abstract fun bindDesertPayment(deert: Desert): IProductPayment
}
