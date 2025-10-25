package org.csystem.app.android.payment.repository

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.csystem.app.android.payment.repository.dal.PaymentLocalRepositoryHelper
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import org.csystem.app.android.payment.repository.entity.Category
import org.csystem.app.android.payment.repository.entity.PaymentToProduct
import org.csystem.app.android.payment.repository.entity.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

@RunWith(AndroidJUnit4::class)
class PaymentLocalRepositoryHelperTest {
    private lateinit var mHelper: PaymentLocalRepositoryHelper

    private fun insertCategories() {
        mHelper.saveCategory(Category(description = "cat-1"))
        mHelper.saveCategory(Category(description = "cat-2"))
        mHelper.saveCategory(Category(description = "cat-3"))
    }

    private fun insertProducts() {
        mHelper.saveProduct(Product(code = "test-1", name = "Test Product 1", categoryId = 1, unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-2", name = "Test Product 2", categoryId = 2, unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-3", name = "Test Product 3", categoryId = 3, unitPrice = 100.0))
    }

    private fun deleteDatabase() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        File(appContext.dataDir, "databases").listFiles()!!.forEach { it.delete() }
        insertCategories()
    }

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.databaseBuilder(appContext, PaymentLocalDatabase::class.java, "paymentdb-test")
            .allowMainThreadQueries()
            .build()

        mHelper = PaymentLocalRepositoryHelper(db)
        insertCategories()
    }


    @Test
    fun findAll_whenCalled_thenSizeSuccessful() {
        insertProducts()
        val expectedSize = 3

        val products = mHelper.findAllProducts()

        assertEquals(expectedSize, products.size)
        deleteDatabase()
    }

    @Test
    fun save_whenCalled_thenUpdate() {
        insertProducts()
        var product = Product(code = "test-3", name = "Test Product 3-updated", categoryId = 3, unitPrice = 300.0)

        mHelper.saveProduct(product)

        product = mHelper.findByProductCode("test-3")

        assertEquals("Test Product 3-updated", product.name)
        assertEquals(300.0, product.unitPrice, 0.00001)
        deleteDatabase()
    }

    @Test
    fun savePaymentInfo_whenCalled() {
        try {
            insertProducts()
            val ptp1 = PaymentToProduct(productCode = "test-1", unitPrice = 100.0, amount = 10.0)
            val ptp2 = PaymentToProduct(productCode = "test-2", unitPrice = 300.0, amount = 5.6)


            mHelper.savePayment(ptp1, ptp2)
        } catch (ex: Exception) {
            fail("Unexpected exception:${ex.message}")
        }
        finally {
            deleteDatabase()
        }
    }

    @Test
    fun findPaymentInfoByDate_whenCalled_thenSizeSuccessful() {
        insertProducts()
        val ptp1 = PaymentToProduct(productCode = "test-1", unitPrice = 100.0, amount = 10.0)
        val ptp2 = PaymentToProduct(productCode = "test-2", unitPrice = 300.0, amount = 5.6)
        val ptp3 = PaymentToProduct(productCode = "test-3", unitPrice = 300.0, amount = 5.6)

        mHelper.savePayment(ptp1, ptp2, ptp3)
        val expectedSize = 3
        val today = LocalDate.now()
        val products = mHelper.findPaymentInfoByDate(today)

        assertEquals(expectedSize, products.size)
        deleteDatabase()
    }

    @Test
    fun findPaymentInfoByDate_whenCalled_thenNotFound() {
        insertProducts()
        val ptp1 = PaymentToProduct(productCode = "test-1", unitPrice = 100.0, amount = 10.0)
        val ptp2 = PaymentToProduct(productCode = "test-2", unitPrice = 300.0, amount = 5.6)
        val ptp3 = PaymentToProduct(productCode = "test-3", unitPrice = 300.0, amount = 5.6)

        mHelper.savePayment(LocalDateTime.of(2024, Month.SEPTEMBER, 5, 17, 13, 56), ptp1, ptp2, ptp3)
        val expectedSize = 0
        val today = LocalDate.now()
        val products = mHelper.findPaymentInfoByDate(today)

        assertEquals(expectedSize, products.size)
        deleteDatabase()
    }
}