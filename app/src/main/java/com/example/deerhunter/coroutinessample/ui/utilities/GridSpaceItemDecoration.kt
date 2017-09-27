package com.example.deerhunter.coroutinessample.ui.utilities

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpaceItemDecoration(private val spanCount: Int,
                              private val horizontalSpacing: Int,
                              private val verticalSpacing: Int,
                              private val includeEdge: Boolean,
                              private val orientation: Int,
                              private val headerNum: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) - headerNum

        if (position >= 0) {
            if (orientation == GridLayoutManager.HORIZONTAL) {
                setupOffsetsForHorizontalOrientation(position, outRect)
            } else {
                setupOffsetsForVerticalOrientation(position, outRect)
            }
        } else {
            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        }
    }

    private fun setupOffsetsForVerticalOrientation(position: Int, outRect: Rect) {
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount
            outRect.right = (column + 1) * horizontalSpacing / spanCount

            if (position < spanCount) {
                outRect.top = verticalSpacing
            }
            outRect.bottom = verticalSpacing
        } else {
            outRect.left = column * horizontalSpacing / spanCount
            outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount
            if (position >= spanCount) {
                outRect.top = verticalSpacing
            }
        }
    }

    private fun setupOffsetsForHorizontalOrientation(position: Int, outRect: Rect) {
        val row = position % spanCount

        if (includeEdge) {
            outRect.top = verticalSpacing - row * verticalSpacing / spanCount
            outRect.bottom = (row + 1) * verticalSpacing / spanCount

            if (position / spanCount == 0) {
                outRect.left = horizontalSpacing
            }
            outRect.right = horizontalSpacing
        } else {
            outRect.top = row * verticalSpacing / spanCount
            outRect.bottom = verticalSpacing - (row + 1) * verticalSpacing / spanCount
            if (position / spanCount != 0) {
                outRect.left = horizontalSpacing
            }
        }
    }
}