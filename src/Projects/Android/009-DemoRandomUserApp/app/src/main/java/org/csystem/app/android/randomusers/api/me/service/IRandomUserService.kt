package org.csystem.app.android.randomusers.api.me.service

import org.csystem.app.android.randomusers.api.me.dto.RandomUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRandomUserService {
    @GET("/")
    fun findUser(@Query("results") count: Int): Call<RandomUserInfo>
    @GET("/")
    fun findUser(): Call<RandomUserInfo>
}