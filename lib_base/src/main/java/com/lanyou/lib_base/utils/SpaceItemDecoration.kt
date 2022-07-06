package com.lanyou.lib_base.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(var left: Int = 0, var top: Int = 0, var right: Int = 0, var bottom: Int = 0) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
            outRect.left = this.left
            outRect.top = this.top
            outRect.right = this.right
            outRect.bottom = this.bottom
    }
}