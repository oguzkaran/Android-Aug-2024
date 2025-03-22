package org.csystem.app.android.payment.dataservice

import com.karandev.data.exception.repository.RepositoryException
import com.karandev.data.exception.service.DataServiceException
import org.csystem.app.android.payment.dataservice.dto.PaymentInfoDTO
import org.csystem.app.android.payment.dataservice.dto.ProductInfoDTO
import org.csystem.app.android.payment.dataservice.mapper.IProductMapper
import org.csystem.app.android.payment.repository.dal.PaymentLocalRepositoryHelper
import java.time.LocalDate
import javax.inject.Inject

class PaymentDataService @Inject constructor(paymentLocalRepositoryHelper: PaymentLocalRepositoryHelper, productMapper: IProductMapper) {
    private val mPaymentLocalRepositoryHelper = paymentLocalRepositoryHelper
    private val mProductMapper = productMapper

    fun findAllProducts() : List<ProductInfoDTO> {
        try {
            return mPaymentLocalRepositoryHelper.findAllProducts().map { mProductMapper.toProductInfoDTO(it) }.toList()
        }
        catch (ex: RepositoryException) {
            throw DataServiceException("PaymentDataService.findAllProducts", ex)
        }
    }

    fun findPayments(date: LocalDate = LocalDate.now()) : List<PaymentInfoDTO> {
        try {
            TODO()
        }
        catch (ex: RepositoryException) {
            throw DataServiceException("PaymentDataService.findAllProducts", ex)
        }
    }
}