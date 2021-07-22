package com.app.mediq.networks

import com.app.mytask.model.EmployeeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiInterface {

    @Headers("Accept: application/json")
    @GET("users")
    fun getUsers(): Call<EmployeeModel>

}