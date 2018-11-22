package com.example.yk2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yk2.Fragment.Fragment_fen;
import com.example.yk2.Fragment.Fragment_gou;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private RadioButton bt_fen;
    private RadioButton bt_gou;
    private RadioGroup group;
    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        bt_fen = (RadioButton) findViewById(R.id.bt_fen);
        bt_gou = (RadioButton) findViewById(R.id.bt_gou);
        group = (RadioGroup) findViewById(R.id.group);

        getData();
    }

    private void getData() {
         list=new ArrayList<>();
        list.add(new Fragment_fen());
        list.add(new Fragment_gou());
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        group.check(R.id.bt_fen);
                        break;
                    case 1:
                        group.check(R.id.bt_gou);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bt_fen:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.bt_gou:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });
    }
}
