package com.example.yk2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.yk2.Adapter.Madapter;
import com.example.yk2.Bean.User;
import com.example.yk2.OkUtils;
import com.example.yk2.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_gou extends Fragment implements View.OnClickListener {
    private String url = "http://www.zhaoapi.cn/product/getCarts";
    private ExpandableListView el;
    private CheckBox ck_all;
    private ArrayList<User.DataBean> list;
    private Madapter ma;
    private TextView tv_allprice;
    private Button tv_allnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_gou, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        el = (ExpandableListView) view.findViewById(R.id.el);
        getData();
        ck_all = (CheckBox) view.findViewById(R.id.ck_all);
        ck_all.setOnClickListener(this);
        tv_allprice = (TextView) view.findViewById(R.id.tv_allprice);
        tv_allprice.setOnClickListener(this);
        tv_allnum = (Button) view.findViewById(R.id.tv_allnum);
        tv_allnum.setOnClickListener(this);
    }

    private void getData() {
        list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", "71");
        OkUtils.getInstance().doPost(url, map, new OkUtils.OkHttpCallBack() {
            @Override
            public void success(String s) {
                User user = new Gson().fromJson(s, User.class);
                List<User.DataBean> data = user.getData();
                ma = new Madapter(data);
                el.setAdapter(ma);
                el.setGroupIndicator(null);
                ma.setOnClickListener(new Madapter.OnClickListener() {
                    @Override
                    public void ischeckShop(int groupPosition) {
                        boolean currentShopChecked = ma.isCurrentShopChecked(groupPosition);
                        ma.changeShop(groupPosition,!currentShopChecked);
                        ma.notifyDataSetChanged();
                        refreshAll();
                    }

                    @Override
                    public void ischeckGoods(int groupPosition, int childPosition) {
                        ma.changGoods(groupPosition,childPosition);
                        ma.notifyDataSetChanged();
                        refreshAll();
                    }

                    @Override
                    public void ischeckNum(int groupPosition, int childPosition, int number) {
                        ma.changeGoodsNum(groupPosition,childPosition,number);
                        ma.notifyDataSetChanged();
                        refreshAll();
                    }
                });
                for (int i=0;i<data.size();i++){
                    el.expandGroup(i);
                }
            }

            @Override
            public void failed(Exception e) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ck_all:
                boolean currentGoodsChecked = ma.isCurrentGoodsChecked();
                ma.changeAllGoods(!currentGoodsChecked);
                ma.notifyDataSetChanged();
                refreshAll();
                break;

        }
    }
    public void refreshAll(){
        //全选
        boolean currentGoodsChecked = ma.isCurrentGoodsChecked();
        ck_all.setChecked(currentGoodsChecked);
        //价格
        float checkedGoodsPrice = ma.isCheckedGoodsPrice();
        tv_allprice.setText("总计"+checkedGoodsPrice);
        //数量
        int checkedeGoodsNum = ma.isCheckedeGoodsNum();
        tv_allnum.setText("去结算"+checkedeGoodsNum);
    }
}
