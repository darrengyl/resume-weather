package com.interview.darrengu.resumeweather.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.interview.darrengu.resumeweather.R
import com.interview.darrengu.resumeweather.model.Education
import kotlinx.android.synthetic.main.view_education.view.*

class EducationView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_education, this, true)
    }

    fun bindEducation(education: Education) {
        with(education) {
            schoolNameTextView.text = schoolName
            majorTextView.text = major
            graduationDateTextView.text = graduationDate
        }
    }


}