package org.csystem.library.android.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.library.android.payment.product.Food
import org.csystem.library.android.payment.product.IProductPayment
import org.csystem.library.android.payment.product.constant.name.FOOD_PAYMENT
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class FoodPaymentModule {
    @Binds
    @Named(FOOD_PAYMENT)
    abstract fun bindFoodPayment(food: Food): IProductPayment
}