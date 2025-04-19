package org.csystem.app.android.randomusers.api.me.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RandomUserInfo(
    @SerializedName("results")
    val users: List<RandomUser>,
    @SerializedName("info")
    val info: Info,
)

data class RandomUser(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("location")
    val location: Location,
    @SerializedName("email")
    val email: String,
    @SerializedName("login")
    val login: Login,
    @SerializedName("dob")
    val dob: Dob,
    @SerializedName("registered")
    val registered: Registered,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("cell")
    val cell: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("picture")
    val picture: Picture,
    @SerializedName("nat")
    val nat: String,
): Serializable {
    override fun toString() = "${name.title} ${name.first} ${name.last}"
}

data class Name(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String,
): Serializable

data class Location(
    @SerializedName("street")
    val street: Street,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("coordinates")
    val coordinates: Coordinates,
    @SerializedName("timezone")
    val timezone: Timezone,
): Serializable

data class Street(
    @SerializedName("number")
    val number: Long,
    @SerializedName("name")
    val name: String,
): Serializable

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
): Serializable

data class Timezone(
    @SerializedName("offset")
    val offset: String,
    @SerializedName("description")
    val description: String,
): Serializable

data class Login(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("salt")
    val salt: String,
    @SerializedName("md5")
    val md5: String,
    @SerializedName("sha1")
    val sha1: String,
    @SerializedName("sha256")
    val sha256: String,
): Serializable

data class Dob(
    @SerializedName("date")
    val date: String,
    @SerializedName("age")
    val age: Long,
): Serializable

data class Registered(
    @SerializedName("date")
    val date: String,
    @SerializedName("age")
    val age: Long,
): Serializable

data class Id(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String,
): Serializable

data class Picture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
): Serializable

data class Info(
    @SerializedName("seed")
    val seed: String,
    @SerializedName("results")
    val results: Long,
    @SerializedName("page")
    val page: Long,
    @SerializedName("version")
    val version: String,
)