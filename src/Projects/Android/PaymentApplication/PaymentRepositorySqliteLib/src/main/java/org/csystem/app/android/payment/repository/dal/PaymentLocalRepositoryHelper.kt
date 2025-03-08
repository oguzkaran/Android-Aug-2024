package org.csystem.app.android.payment.repository.dal

import com.karandev.data.exception.repository.RepositoryException
import org.csystem.app.android.payment.repository.dao.IProductDao
import org.csystem.app.android.payment.repository.entity.Product

class PaymentLocalRepositoryHelper constructor(val productDao: IProductDao) {
    fun saveProduct(product: Product) {
        try {
            productDao.insert(product)
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.saveProduct", ex)
        }
    }

    fun findAllProducts(): List<Product> {
        try {
            return productDao.findAll();
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.findAllProducts", ex)
        }
    }

    fun findByProductCode(code: String): Product {
        try {
            return productDao.findByCode(code);
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.findByProductCode", ex)
        }
    }

    fun updateProduct(product: Product) {
        try {
            productDao.update(product)
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.updateProduct", ex)
        }
    }

    fun deleteProduct(product: Product) {
        try {
            productDao.delete(product)
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.deleteProduct", ex)
        }
    }

    fun deleteAllProducts() {
        try {
            productDao.deleteAll()
        } catch (ex: RuntimeException) {
            throw RepositoryException("PaymentLocalRepositoryHelper.deleteAll", ex)
        }
    }

    //...
}