package com.interview.darrengu.resumeweather.network

import com.interview.darrengu.resumeweather.model.Country
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {
    @GET("/rest/v1/name/{countryName}")
    fun getCountryDetail(@Path("countryName") countryName: String): Deferred<List<Country>>
}