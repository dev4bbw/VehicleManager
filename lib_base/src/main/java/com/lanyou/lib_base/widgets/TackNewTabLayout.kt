package com.lanyou.lib_base.widgets

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lanyou.lib_base.R

class TackNewTabLayout : TabLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private var mContext: Context? = null
    private fun init(context: Context, attrs: AttributeSet?) {
        this.mContext = context
    }

    var dimen_12sp = 12
    var dimen_13sp = 13
    var dimen_14sp = 14
    private var tab_texts: Array<TextView?>? = null
    private var num_texts: Array<TextView?>? = null
    fun setupWithMyViewPager(viewPager: ViewPager?, tabs: ArrayList<String?>) {
        setupWithViewPager(viewPager)
        tab_texts = arrayOfNulls(tabCount)
        num_texts = arrayOfNulls(tabCount)
        for (x in 0 until tabCount) {
            val view1 = inflate(context, R.layout.customtab_new_tack_item, null)
            tab_texts!![x] = view1.findViewById(R.id.tab_item_text)
            num_texts!![x] = view1.findViewById(R.id.rv_num)
            tab_texts!![x]?.text = tabs[x]
            num_texts!![x]?.visibility = GONE
            getTabAt(x)!!.customView = view1
            if (0 == x) {
                tab_texts!![x]?.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_14sp.toFloat())
                tab_texts!![x]?.setTextColor(Color.parseColor("#303133"))
            } else {
                tab_texts!![x]?.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_14sp.toFloat())
                tab_texts!![x]?.setTextColor(Color.parseColor("#909399"))
            }
        }
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                if (tab.customView != null) {
                    val text = tab.customView!!.findViewById<TextView>(R.id.tab_item_text)
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_14sp.toFloat())
                    text.setTextColor(Color.parseColor("#303133"))
                }
            }

            override fun onTabUnselected(tab: Tab) {
                if (tab.customView != null) {
                    val text = tab.customView!!.findViewById<TextView>(R.id.tab_item_text)
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_14sp.toFloat())
                    text.setTextColor(Color.parseColor("#909399"))
                }
            }

            override fun onTabReselected(tab: Tab) {}
        })
    }

    private fun setSelectText(index: Int) {
        if (tab_texts != null && tab_texts!!.isNotEmpty()) {
            for (x in tab_texts!!.indices) {
                if (index == x) {
                    tab_texts!![x]!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_13sp.toFloat())
                    tab_texts!![x]!!.setTextColor(Color.parseColor("#303133"))
                } else {
                    tab_texts!![x]!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen_12sp.toFloat())
                    tab_texts!![x]!!.setTextColor(Color.parseColor("#909399"))
                }
            }
        }
    }

    fun setNumTexts(index: Int, taskNum: String) {
        if (num_texts != null && num_texts!!.size > index) {
            if (!TextUtils.isEmpty(taskNum)) {
                val total = taskNum.toInt()
                if (total > 0) {
                    if (total > 99) {
                        num_texts!![index]!!.text = "99"
                    } else {
                        num_texts!![index]!!.text = taskNum
                    }
                    num_texts!![index]!!.visibility = VISIBLE
                    return
                }
            }
            num_texts!![index]!!.visibility = GONE
        }
    }
}