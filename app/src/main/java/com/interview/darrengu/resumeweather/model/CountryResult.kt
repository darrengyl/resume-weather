package com.interview.darrengu.resumeweather.model

sealed class CountryResult {
    class OnSuccessfulResult(val country: Country) : CountryResult()
    class OnErrorResult(val message: String?) : CountryResult()
}