package com.example.appweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {
    var cityname: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var intent = getIntent()
        var city: String = intent.getStringExtra("city_name").toString()
        if(city.equals("")){
            cityname = "Hanoi"
            get7NextDayWeatherData(cityname)
        }
        else{
            cityname = city
            get7NextDayWeatherData(cityname)
        }
    }

    private fun get7NextDayWeatherData(data: String) {
        var url: String = "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=ee90372a67e9138caf38fccdf9826e26"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> {
                    response -> Log.d("Kq2: ", response)

            },
            Response.ErrorListener {  })
        queue.add(stringRequest)
    }
}