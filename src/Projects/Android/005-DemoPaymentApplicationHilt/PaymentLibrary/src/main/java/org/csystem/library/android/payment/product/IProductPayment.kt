package org.csystem.library.android.payment.product

interface IProductPayment {
    fun calculatePayment(amount: Double): Double
}