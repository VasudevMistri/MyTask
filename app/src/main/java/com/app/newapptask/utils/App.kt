package com.app.newapptask.utils

import android.app.Application
import android.content.Context
import com.app.newapptask.network.ConnectivityTools

class App : Application() {

    var connectivityTools: ConnectivityTools? = null
    var checkInternetConnectivity: ConnectivityTools.CheckInternetConnectivity? = null

    companion object {
        private var instance: App? = null
        var appContext: Context? = null
        fun getinstance(): App? {
            return instance
        }

        fun appContext(): Context = instance!!.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
        connectivityTools = ConnectivityTools(this, checkInternetConnectivity)
        connectivityTools!!.registerInternetCheckReceiver()
    }


}