package com.interview.darrengu.resumeweather.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.interview.darrengu.resumeweather.R
import kotlinx.android.synthetic.main.activity_country_list.*

class CountryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        val countryNames = resources.getStringArray(R.array.countries_array)
        val recyclerViewAdapter = CountryListAdapter(countryNames.toList()) {
            startActivity(
                CountryDetailActivity.getStartActivityIntent(
                    this,
                    it
                )
            )
        }
        with(countryList) {
            layoutManager = LinearLayoutManager(this@CountryListActivity)
            adapter = recyclerViewAdapter
        }
    }
}