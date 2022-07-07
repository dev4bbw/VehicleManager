package com.lanyou.lib_base.widgets

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lanyou.lib_base.R
import com.lanyou.lib_base.databinding.LayoutHeadBinding

class HeadLayout @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    RelativeLayout(mContext, attrs, defStyle) {
    private var mBgColor = 0
    private var mBackImg: Drawable? = null
    private var mTitle: String? = null
    private var mTitleSize = 0f
    private var mTitleColor = 0
    private var mMenuImg: Drawable? = null
    private var showBack = true
    private var showTitle = true
    private var backListener: OnBackListener? = null

    private lateinit var binding: LayoutHeadBinding

    init {
        val a = mContext.obtainStyledAttributes(attrs, R.styleable.HeadLayout)
        try {

            mBgColor = a.getInt(R.styleable.HeadLayout_head_bg_color, 0)
            mBackImg = a.getDrawable(R.styleable.HeadLayout_head_back_img)
            mTitle = a.getString(R.styleable.HeadLayout_head_title)
            mTitleSize = a.getFloat(R.styleable.HeadLayout_head_title_size, 17f)
            mTitleColor = a.getInt(R.styleable.HeadLayout_head_title_color, 0)
            mMenuImg = a.getDrawable(R.styleable.HeadLayout_head_menu_img)
            showBack = a.getBoolean(R.styleable.HeadLayout_head_left_show, true)
            showTitle = a.getBoolean(R.styleable.HeadLayout_head_title_show, true)
        } finally {
            a.recycle()
        }
        initView()
    }

    private fun initView() {
        binding = LayoutHeadBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
        binding.apply {
            ivBack.setOnClickListener {
                if (backListener != null) {
                    backListener?.click()
                } else {
                    (mContext as Activity).finish()
                }
            }
            if (mBgColor != 0)
                container.setBackgroundColor(0)
            if (mBackImg != null)
                ivBack.setImageDrawable(mBackImg)
            if (mTitle != null)
                tvTitle.text = mTitle
            if (mTitleSize != 0.0f)
                tvTitle.textSize = mTitleSize
            if (mTitleColor != 0)
                tvTitle.setTextColor(mTitleColor)
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
            ivBack.visibility = if (showBack) View.VISIBLE else View.GONE

        }


    }

    fun setOnBackClick(listener: OnBackListener) {
        backListener = listener
    }


    interface OnBackListener {
        fun click()
    }

}