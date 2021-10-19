package com.example.appweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {
    var cityname: String = ""
    lateinit var image_back: ImageView
    lateinit var txtCityName2: TextView
    lateinit var list_view: ListView
    lateinit var customAdapter: CustomAdapterListView
    lateinit var arrWeather: ArrayList<Weather>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Mapping();
        var intent = getIntent()
        var city: String = intent.getStringExtra("city_name").toString()
        if (city.equals("")) {
            cityname = "Hanoi"
            get7NextDayWeatherData(cityname)
        } else {
            cityname = city
            get7NextDayWeatherData(cityname)
        }
        image_back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                onBackPressed()
            }
        })
    }

    private fun get7NextDayWeatherData(data: String) {
        var url: String =
            "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=7&appid=ee90372a67e9138caf38fccdf9826e26"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Kq2: ", response)
                var jsObject: JSONObject = JSONObject(response)
                var jsObjectCity: JSONObject = jsObject.getJSONObject("city")
                var nameCity: String = jsObjectCity.getString("name")
                txtCityName2.setText(nameCity)

                var jsArrayList: JSONArray = jsObject.getJSONArray("list")
                for (i in 0 until jsArrayList.length()) {
                    var jsObjectList: JSONObject = jsArrayList.getJSONObject(i)
                    //handle date
                    var day: Long = jsObjectList.getString("dt").toLong()
                    var date: Date = Date(day * 1000)

                    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd")
                    var Day: String = simpleDateFormat.format(date)
                    //handle temperature
                    var jsObjectTemp: JSONObject = jsObjectList.getJSONObject("main")
                    var tempMax: String = jsObjectTemp.getString("temp_max")
                    var tempMin: String = jsObjectTemp.getString("temp_min")
                    var txtTempMax: String = tempMax.toDouble().toInt().toString()
                    var txtTempMin: String = tempMin.toDouble().toInt().toString()

                    //handle weather
                    var jsArrWeather: JSONArray = jsObjectList.getJSONArray("weather")
                    var jsObjectWeather: JSONObject = jsArrWeather.getJSONObject(0)
                    var status: String = jsObjectWeather.getString("description")
                    var icon: String = jsObjectWeather.getString("icon")

                    arrWeather.add(i, Weather(Day, status, icon, txtTempMax, txtTempMin))
                }
                customAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { })
        queue.add(stringRequest)
    }

    private fun Mapping() {
        image_back = findViewById(R.id.image_back_main2)
        txtCityName2 = findViewById(R.id.textViewCityName_main2)
        list_view = findViewById(R.id.listView_main2)
        arrWeather = ArrayList<Weather>()
        customAdapter = CustomAdapterListView(this, arrWeather)
        list_view.setAdapter(customAdapter)
    }

}
