package com.app.newapptask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.newapptask.localDB.BooksMainActivity
import com.app.newapptask.restApi.RestApiActivity
import com.app.newapptask.databinding.ActivityMainBinding
import com.app.newapptask.pdf.DownloadPDFActivity

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mBinding.btnRestApi.setOnClickListener {
            val intent = Intent(this, RestApiActivity::class.java)
            startActivity(intent)
        }

        mBinding.btnDownloadPdf.setOnClickListener {
            val intent = Intent(this, DownloadPDFActivity::class.java)
            startActivity(intent)
        }

        mBinding.btnRoomDBDemo.setOnClickListener {
            val intent = Intent(this, BooksMainActivity::class.java)
            startActivity(intent)
        }
    }

}