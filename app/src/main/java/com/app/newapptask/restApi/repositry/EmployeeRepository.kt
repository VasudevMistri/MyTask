package com.app.mytask.repositry

import androidx.lifecycle.MutableLiveData
import com.app.mediq.networks.ApiClient
import com.app.mediq.networks.ApiInterface
import com.app.mytask.model.EmployeeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeRepository {
    var call: Call<EmployeeModel>? = null

    fun callApi(): MutableLiveData<EmployeeModel> {
        cancelApi()
        val mutableLiveData: MutableLiveData<EmployeeModel> = MutableLiveData()
        val apiInterface = ApiClient.get().client!!.create(ApiInterface::class.java)
        val call = apiInterface.getUsers()

        call.enqueue(object : Callback<EmployeeModel> {
            override fun onResponse(
                call: Call<EmployeeModel>,
                response: Response<EmployeeModel>
            ) {
                mutableLiveData.value = response.body()

            }

            override fun onFailure(call: Call<EmployeeModel>, t: Throwable) {
                mutableLiveData.value = null
            }

        })

        return mutableLiveData
    }

    fun cancelApi() {
        if (call != null && call!!.isExecuted) {
            call!!.cancel()
        }
    }


}