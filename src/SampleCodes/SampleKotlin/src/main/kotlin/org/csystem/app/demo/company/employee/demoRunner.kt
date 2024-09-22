package org.csystem.app.demo.company.employee

import org.csystem.app.demo.company.hr.HumanResources
import kotlin.random.Random

private fun getManager(): Manager {
    val manager = Manager(300000.0, "Yazılım")

    manager.citizenId = "12345678";
    manager.name = "Ali Veli";
    manager.address = "Mecidiyeköy";

    return manager
}

private fun getWorker(): Worker {
    val worker = Worker(1000.0, 8)

    worker.citizenId = "12345679";
    worker.name = "Selami Secati";
    worker.address = "Şişli";

    return worker
}

private fun getSalesManager(): SalesManager {
    val salesManager = SalesManager(40000.0)

    salesManager.citizenId = "12345679";
    salesManager.name = "Mustafa Mehmet";
    salesManager.address = "Fatih";
    salesManager.salary = 3000000.0
    salesManager.department = "Pazarlama"

    return salesManager
}


private fun getProjectWorker(): Worker {
    val worker = ProjectWorker(30000.0)

    worker.citizenId = "12345677";
    worker.name = "Ayşe Fatma";
    worker.address = "Beylikdüzü";
    worker.feePerHour = 2000.0
    worker.hourPerDay = 6

    return worker
}

private fun getEmployee(): Employee {
    return when (Random.nextInt(4)) {
        0 -> getManager()
        1 -> getWorker()
        2 -> getProjectWorker()
        else -> getSalesManager()
    }
}

fun runDemoCompanyApp() {
    val hr = HumanResources()

    while (true) {
        println("----------------------------------------------------")
        val employee = getEmployee()

        hr.payInsurance(employee)
        Thread.sleep(1000)
        println("----------------------------------------------------")
    }
}
