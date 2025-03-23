package org.csystem.app.android.payment.repository.dal

     import androidx.room.Transaction
     import com.karandev.data.exception.repository.RepositoryException
     import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
     import org.csystem.app.android.payment.repository.entity.Category
     import org.csystem.app.android.payment.repository.entity.Payment
     import org.csystem.app.android.payment.repository.entity.PaymentToProduct
     import org.csystem.app.android.payment.repository.entity.Product
     import java.time.LocalDate
     import java.time.LocalDateTime
     import javax.inject.Inject

     /**
      * Helper class for managing local payment repository operations.
      *
      * @property mProductDao Data access object for products.
      * @property mCategoryDao Data access object for categories.
      * @property mPaymentDao Data access object for payments.
      * @property mPaymentToProductDao Data access object for payment-to-product associations.
      */
     class PaymentLocalRepositoryHelper @Inject constructor(paymentLocalDatabase: PaymentLocalDatabase) {
         private val mProductDao = paymentLocalDatabase.productDao()
         private val mCategoryDao = paymentLocalDatabase.categoryDao()
         private val mPaymentDao = paymentLocalDatabase.paymentDao()
         private val mPaymentToProductDao = paymentLocalDatabase.paymentToProductDao()

         /**
          * Upsert the given product to the database.
          *
          * @param product The product to be saved.
          * @throws RepositoryException if a RuntimeException occurs during the save operation.
          */
         fun saveProduct(product: Product) {
             try {
                 mProductDao.save(product)
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.saveProduct", ex)
             }
         }

         /**
          * Saves payment information and associates it with products in a transactional manner.
          *
          * This method performs the following steps:
          * 1. Saves a new `Payment` entity to the database.
          * 2. Iterates over the provided `PaymentToProduct` objects and saves each one,
          *    associating it with the newly created `Payment` entity.
          *
          * If any `RuntimeException` occurs during the process, a `RepositoryException` is thrown.
          *
          * @param paymentToProduct Vararg parameter of `PaymentToProduct` objects containing the product payment details to be saved.
          * @throws RepositoryException if a `RuntimeException` occurs during the save operation.
          */
         @Transaction
         fun savePayment(vararg paymentToProduct: PaymentToProduct) {
            try {
                var id = mPaymentDao.save(Payment())

                paymentToProduct.forEach { it.paymentId = id; mPaymentToProductDao.save(it) }

            } catch (ex: RuntimeException) {
                throw RepositoryException("PaymentLocalRepositoryHelper.savePayment", ex)
            }
         }

         /**
          * Saves payment information with a specific date and associates it with products in a transactional manner.
          *
          * This method performs the following steps:
          * 1. Saves a new `Payment` entity with the provided date to the database.
          * 2. Iterates over the provided `PaymentToProduct` objects and saves each one,
          *    associating it with the newly created `Payment` entity.
          *
          * If any exception occurs during the process, a `RepositoryException` is thrown.
          *
          * @param dateTime The date and time for the payment.
          * @param paymentToProduct Vararg parameter of `PaymentToProduct` objects containing the product payment details to be saved.
          * @throws RepositoryException if a `RuntimeException` occurs during the save operation.
          */
         @Transaction
         fun savePayment(dateTime: LocalDateTime, vararg paymentToProduct: PaymentToProduct) {
             try {
                 var id = mPaymentDao.save(Payment(dateTime = dateTime))

                 paymentToProduct.forEach { it.paymentId = id; mPaymentToProductDao.save(it) }

             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.savePayment", ex)
             }
         }

         /**
          * Retrieves all products from the database.
          *
          * @return A list of all products.
          * @throws RepositoryException if a RuntimeException occurs during the retrieval operation.
          */
         fun findAllProducts(): List<Product> {
             try {
                 return mProductDao.findAll()
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.findAllProducts", ex)
             }
         }

         /**
          * Finds a product by its code.
          *
          * @param code The code of the product to be found.
          * @return The product with the given code.
          * @throws RepositoryException if a RuntimeException occurs during the find operation.
          */
         fun findByProductCode(code: String): Product {
             try {
                 return mProductDao.findByCode(code)
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.findByProductCode", ex)
             }
         }

         /**
          * Retrieves payment information for a specific date.
          *
          * @param date The date for which to retrieve payment information.
          * @return A list of `PaymentToProduct` entities associated with the given date.
          * @throws RepositoryException if a `RuntimeException` occurs during the retrieval operation.
          */
         fun findPaymentInfoByDate(date: LocalDate): List<PaymentToProduct> {
             try {
                 return mPaymentDao.findByDate(date)
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.findPaymentInfoByDate", ex)
             }
         }

         /**
          * Deletes the given product from the database.
          *
          * @param product The product to be deleted.
          * @throws RepositoryException if a RuntimeException occurs during the delete operation.
          */
         fun deleteProduct(product: Product) {
             try {
                 mProductDao.delete(product)
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.deleteProduct", ex)
             }
         }

         /**
          * Deletes all products from the database.
          *
          * @throws RepositoryException if a RuntimeException occurs during the delete operation.
          */
         fun deleteAllProducts() {
             try {
                 mProductDao.deleteAll()
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.deleteAll", ex)
             }
         }

         /**
          * Upsert the given category to the database.
          *
          * @param category The category to be upserted.
          * @throws RepositoryException if a RuntimeException occurs during the save operation.
          */
         fun saveCategory(category: Category) {
             try {
                 mCategoryDao.save(category)
             } catch (ex: RuntimeException) {
                 throw RepositoryException("PaymentLocalRepositoryHelper.saveCategory", ex)
             }
         }
     }