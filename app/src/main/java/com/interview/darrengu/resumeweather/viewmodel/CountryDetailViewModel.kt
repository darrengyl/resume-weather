package com.interview.darrengu.resumeweather.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.interview.darrengu.resumeweather.model.CountryResult
import com.interview.darrengu.resumeweather.model.Repository
import kotlinx.coroutines.*

/**
 * This class is a concrete viewModel of CountryDetailActivity
 * It has a cache of the last queried country
 */
class CountryDetailViewModel(private val repository: Repository) : ViewModel() {
    val savedCountryDetail: MutableLiveData<CountryResult> = MutableLiveData()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Get the country result of the query. If the last queried country has the same name as this one, return the cached
     * result, otherwise get the country result from repository
     *
     * @param countryName The country name to be queried
     */
    fun getCountry(countryName: String) {
        savedCountryDetail.value.let {
            if ((it as? CountryResult.OnSuccessfulResult)?.country?.name != countryName) {
                uiScope.launch {
                    val countryResult = withContext(Dispatchers.IO) {
                        repository.getCountry(countryName)
                    }
                    savedCountryDetail.value = countryResult
                }
            } else {
                savedCountryDetail.value = savedCountryDetail.value
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CountryDetailViewModel(repository) as T
        }
    }

}
