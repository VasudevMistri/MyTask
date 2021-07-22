package com.app.mytask.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.mytask.viewmodel.EmployeeViewModel


class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var single = ViewModelFactory()

        fun getInstance(): ViewModelFactory {
            if (single == null) {
                single = ViewModelFactory()
            }
            return single
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {

            modelClass.isAssignableFrom(EmployeeViewModel::class.java) -> {
                return EmployeeViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}