package com.example.yk2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * date:2018/11/22
 * author:别的小朋友(别的小朋友)
 * function:
 */
public class AddView extends LinearLayout implements View.OnClickListener {
    private TextView tv_jian;
    private TextView tv_shu;
    private TextView tv_jia;
    private int number = 1;

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.addview, this);
        tv_jia = view.findViewById(R.id.tv_jia);
        tv_jian = view.findViewById(R.id.tv_jian);
        tv_shu = view.findViewById(R.id.tv_shu);

        tv_jia.setOnClickListener(this);
        tv_jian.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jian:
                if (number > 1) {
                    number--;
                    tv_shu.setText(number + "");

                }
                if (mAddViewNumber!=null){
                    mAddViewNumber.viewnumber(number);
                }
                break;
            case R.id.tv_jia:
                number++;
                tv_shu.setText(number + "");
                if (mAddViewNumber!=null){
                    mAddViewNumber.viewnumber(number);
                }

                break;
        }

    }

    public void setNumber(int number) {
        this.number = number;
        tv_shu.setText(number + "");
    }
    public interface AddViewNumber{
        void viewnumber(int number);
    }
    public AddViewNumber mAddViewNumber;

    public void setAddViewNumber(AddViewNumber addViewNumber) {
        mAddViewNumber = addViewNumber;
    }
}
