package com.example.appweather

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var editSearch: EditText
    lateinit var btnSearch: Button
    lateinit var btnChangeActivity: Button
    lateinit var txtCityName : TextView
    lateinit var txtCountryName: TextView
    lateinit var txtTemperature: TextView
    lateinit var txtState: TextView
    lateinit var txtHumidity: TextView
    lateinit var txtCloud: TextView
    lateinit var txtWind: TextView
    lateinit var txtUpdateDay: TextView
    lateinit var imgIcon: ImageView
    lateinit var City: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Mappings();
        GetCurrentWeatherData("Hanoi")
        btnSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
               var city: String = editSearch.text.toString()
                if(city.equals("")){
                    City = "Hanoi"
                    GetCurrentWeatherData(City)
                }
                else {
                    City = city
                    GetCurrentWeatherData(City)
                }
            }
        })
       btnChangeActivity.setOnClickListener(object: View.OnClickListener{
           override fun onClick(view: View?) {
               var city: String = editSearch.text.toString()
               val intent = Intent(this@MainActivity, MainActivity2::class.java)
               intent.putExtra("city_name", city)
               startActivity(intent)
           }
       })
    }

    fun GetCurrentWeatherData(data: String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=ee90372a67e9138caf38fccdf9826e26"
// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String>{
                             response ->  Log.d("kq",response)

                var jsObject: JSONObject = JSONObject(response)
                //handle the city name, country name data from API and print it to the monitor
                var nameCity: String = jsObject.getString("name")
                txtCityName.setText("City name: " + nameCity)


                //handle the date data from API and print it to monitor
                var day: String = jsObject.getString("dt")
                var l: Long = day.toLong()
                var date: Date = Date(l*1000)

                var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd HH-mm")
                var Day:String = simpleDateFormat.format(date)
                txtUpdateDay.setText(Day)

                //handle the weather data from API and print it to monitor
                var jsArrayWeather: JSONArray = jsObject.getJSONArray("weather")
                var jsObjectWeather: JSONObject = jsArrayWeather.getJSONObject(0)
                var status: String = jsObjectWeather.getString("main")
                var des: String = jsObjectWeather.getString("description")
                txtState.setText(status + " (" + des + ")")
                //handle icon images

                var icon: String = jsObjectWeather.getString("icon")
                Picasso.get().load("https://openweathermap.org/img/wn/" + icon + ".png").into(imgIcon)

                //handle the humidity and temperature
                var jsObjectMain: JSONObject = jsObject.getJSONObject("main")
                var temperature: String = jsObjectMain.getString("temp")
                var humidity: String = jsObjectMain.getString("humidity")
                var a: Double = temperature.toDouble()
                var Temp: String = a.toInt().toString()
                txtTemperature.setText("Temp: " + Temp + "°C");
                txtHumidity.setText("Hum: " + humidity + "%")

                //handle the wind
                var jsObjectWind: JSONObject = jsObject.getJSONObject("wind")
                var wind: String = jsObjectWind.getString("speed")
                txtWind.setText("Wind: " + wind + "m/s")

                //handle the cloud
                var jsObjectCloud: JSONObject = jsObject.getJSONObject("clouds")
                var cloud: String = jsObjectCloud.getString("all")
                txtCloud.setText("Cloud: " + cloud + "%")

                //handle the country name
                var jsonObjectSys: JSONObject = jsObject.getJSONObject("sys")
                var nameCountry: String = jsonObjectSys.getString("country")
                txtCountryName.setText("Country name: " + nameCountry)

        }, Response.ErrorListener {
        })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }



    private fun Mappings() {
        editSearch = findViewById(R.id.editTextSearch)
        btnSearch = findViewById(R.id.buttonSearch)
        btnChangeActivity = findViewById(R.id.buttonChangeActivity)
        txtCityName = findViewById(R.id.textViewCity)
        txtCountryName = findViewById(R.id.textViewCountry)
        txtTemperature = findViewById(R.id.textViewTemperature)
        txtState = findViewById(R.id.textViewState)
        txtHumidity = findViewById(R.id.textViewHumidity)
        txtCloud = findViewById(R.id.textViewCloud)
        txtWind = findViewById(R.id.textViewWind)
        txtUpdateDay = findViewById(R.id.textViewUpdateDay)
        imgIcon = findViewById(R.id.imageIcon)
    }

}