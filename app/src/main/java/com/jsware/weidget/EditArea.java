package com.jsware.weidget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsware.R;

import java.lang.reflect.Field;

/**
 * Created by 1 on 2016/3/16.
 */
public class EditArea extends RelativeLayout {
    EditText editContent;
    TextView tvDes;
    Context mContext;

    int maxLength = -1;
    String hint = "";

    public EditArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_edit_area,this,true);

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

        initViews();
    }

    private void initViews() {
        editContent = (EditText) findViewById(R.id.edit_content);
        tvDes = (TextView) findViewById(R.id.tv_descirption);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (maxLength != -1) {
                    tvDes.setText(s.length()+"/"+maxLength);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (maxLength != -1) {
            InputFilter[] lengthFilters = new InputFilter[]{new InputFilter.LengthFilter(maxLength)};
            editContent.setFilters(lengthFilters);
            tvDes.setText("0/"+maxLength);
        }
        if (!TextUtils.isEmpty(hint)) {
            editContent.setHint(hint);
        }
    }

    public String getEditInput() {
        return editContent.getText().toString();
    }

}
