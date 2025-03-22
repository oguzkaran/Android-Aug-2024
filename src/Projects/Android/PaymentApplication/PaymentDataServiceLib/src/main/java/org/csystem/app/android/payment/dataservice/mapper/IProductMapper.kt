package org.csystem.app.android.payment.dataservice.mapper

import org.csystem.app.android.payment.dataservice.dto.ProductInfoDTO
import org.csystem.app.android.payment.repository.entity.Product
import org.mapstruct.Mapper

@Mapper(implementationName = "ProductMapperImpl")
interface IProductMapper {
    fun toProduct(productInfo: ProductInfoDTO): Product
    fun toProductInfoDTO(product: Product): ProductInfoDTO
}