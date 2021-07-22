package com.app.mytask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.mytask.model.EmployeeModel
import com.app.mytask.repositry.EmployeeRepository

class EmployeeViewModel : ViewModel() {

    var mutableLiveData: MutableLiveData<EmployeeModel>? = null
    var repository: EmployeeRepository? = null

    init {
        repository = EmployeeRepository()
    }

    fun getEmployeeData(): MutableLiveData<EmployeeModel> {
        mutableLiveData = null
        if (mutableLiveData == null) {
            mutableLiveData = repository!!.callApi()
        }
        return mutableLiveData!!
    }
}