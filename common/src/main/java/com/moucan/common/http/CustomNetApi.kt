package com.moucan.common.http

import com.moucan.common.retrofit.annotation.Field
import com.moucan.common.retrofit.annotation.Get
import com.moucan.common.retrofit.annotation.Post
import com.moucan.common.retrofit.annotation.Query
import okhttp3.Call


interface CustomNetApi {
    @Post("/v3/weather/weatherInfo")
    fun postWeather(@Field("city") city: String?, @Field("key") key: String?): Call?


    @Get("/v3/weather/weatherInfo")
    fun getWeather(@Query("city") city: String?, @Query("key") key: String?): Call?
}