package com.interview.darrengu.resumeweather

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.interview.darrengu.resumeweather.model.Country
import com.interview.darrengu.resumeweather.model.CountryResult
import com.interview.darrengu.resumeweather.model.Repository
import com.interview.darrengu.resumeweather.viewmodel.CountryDetailViewModel
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*


class CountryDetailViewModelUnitTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: CountryDetailViewModel
    private lateinit var repository: Repository
    private val observer: Observer<CountryResult> = mock(Observer::class.java) as Observer<CountryResult>

    @Before
    fun SetUp() {
        repository = mock(Repository::class.java)
        viewModel = CountryDetailViewModel(repository).apply {
            savedCountryDetail.observeForever(observer)
        }
    }

    @Test
    fun GetCountry_ErrorResult_ShouldCreateOnErrorResult() {
        val errorString = "test error"
        val argumentCaptor: ArgumentCaptor<CountryResult> = ArgumentCaptor.forClass(
            CountryResult::class.java)
        `when`(repository.getCountry(ArgumentMatchers.anyString())).thenReturn(Single.error(Throwable(errorString)))
        viewModel.getCountry("error")
        verify(observer).onChanged(argumentCaptor.capture())
        val countryResult = argumentCaptor.value
        assertTrue(countryResult is CountryResult.OnErrorResult)
        assertEquals(errorString, (countryResult as CountryResult.OnErrorResult).message)
    }

    @Test
    fun GetCountry_FirstRequestSuccessfulResult_ShouldReturnSuccessfulResult() {
        val country =
            Country("countryName", "capital", "region", "subregion", 0, 0.0)
        val argumentCaptor: ArgumentCaptor<CountryResult> = ArgumentCaptor.forClass(
            CountryResult::class.java)
        `when`(repository.getCountry(ArgumentMatchers.anyString())).thenReturn(Single.just(country))
        viewModel.getCountry("test")
        verify(observer).onChanged(argumentCaptor.capture())
        val countryResult = argumentCaptor.value
        assertTrue(countryResult is CountryResult.OnSuccessfulResult)
        assertSame(country, (countryResult as CountryResult.OnSuccessfulResult).country)
    }

    @Test
    fun GetCountry_SecondRequestSuccessfulResult_ShouldCallGetCountryOnce() {
        val country =
            Country("country1", "capital", "region", "subregion", 0, 0.0)
        val argumentCaptor: ArgumentCaptor<CountryResult> = ArgumentCaptor.forClass(
            CountryResult::class.java)
        `when`(repository.getCountry(ArgumentMatchers.anyString())).thenReturn(Single.just(country))
        viewModel.getCountry("country1")
        Thread.sleep(1000)
        viewModel.getCountry("country1")
        verify(repository, times(1)).getCountry(ArgumentMatchers.anyString())
        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        val countryResult = argumentCaptor.value
        assertTrue(countryResult is CountryResult.OnSuccessfulResult)
        assertSame(country, (countryResult as CountryResult.OnSuccessfulResult).country)
    }
}