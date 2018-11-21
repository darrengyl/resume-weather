package com.interview.darrengu.resumeweather.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.interview.darrengu.resumeweather.R
import com.interview.darrengu.resumeweather.model.Education
import com.interview.darrengu.resumeweather.model.Experience
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        generateExperiences().forEach {
            val experienceView = ExperienceView(this).apply {
                bindExperience(it)
            }
            profileLayout.addView(experienceView)
        }
        generateEducations().forEach {
            val educationView = EducationView(this).apply {
                bindEducation(it)
            }
            profileLayout.addView(educationView)
        }
        nextScreenButton.setOnClickListener {
            startActivity(Intent(this, CountryListActivity::class.java))
        }
    }

    private fun generateEducations(): List<Education> =
        listOf(
            Education(
                schoolName = "Illinois Institute of Technology",
                location = "Chicago, IL",
                graduationDate = "Dec, 2014",
                major = "Master of Computer Science"
            ),
            Education(
                schoolName = "Shanghai Maritime University",
                location = "Shanghai, China",
                graduationDate = "June, 2012",
                major = "Bachelor of Communication Engineering"
            )
        )

    private fun generateExperiences(): List<Experience> =
            listOf(
                Experience(
                    title = "Android Developer",
                    companyName = "SpotOn",
                    location = "Chicago, IL",
                    startEndDate = "Jul 2018 - Present",
                    description = "Develop a point of sale app on 3rd-party digital transaction devices that are used by 25,000 merchants. This app helps merchants create their own merchandise and add discounts/fees according to their own needs."
                ),
                Experience(
                    title = "Android Developer",
                    companyName = "Outcome Health",
                    location = "Chicago, IL",
                    startEndDate = "Jul 2017 - June 2018",
                    description = "Redesign and refactor the infusion room tablet app that was running in 150k devices at 41000 offices all over the country.\n Build an internal tool for our server to diagnose devices remotely"
                ),
                Experience(
                    title = "Mobile Software Engineer",
                    companyName = "Vaporstream",
                    location = "Chicago, IL",
                    startEndDate = "Nov 2015 - Jul 2017",
                    description = "Work in a 2-man team to develop an Android messaging app-Vaporstream 2 which protects the privacy of users during chatting process."
                ),
                Experience(
                    title = "Mobile Android App Developer (Volunteer, Independent project)",
                    companyName = "Church of Chicagoans",
                    location = "Chicago, IL",
                    startEndDate = "June 2014 - Present",
                    description = "Develop a song app that can view score and play simple music. Achieved 4000+ installs. Currently 1000+ users are still using it."
                ),
                Experience(
                    title = "Mobile Software Testing and Development Engineer (Intern)",
                    companyName = "Motorola Mobility",
                    location = "Libertyville, IL",
                    startEndDate = "June 2013 - Dec 2013",
                    description = "Employed manual and automated testing procedures to capture defect, file a change request and follow up the changes made to the request using JIRA and aPython."
                )
            )
}
