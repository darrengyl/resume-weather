# resume-weather
Create an Android application that contains the following three screens:
1. Create a profile of yourself. At minimum include your name, a picture and a scrollable summary of your
education and work experience.
2. Create a screen that contains a list of countries.
a. A list of countries can be found here https://github.com/vinaygaba/Ultimate-String-Array-
List/blob/master/Countries.xml

3. When a country is selected transition to a country detail screen.
a. Data for the detail screen should be retrieved by making an HTTP GET to
https://restcountries.eu/rest/v1/name/{countryName}  where “{countryName}” is the name of
the country from the list (ex. https://restcountries.eu/rest/v1/name/Argentina ).
b. The country detail screen should show the name of the country, its capital, population, area,
region and sub-region.

Provide navigation between the three screens as you see fit.
