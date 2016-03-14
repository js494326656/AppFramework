package com.jsware.weidget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.jsware.R;

import java.lang.reflect.Field;

public class CleanableEditText extends RelativeLayout {
    EditText editContent;
    DrawRightTextView tvDes;
    Context mContext;

    int drawLeftResId = 0,drawRightResId = 0,textColorResId = 0;
    int inputType = EditorInfo.IME_NULL;
    int maxLength = -1;
    String hint = "";

    private Drawable mLeftDrawable,mRightDrawable;
    private boolean isHasFocus;

    public CleanableEditText(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_clean_edit,this,true);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_clean_edit,this,true);
        initParam(context,attrs);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_clean_edit,this,true);
        initParam(context,attrs);
        init();
    }

    private void initParam(Context context,AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CleanEdit);
        drawLeftResId = a.getResourceId(R.styleable.CleanEdit_drawLeft, 0);
        drawRightResId = a.getResourceId(R.styleable.CleanEdit_drawRight, 0);
        textColorResId = a.getResourceId(R.styleable.CleanEdit_textColor, 0);
        a.recycle();

        Resources.Theme theme = context.getTheme();
        TypedArray defType = null;
        try {
            // TypedArray
            Class<?> styleableCls = Class.forName("com.android.internal.R$styleable");
            Object styleableInstance = styleableCls.newInstance();
            Field field = styleableCls.getField("TextView");
            int[] styleableValues = (int[]) field.get(styleableInstance);
            Class<?> styleableAttrCls = Class.forName("com.android.internal.R$attr");
            Field defStyleAttrField = styleableAttrCls.getField("textViewStyle");
            Object styleAttrInstance = styleableAttrCls.newInstance();
            int defStyleAttrValue = (int) defStyleAttrField.get(styleAttrInstance);
            defType = theme.obtainStyledAttributes(attrs,styleableValues,defStyleAttrValue,0);
            // inputType
            Field inputField = styleableCls.getField("TextView_inputType");
            int inputValue = (int) inputField.get(styleableCls);
            inputType = defType.getInt(inputValue, EditorInfo.TYPE_NULL);
            // hint
            Field hintField = styleableCls.getField("TextView_hint");
            int hintValue = (int) hintField.get(styleableCls);
            hint = defType.getString(hintValue);
            // maxLength
            Field maxLengthField = styleableCls.getField("TextView_maxLength");
            int maxLengthValue = (int) maxLengthField.get(styleableCls);
            maxLength = defType.getInt(maxLengthValue, -1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        defType.recycle();
    }

    private void init() {
        editContent = (EditText) findViewById(R.id.edit_content);
        tvDes = (DrawRightTextView) findViewById(R.id.tv_descirption);
        if (drawLeftResId != 0) {
            mLeftDrawable = mContext.getResources().getDrawable(drawLeftResId);
            mLeftDrawable.setBounds(0, 0, mLeftDrawable.getMinimumWidth(), mLeftDrawable.getMinimumHeight());
            editContent.setCompoundDrawables(mLeftDrawable, null, null, null);
            editContent.setCompoundDrawablePadding(10);
        }
        if (drawRightResId != 0) {
            mRightDrawable = mContext.getResources().getDrawable(drawRightResId);
            mRightDrawable.setBounds(0, 0, mRightDrawable.getMinimumWidth(), mRightDrawable.getMinimumHeight());
            tvDes.setCompoundDrawables(null, null, mRightDrawable, null);
            tvDes.setCompoundDrawablePadding(10);
        }
        editContent.setInputType(inputType);
        editContent.setHint(hint);
        InputFilter[] lengthFilters = new InputFilter[]{new InputFilter.LengthFilter(maxLength)};
        editContent.setFilters(lengthFilters);
        editContent.setOnFocusChangeListener(new FocusChangeListenerImpl());
        editContent.addTextChangedListener(new TextWatcherImpl());
        tvDes.setOnRightClickListener(new DrawRightTextView.OnRightClickListener() {
            @Override
            public void onRightBtnClick() {
                editContent.setText("");
            }
        });
        setClearDrawableVisible(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setClearDrawableVisible(true);
        } else {
            setClearDrawableVisible(false);
        }
    }

    private class FocusChangeListenerImpl implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = !TextUtils.isEmpty(editContent.getText().toString().trim());
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tvDes.dispatchTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            if (isHasFocus) {
                boolean isVisible = !TextUtils.isEmpty(editContent.getText().toString().trim());
                setClearDrawableVisible(isVisible);
                if (dynamicCheckListener != null) {
                    dynamicCheckListener.onInputChanged(s.toString());
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }

    protected void setClearDrawableVisible(boolean isVisible) {
        if (isEnabled()) {
            if (isVisible) {
                tvDes.setVisibility(VISIBLE);
            } else {
                tvDes.setVisibility(GONE);
            }
        } else {
            tvDes.setVisibility(GONE);
        }
    }

    public void setPrompt(String promptMsg) {
        tvDes.setText(promptMsg);
    }

    public Editable getText(){
        return editContent.getText();
    }

    public void setInputType(int inputType){
        editContent.setInputType(inputType);
    }

    public CharSequence getHint(){
        return editContent.getHint();
    }

    private DynamicCheckListener dynamicCheckListener = null;

    public void setDynamicCheckListener(DynamicCheckListener dynamicCheckListener) {
        this.dynamicCheckListener = dynamicCheckListener;
    }

    public interface DynamicCheckListener{
        void onInputChanged(String text);
    }
}