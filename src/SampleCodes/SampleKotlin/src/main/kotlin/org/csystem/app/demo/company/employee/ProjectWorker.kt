package org.csystem.app.demo.company.employee

class ProjectWorker(var extraFee: Double) : Worker() {
    //...
    override fun calculateInsurancePayment() = super.calculateInsurancePayment() + extraFee * 1.5
}