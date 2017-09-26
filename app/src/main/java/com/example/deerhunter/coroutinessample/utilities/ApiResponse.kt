package com.example.deerhunter.coroutinessample.utilities

import okhttp3.ResponseBody
import okio.BufferedSource

class ApiResponse<out T>(val typed: T, val raw: ResponseBody) {
    fun source() : BufferedSource = raw.source()
}