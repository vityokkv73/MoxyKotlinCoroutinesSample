package com.example.deerhunter.coroutinessample.utilities

import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseConverterFactory(gson: Gson) : Converter.Factory() {
    private val gsonConverterFactory = GsonConverterFactory.create(gson)

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        if (type.isValidApiResponseType()) {
            val actualType = (type as ParameterizedType).actualTypeArguments[0]
            val gsonConverter = gsonConverterFactory.responseBodyConverter(actualType, annotations, retrofit)
            return ApiResponseConverter(gsonConverter)
        } else {
            return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
        }
    }

    private fun Type?.isValidApiResponseType(): Boolean {
        return this != null
                && this is ParameterizedType
                && this.rawType == ApiResponse::class.java
                && this.actualTypeArguments?.size == 1
    }
}