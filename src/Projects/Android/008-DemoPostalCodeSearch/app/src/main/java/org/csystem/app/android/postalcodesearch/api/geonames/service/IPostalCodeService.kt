package org.csystem.app.android.postalcodesearch.api.geonames.service

import org.csystem.app.android.postalcodesearch.api.geonames.dto.PostalCodes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IPostalCodeService {
    @GET("postalCodeLookupJSON")
    fun findByPostalCode(@Query("postalcode") postalCode: String,
                         @Query("username")username: String,
                         @Query("country")country: String): Call<PostalCodes>
}