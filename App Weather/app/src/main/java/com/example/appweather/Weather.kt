package com.example.appweather

public class Weather {
   public lateinit var Day:String
   public lateinit var Status:String
   public lateinit var Image:String
   public lateinit var MaxTemp:String
   public lateinit var MinTemp:String

    constructor(Day: String, Status: String, Image: String, MaxTemp: String, MinTemp: String) {
        this.Day = Day
        this.Status = Status
        this.Image = Image
        this.MaxTemp = MaxTemp
        this.MinTemp = MinTemp
    }
}