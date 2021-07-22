package com.app.mediq.networks

import java.io.IOException

class NoConnectivityException : IOException() {
  override  val message: String
        get() = "No connectivity exception"
}