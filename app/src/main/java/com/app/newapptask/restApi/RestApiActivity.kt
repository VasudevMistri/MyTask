package com.app.newapptask.restApi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.newapptask.restApi.adapter.EmployeeAdapter
import com.app.newapptask.databinding.ActivityRestapiBinding
import com.app.mytask.network.ViewModelFactory
import com.app.mytask.viewmodel.EmployeeViewModel
import com.app.newapptask.R

class RestApiActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityRestapiBinding
    lateinit var viewModel: EmployeeViewModel
    lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_restapi)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance()
        ).get(EmployeeViewModel::class.java)

        getEmployeeData()

    }

    private fun getEmployeeData() {
        mBinding.progressCircular.visibility= View.VISIBLE
        viewModel.getEmployeeData().observe(this) { baseModel ->
            if (baseModel != null) {
                if (baseModel.data != null && baseModel.data.size > 0) {
                    employeeAdapter = EmployeeAdapter(this, baseModel.data)
                    mBinding.rvLocal.adapter = employeeAdapter
                    mBinding.progressCircular.visibility= View.GONE
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}