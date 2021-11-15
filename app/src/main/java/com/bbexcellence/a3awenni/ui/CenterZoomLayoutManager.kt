package com.bbexcellence.a3awenni.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs


class CenterZoomLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean)
    : LinearLayoutManager(context,
    orientation,
    reverseLayout) {
    private val shrinkAmount = 0.15f
    private val shrinkDistance = 0.9f

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = shrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - shrinkAmount
            for (i in 0..childCount) {
                val child = getChildAt(i)!!
                val childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2f
                val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = shrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - shrinkAmount
            for (i in 0..childCount) {
                val child = getChildAt(i)!!
                val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                if (scale > 1) {
                    child.elevation = 16f
                }
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }
}