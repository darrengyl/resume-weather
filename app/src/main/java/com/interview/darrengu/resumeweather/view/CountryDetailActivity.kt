package com.interview.darrengu.resumeweather.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.interview.darrengu.resumeweather.R
import com.interview.darrengu.resumeweather.model.CountryResult
import com.interview.darrengu.resumeweather.model.Repository
import com.interview.darrengu.resumeweather.network.NetworkService
import com.interview.darrengu.resumeweather.viewmodel.CountryDetailViewModel
import kotlinx.android.synthetic.main.activity_country_detail.*

class CountryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)
        val countryName = intent.getStringExtra(EXTRA_COUNTRY_NAME)
        val networkApi = NetworkService.networkApi
        val repository = Repository(networkApi)
        val viewModel = ViewModelProviders.of(this, CountryDetailViewModel.Factory(repository))
            .get(CountryDetailViewModel::class.java)

        viewModel.readOnlySavedCountryDetail.observe(this, Observer { countryResult ->
            countryResult?.let {
                when(it) {
                    is CountryResult.OnSuccessfulResult -> {
                        with(it.country) {
                            countryNameTextView.text = if (name?.isNotEmpty() == true) name else getString(R.string.na)
                            capitalTextView.text = if (capital?.isNotEmpty() == true) capital else getString(R.string.na)
                            populationTextView.text = if (population != null && population > 0) population.toString() else getString(R.string.na)
                            areaTextView.text = if (area != null && area > 0.0) area.toString() else getString(R.string.na)
                            regionTextView.text = if (region?.isNotEmpty() == true) region else getString(R.string.na)
                            subRegionTextView.text = if (subregion?.isNotEmpty() == true) subregion else getString(R.string.na)
                        }
                        setCountryVisibility(true)
                        setErrorMessageVisibility(false)
                    }
                    is CountryResult.OnErrorResult -> {
                        errorMessageTextView.text = it.message
                        setCountryVisibility(false)
                        setErrorMessageVisibility(true)
                    }
                }
            }
        })

        viewModel.getCountry(countryName)
    }

    private fun setCountryVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        countryNameTextView.visibility = visibility
        capitalTextView.visibility = visibility
        populationTextView.visibility = visibility
        areaTextView.visibility = visibility
        regionTextView.visibility = visibility
        subRegionTextView.visibility = visibility
        capitalLabel.visibility = visibility
        populationLabel.visibility = visibility
        areaLabel.visibility = visibility
        regionLabel.visibility = visibility
        subRegionLabel.visibility = visibility
    }

    private fun setErrorMessageVisibility(visible: Boolean) {
        errorMessageTextView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAG = CountryDetailActivity::class.java.simpleName
        private val EXTRA_COUNTRY_NAME = "$TAG.COUNTRY_NAME"
        fun getStartActivityIntent(context: Context, countryName: String) =
            Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(EXTRA_COUNTRY_NAME, countryName)
            }
    }
}