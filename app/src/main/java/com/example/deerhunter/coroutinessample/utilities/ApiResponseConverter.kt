package com.example.deerhunter.coroutinessample.utilities

import okhttp3.ResponseBody
import retrofit2.Converter
import java.nio.charset.Charset

class ApiResponseConverter(private val gsonConverter: Converter<ResponseBody, *>) : Converter<ResponseBody, ApiResponse<*>> {

    override fun convert(responseBody: ResponseBody?): ApiResponse<*>? {
        if (responseBody != null) {
            val asString = readAsString(responseBody)
            val copyOne = ResponseBody.create(responseBody.contentType(), asString)
            val copyTwo = ResponseBody.create(responseBody.contentType(), asString)
            return ApiResponse(gsonConverter.convert(copyOne), copyTwo)
        }
        return null
    }

    fun readAsString(responseBody: ResponseBody): String {
        val source = responseBody.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer().clone()
        responseBody.close()
        return buffer.readString(Charset.forName("UTF-8"))
    }
}