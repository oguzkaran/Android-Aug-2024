package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.BaseProduct
import org.csystem.library.android.payment.product.Desert
import org.csystem.library.android.payment.product.constant.name.DESERT
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DesertModule {
    @Binds
    @Named(DESERT)
    abstract fun bindDesert(desert: Desert): BaseProduct
}
