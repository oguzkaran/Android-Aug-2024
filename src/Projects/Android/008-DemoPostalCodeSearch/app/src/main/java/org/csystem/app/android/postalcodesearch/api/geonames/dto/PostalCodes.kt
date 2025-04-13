package org.csystem.app.android.postalcodesearch.api.geonames.dto

import com.google.gson.annotations.SerializedName

data class PostalCodes(
    @SerializedName("postalcodes")
    val postalCodes: List<PostalCode>,
)