package org.csystem.app.android.randomusers.api.me.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataService {
    @GET
    fun getData(@Url urlStr: String): Call<ResponseBody>
}