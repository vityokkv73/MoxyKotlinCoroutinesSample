package com.example.deerhunter.coroutinessample.utilities

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE

class MemoryManager(context: Context) {

    private val MAX_API_LOG_SIZE = 3

    /**
     * Количество оперативной памяти выделенное под heap приложения в мегабайтах
     */
    private val memoryClass: Int

    val imageLoaderCacheSize: Long
        get() = (mbToBytes(memoryClass) / 4).toLong()

    val apiLogSize: Int
        get() = mbToBytes(if (memoryClass / 8 > MAX_API_LOG_SIZE) MAX_API_LOG_SIZE else memoryClass / 8)

    init {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        memoryClass = am.memoryClass
    }

    private fun mbToBytes(mb: Int): Int {
        return mb * 1024 * 1024
    }
}
