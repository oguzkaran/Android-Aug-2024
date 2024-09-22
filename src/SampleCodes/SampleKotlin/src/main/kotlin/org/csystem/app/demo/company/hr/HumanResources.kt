package org.csystem.app.demo.company.hr

import org.csystem.app.demo.company.employee.Employee

class HumanResources {
    //...
    fun payInsurance(employee: Employee) {
        println("Citizen Id:${employee.citizenId}")
        println("Citizen Id:${employee.name}")
        println("Citizen Id:${employee.address}")
        println("Insurance Payment:${employee.calculateInsurancePayment()}")
        //...
    }
}