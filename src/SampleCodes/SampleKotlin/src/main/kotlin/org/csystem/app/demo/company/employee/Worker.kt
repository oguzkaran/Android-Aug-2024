package org.csystem.app.demo.company.employee

open class Worker(var feePerHour: Double = 0.0, var hourPerDay: Int = 0) : Employee() {
    //...
    override fun calculateInsurancePayment() = feePerHour * hourPerDay.toDouble() * 1.5
}