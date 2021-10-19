package com.example.appweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CustomAdapterListView() : BaseAdapter() {

    lateinit var context: Context
    lateinit var arrayList: ArrayList<Weather>

    constructor(context: Context, arrList: ArrayList<Weather>) : this() {
        this.context = context
        this.arrayList = arrList
    }

    override fun getCount(): Int = arrayList.size

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.line_list_view, null)

        var weather: Weather = arrayList.get(position)
        var txtDayList: TextView = view.findViewById(R.id.textViewDateList)
        var txtStatus: TextView = view.findViewById(R.id.textViewStateList)
        var txtMaxTemp: TextView = view.findViewById(R.id.textViewMaxTempList)
        var txtMinTemp: TextView = view.findViewById(R.id.textViewMinTempList)
        var imgStateList: ImageView = view.findViewById(R.id.imageStateList)

        txtDayList.setText(weather.Day)
        txtStatus.setText(weather.Status)
        txtMaxTemp.setText(weather.MaxTemp +"°C")
        txtMinTemp.setText(weather.MinTemp +"°C")
        Picasso.get().load("https://openweathermap.org/img/wn/" + weather.Image + ".png").into(imgStateList)

        return view;
    }

}