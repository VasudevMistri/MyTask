package com.app.newapptask.pdf

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.app.newapptask.R
import com.app.newapptask.databinding.ActivityDownloadPdfBinding
import com.downloader.OnDownloadListener
import com.downloader.OnProgressListener
import com.downloader.PRDownloader
import com.downloader.Progress
import java.io.File
import java.util.*

class DownloadPDFActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityDownloadPdfBinding
    var pdfName = ""

    companion object {
        const val STORAGE_PERMISSION_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_download_pdf
        )


        mBinding.btnDownloadPdf.setOnClickListener {
            pdfName = (Math.random() * 4).toString()
            if (hasExternalReadWritePermission()) {
                val external = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "MyTask")
                if (!external.exists()) {
                    external.mkdir()
                    pdfDownload(external.path)
                } else {
                    pdfDownload(external.path)
                }
            } else {
                askForStoragePermission()
            }
        }
    }


    private fun hasExternalReadWritePermission(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }


    private fun askForStoragePermission() {
        if (!hasExternalReadWritePermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ), STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val external = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "MyTask")
                if (!external.exists()) {
                    external.mkdir()
                    pdfDownload(external.path)
                } else {
                    pdfDownload(external.path)
                }
            } else {
                Toast.makeText(this, "Please grant external storage permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun pdfDownload(external: String) {
        mBinding.progressCircular.visibility = View.VISIBLE
        PRDownloader.download(
            "http://www.africau.edu/images/default/sample.pdf",
            external,
            pdfName + ".pdf"
        )
            .build()
            .setOnProgressListener {
                object : OnProgressListener {
                    override fun onProgress(progress: Progress?) {
                        Log.e("onRes Progress", "" + progress?.currentBytes)
                    }
                }
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Log.e("onRes Down", external + "/" + pdfName + ".pdf")
                    Handler().postDelayed({
                        mBinding.progressCircular.visibility = View.GONE
                        Toast.makeText(
                            this@DownloadPDFActivity,
                            "Download successfully for this path " + external + "/" + pdfName + ".pdf",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, 2000)

                    mBinding.tvURL.setText(external + "/" + pdfName + ".pdf")
                }

                override fun onError(error: com.downloader.Error?) {
                    mBinding.progressCircular.visibility = View.GONE
                    Toast.makeText(
                        this@DownloadPDFActivity,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

}