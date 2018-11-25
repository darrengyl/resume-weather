package com.interview.darrengu.resumeweather

import android.arch.lifecycle.Observer
import com.interview.darrengu.resumeweather.model.Country
import com.interview.darrengu.resumeweather.model.CountryResult
import com.interview.darrengu.resumeweather.model.Repository
import com.interview.darrengu.resumeweather.viewmodel.CountryDetailViewModel
import io.mockk.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CountryDetailViewModelUnitTest {
    private val repository = mockk<Repository>()
    private val observer = mockk<Observer<CountryResult>>()
    private val viewModel: CountryDetailViewModel = CountryDetailViewModel(repository).apply {
        readOnlySavedCountryDetail.observeForever(observer)
    }

    @BeforeEach
    fun reset() {
        clearAllMocks()
    }

    @Nested
    inner class GetCountry {
        @Test
        fun `Check if getCountry() returns error result when an exception is thrown`() {
            GlobalScope.launch {
                val errorString = "test error"
                val slot = slot<CountryResult>()
                coEvery { repository.getCountry(any()) } returns CountryResult.OnErrorResult("error")
                viewModel.getCountry("error")
                verify { observer.onChanged(capture(slot)) }
                val countryResult = slot.captured
                assertTrue(countryResult is CountryResult.OnErrorResult)
                assertEquals(errorString, (countryResult as CountryResult.OnErrorResult).message)
            }
        }

        @Test
        fun `Check if getCountry() returns new successful result from restful service when first request happens`() {
            GlobalScope.launch {
                val country = Country("countryName", "capital", "region", "subregion", 0, 0.0)
                val slot = slot<CountryResult>()
                coEvery { repository.getCountry(any()) } returns CountryResult.OnSuccessfulResult(country)
                viewModel.getCountry("countryName")
                verify { observer.onChanged(capture(slot)) }
                val countryResult = slot.captured
                assertTrue(countryResult is CountryResult.OnSuccessfulResult)
                assertSame(country, (countryResult as CountryResult.OnSuccessfulResult).country)
            }
        }

        @Test
        fun `Check if getCountry() returns same successful result from cache when a replicate request happens`() {
            GlobalScope.launch {
                val country = Country("countryName", "capital", "region", "subregion", 0, 0.0)
                val slot = slot<CountryResult>()
                coEvery { repository.getCountry(any()) } returns CountryResult.OnSuccessfulResult(country)
                viewModel.getCountry("countryName")
                viewModel.getCountry("countryName")
                coVerify { repository.getCountry("countryName") }
                verify(exactly = 2) { observer.onChanged(capture(slot)) }
                val countryResult = slot.captured
                assertTrue(countryResult is CountryResult.OnSuccessfulResult)
                assertSame(country, (countryResult as CountryResult.OnSuccessfulResult).country)
            }
        }
    }


}