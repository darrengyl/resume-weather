package com.interview.darrengu.resumeweather.model

import com.interview.darrengu.resumeweather.network.NetworkApi

/**
 * This class is a repository to fetch country object. The source of the repository can be from restful service or database
 * in the future
 *
 * @property networkApi The api retrofit uses for restful service
 */
class Repository(private val networkApi: NetworkApi) {
    suspend fun getCountry(countryName: String): CountryResult {
        return try {
            val countries = networkApi.getCountryDetail(countryName).await()
            val country = countries.filter { country -> country.name == countryName }[0]
            CountryResult.OnSuccessfulResult(country)
        } catch (exception: Throwable) {
            CountryResult.OnErrorResult(exception.localizedMessage)
        }
    }
}