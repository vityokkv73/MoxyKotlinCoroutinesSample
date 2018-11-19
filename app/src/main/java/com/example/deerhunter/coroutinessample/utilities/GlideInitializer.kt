package com.example.deerhunter.coroutinessample.utilities

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideInitializer : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .disallowHardwareConfig()
        ).setLogLevel(Log.ERROR)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
