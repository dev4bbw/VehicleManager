package com.lanyou.lib_base.widgets

import android.content.Context
import android.graphics.*
import kotlin.jvm.JvmOverloads
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec
import android.widget.TextView
import androidx.annotation.Nullable
import com.lanyou.lib_base.R

/**
 * Creatd by cretin on 2017/7/24.
 */
class ProgressView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //记录View的宽度
    private var mViewWidth = 0

    //记录Progress的宽度
    private var mProgressWidth = 0

    //记录View的高度
    private var mViewHeight = 0
    private var mContext: Context? = null
    private var mScreenScale = 0f

    //进度条的头部资源图
    private var mProgressBitmap: Bitmap? = null

    //进度条背景资源图
    private var mProgressTipsBitmap: Bitmap? = null

    //画笔
    private val mPaint = Paint()

    //计算每一步的步距
    private var mStepLength = 0f

    //当前进度
    private var mProgress = 0

    //设置进度
    fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    //初始化操作
    private fun init(context: Context) {
        mContext = context
        mScreenScale = getScreenScale(context)

        //引入本地资源图片
        mProgressBitmap = BitmapFactory.decodeResource(resources, R.drawable.progress)
        mProgressTipsBitmap = BitmapFactory.decodeResource(resources, R.drawable.progress_tips)
    }

    override fun onDraw(canvas: Canvas) {
        //首先绘制一条背景图
        val mTopDestRect = RectF(
            18 * mScreenScale, (mViewHeight.toFloat() / 40 * 28).toInt()
                .toFloat(),
            mProgressWidth + 17 * mScreenScale, (mViewHeight.toFloat() / 40 * 38).toInt().toFloat()
        )
        mPaint.color = Color.parseColor("#d9d9d9")
        canvas.drawRoundRect(mTopDestRect, 100f, 100f, mPaint)

        //绘制进度条的头部
        canvas.drawBitmap(
            mProgressBitmap!!,
            18 * mScreenScale + mStepLength * mProgress,
            (mViewHeight.toFloat() / 40 * 26).toInt().toFloat(),
            mPaint
        )
        mPaint.color = Color.parseColor("#7ec059")
        mPaint.textSize = 12 * mScreenScale
        //绘制进度值背景
        canvas.drawBitmap(
            mProgressTipsBitmap!!,
            18 * mScreenScale + mStepLength * mProgress - 10 * mScreenScale,
            0f,
            mPaint
        )

        //绘制进度值
        canvas.drawText(
            "$mProgress%",
            18 * mScreenScale + mStepLength * mProgress,
            14 * mScreenScale,
            mPaint
        )

        //绘制走过的进度条的背景
        val mRect = RectF(
            18 * mScreenScale,
            (mViewHeight.toFloat() / 40 * 28).toInt()
                .toFloat(),
            18 * mScreenScale + mStepLength * mProgress + 10,
            (mViewHeight.toFloat() / 40 * 38).toInt()
                .toFloat()
        )
        canvas.drawRoundRect(mRect, 100f, 100f, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = (280 * mScreenScale).toInt()
        val desiredHeight = (40 * mScreenScale).toInt()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        mViewWidth = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height
        mViewHeight = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(mViewWidth, mViewHeight)
        mProgressWidth = (mViewWidth.toFloat() / 280 * 244).toInt()
        //        mStepLength = (244 * mScreenScale - 17 * mScreenScale) / 100;
        mStepLength = (mProgressWidth / 100).toFloat()
        Log.e("w + h:", "$mViewWidth  $mViewHeight   $mStepLength  $mProgressWidth")
    }

    //获取屏幕分辨率比例
    private fun getScreenScale(context: Context): Float {
        val tv = TextView(context)
        tv.textSize = 1f
        return tv.textSize
    }

    init {
        init(context)
    }
}