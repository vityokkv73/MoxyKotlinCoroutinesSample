package com.example.deerhunter.coroutinessample.ui.utilities

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.utilities.getDisplaySize

class UiCalculator(context: Context) {
    private val resources = context.resources
    val isTablet by lazy { resources.getBoolean(R.bool.isTablet) }
    val displayWidth by lazy { context.getDisplaySize().x }
    val displayHeight by lazy { context.getDisplaySize().y }

    private val horizontalPadding by lazy { resources.getDimensionPixelSize(R.dimen.media_view_horizontal_padding) }
    private val defaultChannelCardWidth by lazy { resources.getDimensionPixelSize(R.dimen.channel_card_width) }
    private val horizontalSpaceBetweenCards by lazy { resources.getDimensionPixelOffset(R.dimen.horizontal_space_between_cards) }

    private val defaultMediaItemCardHeight by lazy { context.resources.getDimensionPixelSize(R.dimen.movie_card_height) }
    private val defaultMediaItemCardWidth by lazy { context.resources.getDimensionPixelSize(R.dimen.movie_card_width) }

    val contentWidth = displayWidth - horizontalPadding * 2
    val mediaItemDivider by lazy { context.resources.getDimensionPixelSize(R.dimen.media_item_divider) }

    fun isPortraitOrientation() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    private fun calculateColumnsCount(): Int {
        if (!isTablet) return MOBILE_COLUMNS_COUNT

        val spanCount = (contentWidth + horizontalSpaceBetweenCards) / (defaultChannelCardWidth + horizontalSpaceBetweenCards)
        return when {
            spanCount >= TABLET_MAX_COLUMNS_COUNT -> TABLET_MAX_COLUMNS_COUNT
            spanCount >= TABLET_MIDDLE_COLUMNS_COUNT -> TABLET_MIDDLE_COLUMNS_COUNT
            else -> TABLET_MIN_COLUMNS_COUNT
        }
    }

    fun calculateRowData(): RowLayoutData {
        val columnsCount = calculateColumnsCount()
        val channelCardWidth = calculateChannelCardWidth(columnsCount)
        val zoomFactor = channelCardWidth.toDouble() / defaultChannelCardWidth
        val mediaItemCardSize = calculateMediaItemCardSize(horizontalPadding, channelCardWidth, zoomFactor)
        return RowLayoutData(columnsCount, horizontalPadding, channelCardWidth, zoomFactor, mediaItemCardSize)
    }

    private fun calculateChannelCardWidth(columnsCount: Int) = (contentWidth - (columnsCount - 1) * horizontalSpaceBetweenCards) / columnsCount

    private fun calculateMediaItemCardSize(horizontalSpacing: Int, channelCardWidth: Int, zoomFactor: Double): Point {
        return if (isTablet) {
            Point(channelCardWidth, (defaultMediaItemCardHeight * zoomFactor).toInt())
        } else {
            val width = (contentWidth - horizontalSpacing) / 2
            val height = (width * (defaultMediaItemCardHeight.toDouble() / defaultMediaItemCardWidth)).toInt()
            Point(width, height)
        }
    }

    fun calculateColumnsWidth(columnsCount: Int, columnWidth: Int, dividerWidth: Int = mediaItemDivider) : Int {
        if (columnsCount == 0) return 0
        return columnsCount * columnWidth + (columnsCount-1)*dividerWidth
    }

    data class RowLayoutData(val columnsCount: Int, val horizontalPadding: Int, val channelCardWidth: Int,
                             val zoomFactor: Double, val movieCardSize: Point)

    companion object {
        const val TABLET_MAX_COLUMNS_COUNT = 6
        const val TABLET_MIDDLE_COLUMNS_COUNT = 4
        const val TABLET_MIN_COLUMNS_COUNT = 2
        const val MOBILE_COLUMNS_COUNT = 2
    }
}