package org.csystem.library.android.payment.product

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import org.csystem.kotlin.util.string.randomTextEN
import javax.inject.Inject
import kotlin.random.Random


@ActivityScoped
class Drink @Inject constructor(@ApplicationContext var context: Context): BaseProduct(Random.randomTextEN(4), Random.nextDouble(1.0, 100.0)) {
    override fun calculatePayment(amount: Double) : Double {
        Toast.makeText(context, "Drink created -> Name:$name, Unit Price:$unitPrice, Amount:$amount", Toast.LENGTH_SHORT).show()
        return amount * unitPrice * 1.9
    }
}
