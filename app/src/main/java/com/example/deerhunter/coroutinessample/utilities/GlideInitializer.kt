package com.example.deerhunter.coroutinessample.utilities

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelCache
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber
import java.io.InputStream

@GlideModule
class GlideInitializer : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryManager = MemoryManager(context)
        val memorySizeCalculator = MemorySizeCalculator.Builder(context).build()

        builder.setMemoryCache(LruResourceCache(memoryManager.imageLoaderCacheSize))
            .setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .disallowHardwareConfig()
            )

        Timber.d(
            "Glide memory cache size suggestions: memory size calculator = %d, memory manager = %d",
            memorySizeCalculator.memoryCacheSize, memoryManager.imageLoaderCacheSize
        )

        builder.setLogLevel(Log.ERROR)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        glide.registry.replace(String::class.java, InputStream::class.java, ApiModelLoader.Factory())
    }
}

class ApiModelLoader(
    modelLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<String, GlideUrl>
) : BaseGlideUrlLoader<String>(modelLoader, modelCache) {

    override fun getUrl(model: String, width: Int, height: Int, options: Options) =
        "http://image.tmdb.org/t/p/w185$model"

    override fun handles(model: String): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<String, InputStream> {

        private val modelCache = ModelCache<String, GlideUrl>(500)

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return ApiModelLoader(
                multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                modelCache
            )
        }

        override fun teardown() {
        }
    }
}
