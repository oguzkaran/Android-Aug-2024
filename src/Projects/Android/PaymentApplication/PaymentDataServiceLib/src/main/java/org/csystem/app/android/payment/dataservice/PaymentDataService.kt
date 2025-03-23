package org.csystem.app.android.payment.dataservice

import com.karandev.data.exception.repository.RepositoryException
import com.karandev.data.exception.service.DataServiceException
import org.csystem.app.android.payment.dataservice.dto.PaymentInfoDTO
import org.csystem.app.android.payment.dataservice.dto.PaymentSaveDTO
import org.csystem.app.android.payment.dataservice.dto.ProductInfoDTO
import org.csystem.app.android.payment.dataservice.mapper.IPaymentMapper
import org.csystem.app.android.payment.dataservice.mapper.IProductMapper
import org.csystem.app.android.payment.repository.dal.PaymentLocalRepositoryHelper
import java.time.LocalDate
import javax.inject.Inject

/**
 * Service class for handling payment data operations.
 *
 * @property mPaymentLocalRepositoryHelper Helper for local repository operations.
 * @property mProductMapper Mapper for product data transfer objects.
 * @property mPaymentMapper Mapper for payment data transfer objects.
 */
class PaymentDataService @Inject constructor(paymentLocalRepositoryHelper: PaymentLocalRepositoryHelper,
                                             productMapper: IProductMapper, paymentMapper: IPaymentMapper) {
    private val mPaymentLocalRepositoryHelper = paymentLocalRepositoryHelper
    private val mProductMapper = productMapper
    private val mPaymentMapper = paymentMapper

    /**
     * Saves payment information.
     *
     * @param paymentInfoDTO Vararg of PaymentSaveDTO containing payment information to be saved.
     * @throws DataServiceException if there is an error during the save operation.
     */
    fun savePayment(vararg paymentInfoDTO: PaymentSaveDTO) {
        try {
            paymentInfoDTO.forEach { mPaymentLocalRepositoryHelper.savePayment(mPaymentMapper.toPaymentToProduct(it)) }
        }
        catch (ex: RepositoryException) {
            throw DataServiceException("PaymentDataService.savePayment", ex)
        }
    }

    /**
     * Retrieves all products.
     *
     * @return List of ProductInfoDTO containing product information.
     * @throws DataServiceException if there is an error during the retrieval operation.
     */
    fun findAllProducts() : List<ProductInfoDTO> {
        try {
            return mPaymentLocalRepositoryHelper.findAllProducts().map { mProductMapper.toProductInfoDTO(it) }.toList()
        }
        catch (ex: RepositoryException) {
            throw DataServiceException("PaymentDataService.findAllProducts", ex)
        }
    }

    /**
     * Finds payments by date.
     *
     * @param date The date for which to find payments. Defaults to the current date.
     * @return List of PaymentInfoDTO containing payment information.
     * @throws DataServiceException if there is an error during the retrieval operation.
     */
    fun findPayments(date: LocalDate = LocalDate.now()) : List<PaymentInfoDTO> {
        try {
            TODO()
        }
        catch (ex: RepositoryException) {
            throw DataServiceException("PaymentDataService.findAllProducts", ex)
        }
    }
}