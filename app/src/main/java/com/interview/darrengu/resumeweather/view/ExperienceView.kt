package com.interview.darrengu.resumeweather.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.interview.darrengu.resumeweather.R
import com.interview.darrengu.resumeweather.model.Experience
import kotlinx.android.synthetic.main.view_experience.view.*

class ExperienceView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_experience, this, true)
    }

    fun bindExperience(experience: Experience) {
        with(experience) {
            titleTextView.text = title
            companyNameTextView.text = companyName
            locationTextView.text = location
            startEndDateTextView.text = startEndDate
            descriptionTextView.text = description
        }
    }
}