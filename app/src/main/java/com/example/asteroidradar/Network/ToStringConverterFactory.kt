package com.example.asteroidradar.Network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

import retrofit2.Converter

import retrofit2.Retrofit

import okhttp3.ResponseBody
import java.lang.reflect.Type


class ToStringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            Converter { value -> value.string() }
        } else null
    }

    fun requestBodyConverter(
        type: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return if (String::class.java == type) {
            Converter<String?, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
        } else null
    }

    companion object {
        private val MEDIA_TYPE: MediaType? = "text/plain".toMediaTypeOrNull()
    }
}
