package com.lanyou.lib_base.widgets

import android.content.Context
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.CheckedTextView
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import com.lanyou.lib_base.R
import android.util.TypedValue
import android.text.method.DigitsKeyListener
import android.view.View.OnFocusChangeListener
import android.text.TextWatcher
import android.text.Editable
import android.text.method.PasswordTransformationMethod
import android.text.method.HideReturnsTransformationMethod
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class PwdVisibleLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs),
    View.OnClickListener {
    var editText: EditText? = null
        private set
    private var ctv: CheckedTextView? = null
    private var img_clear: ImageView? = null
    private val textSize: Float
    private val textColor: Int
    private val hintColor: Int
    private val checkMark: Drawable?
    private val hint: String?
    private val text: String?
    private val digits: String?
    private fun initView() {
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.layout_pwd_visible, this) as LinearLayout
        editText = layout.findViewById<View>(R.id.et_password) as EditText
        ctv = layout.findViewById<View>(R.id.ctv) as CheckedTextView
        img_clear = layout.findViewById<View>(R.id.img_clear) as ImageView
        //默认密码是密码样式并且由于进入页面未获得焦距，小眼睛应隐藏
        hidePassword()
        hideDrawable()
        if (textSize != -1f) {
            editText!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
        if (textColor != -1) {
            editText!!.setTextColor(textColor)
        }
        if (!TextUtils.isEmpty(hint)) {
            editText!!.hint = hint
        }
        if (!TextUtils.isEmpty(text)) {
            editText!!.setText(text)
        }
        if (hintColor != -1) {
            editText!!.setHintTextColor(hintColor)
        }
        if (checkMark != null) {
            ctv!!.checkMarkDrawable = checkMark
        }
        if (digits != null) {
            editText!!.setOnKeyListener(DigitsKeyListener.getInstance(digits) as OnKeyListener)
        }
        ctv!!.setOnClickListener(this)
        img_clear!!.setOnClickListener(this)
        editText!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (!TextUtils.isEmpty(pwd)) {
                    //如果获取焦距并且密码不为空，则显示小眼睛和清除图片
                    showDrawable()
                }
            } else {
                if (ctv!!.isChecked) {
                    ctv!!.toggle()
                    hidePassword()
                }
                hideDrawable()
            }
        }
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                if (!hasFocus()) {
                    return
                }
                val afterChangeText = s.toString()
                if (!TextUtils.isEmpty(afterChangeText)) {
                    showDrawable()
                } else {
                    hideDrawable()
                }
            }
        })
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        val i = v.id
        if (i == R.id.ctv) {
            ctv!!.toggle()
            if (!ctv!!.isChecked) {
                hidePassword()
            } else {
                showPassword()
            }
            editText!!.setSelection(editText!!.text.length)
        } else if (i == R.id.img_clear) {
            editText!!.setText("")
            hidePassword()
            hideDrawable()
            if (ctv!!.isChecked) {
                ctv!!.toggle()
            }
        }
        editText!!.requestFocus()
    }

    /**
     * 隐藏密码
     */
    private fun hidePassword() {
        editText!!.transformationMethod = PasswordTransformationMethod.getInstance()
        editText!!.postInvalidate()
        //		et_password.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 显示密码
     */
    private fun showPassword() {
        editText!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
        editText!!.postInvalidate()
        //		et_password.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    /**
     * 隐藏小眼睛和清除图片
     */
    private fun hideDrawable() {
        img_clear!!.visibility = GONE
        ctv!!.visibility = GONE
    }

    /**
     * 显示小眼睛和清除图片
     */
    private fun showDrawable() {
        img_clear!!.visibility = VISIBLE
        ctv!!.visibility = VISIBLE
    }

    fun setText(pasword: String?) {
        editText!!.setText(pasword)
    }

    val pwd: String
        get() = editText!!.text.toString()

    //	private boolean mbDisplayFlg=false;
    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.pwdVisible
        )
        textSize = a.getDimension(R.styleable.pwdVisible_textSize, -1f)
        hintColor = a.getColor(R.styleable.pwdVisible_textColorHint, -1)
        textColor = a.getColor(R.styleable.pwdVisible_textColor, -1)
        hint = a.getString(R.styleable.pwdVisible_hint)
        text = a.getString(R.styleable.pwdVisible_text)
        checkMark = a.getDrawable(R.styleable.pwdVisible_checkMark)
        digits = a.getString(R.styleable.pwdVisible_digits)
        a.recycle()
        initView()
    }
}