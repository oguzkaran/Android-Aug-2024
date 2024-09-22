package org.csystem.app.demo.company.employee

class SalesManager(var saleExtra: Double) : Manager() {
    //...
    override fun calculateInsurancePayment() = super.calculateInsurancePayment() + saleExtra
}