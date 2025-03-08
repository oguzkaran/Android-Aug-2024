package org.csystem.app.android.payment.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.csystem.app.android.payment.repository.dal.PaymentLocalRepositoryHelper
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import org.csystem.app.android.payment.repository.entity.Product

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.File

@RunWith(AndroidJUnit4::class)
class PaymentLocalRepositoryHelperTest {
    private lateinit var mHelper: PaymentLocalRepositoryHelper

    private fun deleteDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        File(appContext.dataDir, "databases").listFiles().forEach { it.delete() }
    }

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.databaseBuilder(appContext, PaymentLocalDatabase::class.java, "paymentdb-test")
            .allowMainThreadQueries()
            .build()

        mHelper = PaymentLocalRepositoryHelper(db.productDao())
    }


    @Test
    fun findAll_whenCalled_thenSizeSuccessful() {
        mHelper.saveProduct(Product(code = "test-1", name = "Test Product 1", unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-2", name = "Test Product 2", unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-3", name = "Test Product 2", unitPrice = 100.0))

        val expectedSize = 3

        val products = mHelper.findAllProducts()

        assertEquals(expectedSize, products.size)
        deleteDatabase()
    }
}