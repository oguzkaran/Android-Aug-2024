package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.BaseProduct
import org.csystem.library.android.payment.product.Food
import org.csystem.library.android.payment.product.constant.name.FOOD
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class FoodModule {
    @Binds
    @Named(FOOD)
    abstract fun bindFood(food: Food): BaseProduct
}
