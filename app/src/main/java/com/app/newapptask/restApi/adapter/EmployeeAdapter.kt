package com.app.newapptask.restApi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.newapptask.R
import com.app.newapptask.databinding.RowEmployeeListBinding
import com.app.mytask.model.Data

class EmployeeAdapter(
    val context: Context,
    var arrayList: List<Data>
) : RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.row_employee_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mBinding!!.data = arrayList!![position]
    }

    override fun getItemCount(): Int {
        return if (arrayList == null) 0 else arrayList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class MyViewHolder internal constructor(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        var mBinding: RowEmployeeListBinding?

        init {
            mBinding = DataBindingUtil.bind(itemView!!)
        }
    }


}
