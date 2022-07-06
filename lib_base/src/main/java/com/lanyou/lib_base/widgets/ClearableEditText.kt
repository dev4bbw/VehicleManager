package com.lanyou.lib_base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.lanyou.lib_base.R

/***
 * 带删除按钮的Edittext
 *
 * @author zhaohaibin
 */
class ClearableEditText(context: Context?, attrs: AttributeSet?) : EditText(context, attrs),
    TextWatcher {
    /**
     * 右侧位图
     */
    private val rightDrawable: Drawable?
    private val dRightSideLength: Int

    /**
     * 该EditText的验证器
     */
    private var contentValidator: EditTextContentValidator? = null
    fun setContentValidator(contentValidator: EditTextContentValidator?) {
        this.contentValidator = contentValidator
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && hasFocus()
            && rightDrawable != null
        ) {
            val x: Int = event.x.toInt()
            val y: Int = event.y.toInt()
            if (x >= this.width - (dRightSideLength
                        + this.paddingRight + X_REVISE_PIXS)
                && x <= this.width - (this.paddingRight - X_REVISE_PIXS)
            ) {
                // disDrawable();
                this.setText("")
                event.setAction(MotionEvent.ACTION_CANCEL) // use this to
            }
        }
        return super.onTouchEvent(event)
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!hasFocus()) {
            return
        }
        val afterChangeText = s.toString()
        if (!TextUtils.isEmpty(afterChangeText)) {
            if (!TextUtils.isEmpty(strNumber)) {
                val lastContent = afterChangeText[afterChangeText.length - 1]
                val strLastContent = lastContent.toString()
                if (!TextUtils.isEmpty(strLastContent) && strLastContent != strNumber) {
                    var content = afterChangeText + strNumber
                    if (content.contains(strNumber!!)) {
                        content = content.replace(strNumber!!, "")
                        content = content + strNumber
                        setText(content)
                        setSelection(content.length)
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(afterChangeText)) {
            showDrawable()
        } else {
            disDrawable()
        }
    }

    private var strNumber: String? = null

    /**
     * 设置单位 ¥，米，等
     *
     * @param strNumber
     */
    fun setOnCaseNumber(strNumber: String?) {
        this.strNumber = strNumber
    }

    override fun afterTextChanged(s: Editable) {}

    /***
     * 隐藏删除图片，防止重写onfocus引起的图片不隐藏问题
     */
    fun disDrawable() {
        this.setCompoundDrawables(null, null, null, null)
        // rightDrawable = null;
    }

    /***
     * 隐藏删除图片，防止重写onfocus引起的图片不隐藏问题
     */
    fun showDrawable() {
        this.setCompoundDrawablesWithIntrinsicBounds(
            null, null, rightDrawable,
            null
        )
    }

    /**
     * 该EditText的验证器
     *
     * @author zhaohaibin
     */
    interface EditTextContentValidator {
        fun validate(targetView: ClearableEditText?, str: String?): Boolean
        fun onValidateRight(targetView: ClearableEditText?)
        fun onValidateError(targetView: ClearableEditText?)
    }

    companion object {
        // 增加点击范围
        private const val X_REVISE_PIXS = 5
    }

    /**
     * 布局构造函数
     *
     * @param context
     * @param attrs
     */
    init {
        this.addTextChangedListener(this)
        rightDrawable = resources.getDrawable(R.drawable.selector_edit_clear)
        dRightSideLength = rightDrawable.getIntrinsicWidth()
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!TextUtils.isEmpty(getText().toString())) {
                    showDrawable()
                }
            } else {
                disDrawable()
                if (contentValidator != null) {
                    if (contentValidator!!.validate(
                            this@ClearableEditText,
                            getText().toString()
                        )
                    ) {
                        contentValidator!!
                            .onValidateRight(this@ClearableEditText)
                    } else {
                        contentValidator!!
                            .onValidateError(this@ClearableEditText)
                    }
                }
            }
        }
    }
}