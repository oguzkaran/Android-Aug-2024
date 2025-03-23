package org.csystem.app.android.payment.dataservice.mapper

import org.csystem.app.android.payment.dataservice.dto.PaymentInfoDTO
import org.csystem.app.android.payment.dataservice.dto.PaymentSaveDTO
import org.csystem.app.android.payment.repository.entity.Payment
import org.csystem.app.android.payment.repository.entity.PaymentToProduct
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(implementationName = "PaymentMapperImpl")
interface IPaymentMapper {
    @Mapping(target = "dateTime", source = "dateTime", dateFormat = "dd/MM/yyyy HH:mm:ss")
    fun toPayment(paymentInfoDTO: PaymentInfoDTO): Payment

    @Mapping(target = "dateTime", source = "dateTime", dateFormat = "dd/MM/yyyy HH:mm:ss")
    fun toPaymentInfoDTO(payment: Payment): PaymentInfoDTO

    fun toPaymentToProduct(paymentSaveDTO: PaymentSaveDTO): PaymentToProduct
}