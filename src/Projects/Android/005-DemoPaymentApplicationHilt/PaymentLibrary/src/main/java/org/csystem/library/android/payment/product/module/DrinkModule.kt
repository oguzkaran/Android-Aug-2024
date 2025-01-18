package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.BaseProduct
import org.csystem.library.android.payment.product.Drink
import org.csystem.library.android.payment.product.constant.name.DRINK
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DrinkModule {
    @Binds
    @Named(DRINK)
    abstract fun bindDrink(drink: Drink): BaseProduct
}
