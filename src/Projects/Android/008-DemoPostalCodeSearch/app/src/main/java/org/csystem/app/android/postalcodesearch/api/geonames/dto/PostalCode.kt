package org.csystem.app.android.postalcodesearch.api.geonames.dto

import com.google.gson.annotations.SerializedName

data class PostalCode(
    @SerializedName("adminCode2")
    val adminCode2: String,
    @SerializedName("adminCode1")
    val adminCode1: String,
    @SerializedName("adminName2")
    val adminName2: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("postalcode")
    val postalCode: String,
    @SerializedName("adminName1")
    val adminName1: String,
    @SerializedName("placeName")
    val placeName: String,
    @SerializedName("lat")
    val lat: Double
) {
    override fun toString() = placeName
}