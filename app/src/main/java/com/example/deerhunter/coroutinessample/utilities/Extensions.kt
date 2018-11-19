package com.example.deerhunter.coroutinessample.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.Px
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestListener
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.di.activity.ActivityComponent
import com.example.deerhunter.coroutinessample.ui.BaseActivity
import java.io.Serializable

fun <T : Fragment> T.getActivityComponent(): ActivityComponent = (activity as BaseActivity).activityComponent

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any>): T {
    arguments = bundleOf(*params)
    return this
}

fun bundleOf(vararg params: Pair<String, Any>): Bundle {
    val b = Bundle()
    for (p in params) {
        val (k, v) = p
        when (v) {
            is Boolean -> b.putBoolean(k, v)
            is Byte -> b.putByte(k, v)
            is Char -> b.putChar(k, v)
            is Short -> b.putShort(k, v)
            is Int -> b.putInt(k, v)
            is Long -> b.putLong(k, v)
            is Float -> b.putFloat(k, v)
            is Double -> b.putDouble(k, v)
            is String -> b.putString(k, v)
            is CharSequence -> b.putCharSequence(k, v)
            is Parcelable -> b.putParcelable(k, v)
            is Serializable -> b.putSerializable(k, v)
            is BooleanArray -> b.putBooleanArray(k, v)
            is ByteArray -> b.putByteArray(k, v)
            is CharArray -> b.putCharArray(k, v)
            is DoubleArray -> b.putDoubleArray(k, v)
            is FloatArray -> b.putFloatArray(k, v)
            is IntArray -> b.putIntArray(k, v)
            is LongArray -> b.putLongArray(k, v)
            is Array<*> -> {
                @Suppress("UNCHECKED_CAST")
                when {
                    v.isArrayOf<Parcelable>() -> b.putParcelableArray(k, v as Array<out Parcelable>)
                    v.isArrayOf<CharSequence>() -> b.putCharSequenceArray(k, v as Array<out CharSequence>)
                    v.isArrayOf<String>() -> b.putStringArray(k, v as Array<out String>)
                    else -> throw Exception("Unsupported bundle component (${v.javaClass})")
                }
            }
            is ShortArray -> b.putShortArray(k, v)
            is Bundle -> b.putBundle(k, v)
            else -> throw Exception("Unsupported bundle component (${v.javaClass})")
        }
    }

    return b
}

private const val NO_OVERRIDE = -1

fun ImageView.loadImage(
    loadingImage: Any?,
    @Px width: Int = NO_OVERRIDE,
    @Px height: Int = NO_OVERRIDE,
    errorDrawable: Drawable? = context.getDrawableCompat(R.color.card_view_image_background_error)!!,
    placeholderDrawable: Drawable? = context.getDrawableCompat(R.color.card_view_image_background)!!,
    thumbnail: Float = NO_OVERRIDE.toFloat(),
    centerCrop: Boolean = false,
    fitCenter: Boolean = false,
    animate: Boolean = true,
    requestListener: RequestListener<Drawable>? = null,
    vararg transformations: Transformation<Bitmap>
) {

    if (!isValidGlideContext(context) || (loadingImage is String && loadingImage.isEmpty())) return

    val request = GlideApp.with(context).load(loadingImage)

    if (fitCenter) {
        request.fitCenter()
    } else if (centerCrop) {
        request.centerCrop()
    }

    /*
     * Note: if using center crop or fit center and transformations
     * you need to manually add FitCenter() or CenterCrop()
     * because glide overrides it
     */
    if (transformations.isNotEmpty()) {
        request.transforms(*transformations)
    }

    if (!animate) {
        request.dontAnimate()
    }

    if (width != NO_OVERRIDE && height != NO_OVERRIDE) {
        request.override(width, height)
    }

    if (requestListener != null) {
        request.listener(requestListener)
    }

    if (thumbnail != NO_OVERRIDE.toFloat()) {
        request.thumbnail(thumbnail)
    }

    if (errorDrawable != null) {
        request.error(errorDrawable)
    }

    if (placeholderDrawable != null) {
        request.placeholder(placeholderDrawable)
    }

    request.into(this)
}

private fun isValidGlideContext(context: Context?): Boolean {
    if (context == null) return false

    if (context is Activity) {
        if (context.isDestroyed || context.isFinishing) {
            return false
        }
    }
    return true
}

fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

private fun getImageLink(fileName: String): String {
    // we need to call /configuration to get base_url and possible size formats.
    // But as you could notice I'm too lazy to do this :) so I've just hardcoded it
        return StringBuilder("http://image.tmdb.org/t/p/w500/")
                .append(fileName.removePrefix("/")).append("?api_key=b0fc4c075392638bf2e7cdd923e9e42c").toString()
}

fun Activity.getIntExtra(key: String, defValue: Int): Int = this.intent.extras.getInt(key, defValue)

fun Activity.getSerializableExtra(key: String): Serializable? = this.intent.extras.getSerializable(key)

fun <T : Intent> T.withArguments(vararg params: Pair<String, Any>): T {
    val bundle = bundleOf(*params)
    this.putExtras(bundle)
    return this
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun Fragment.getDisplaySize(): Point {
    val display = requireActivity().windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun Context.getDisplaySize(): Point {
    val metrics = DisplayMetrics()
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(metrics)
    return Point(metrics.widthPixels, metrics.heightPixels)
}

fun View.parentViewGroup() = parent as ViewGroup

fun View?.isChildOf(possibleParent: View): Boolean {
    if (this == null) return false
    if (this == possibleParent) return true
    if (possibleParent !is ViewGroup) return false
    if (possibleParent.indexOfChild(this) > -1) return true
    if (parent != null && parent is ViewGroup) return (parent as ViewGroup).isChildOf(possibleParent)
    return false
}

fun View.setWidth(width: Int) {
    layoutParams = layoutParams.apply { this.width = width }
}

fun View.setHeight(height: Int) {
    layoutParams = layoutParams.apply { this.height = height }
}

fun View.setSize(size: Point) {
    layoutParams = layoutParams.apply {
        width = size.x
        height = size.y
    }
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun ViewGroup.inflate(layoutResId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutResId, root, attachToRoot)