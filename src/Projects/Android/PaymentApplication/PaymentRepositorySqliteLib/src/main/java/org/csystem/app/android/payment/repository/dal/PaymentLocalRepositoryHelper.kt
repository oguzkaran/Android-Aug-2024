package org.csystem.app.android.payment.repository.dal

import com.karandev.data.exception.repository.RepositoryException
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import org.csystem.app.android.payment.repository.entity.Category
import org.csystem.app.android.payment.repository.entity.Product
import javax.inject.Inject

class PaymentLocalRepositoryHelper @Inject constructor(paymentLocalDatabase: PaymentLocalDatabase) {
    private val mProductDao = paymentLocalDatabase.productDao()
    private val mCategoryDao = paymentLocalDatabase.categoryDao()

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

    //...
}