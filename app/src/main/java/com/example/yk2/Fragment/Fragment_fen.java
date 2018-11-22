package com.example.yk2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.yk2.Adapter.GvAdapter;
import com.example.yk2.Adapter.LvAdapter;
import com.example.yk2.Bean.Goods;
import com.example.yk2.Bean.News;
import com.example.yk2.OkUtils;
import com.example.yk2.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_fen extends Fragment {

    private ListView lv;
    private GridView gv;
    private ArrayList<News.DataBean> list;
    private String url="http://www.zhaoapi.cn/product/getCatagory";
    private String path="http://www.zhaoapi.cn/product/getProductCatagory";
    private LvAdapter la;
    private ArrayList<Goods.DataBean> li;
    private GvAdapter ga;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_fen, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv);
        getlv();
        gv = (GridView) view.findViewById(R.id.gv);


    }



    private void getlv() {
         list=new ArrayList<>();
        la=new LvAdapter(getActivity(),list);
        lv.setAdapter(la);
        OkUtils.getInstance().doGet(url, new OkUtils.OkHttpCallBack() {
            @Override
            public void success(String s) {
                News news = new Gson().fromJson(s, News.class);
                List<News.DataBean> data = news.getData();
                list.clear();
                list.addAll(data);
                la.notifyDataSetChanged();
            }

            @Override
            public void failed(Exception e) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = new HashMap<>();
                map.put("cid",position+"");
                OkUtils.getInstance().doPost(path, map, new OkUtils.OkHttpCallBack() {
                    @Override
                    public void success(String s) {
                        li=new ArrayList<>();
                        ga=new GvAdapter(getActivity(),li);
                        Goods goods = new Gson().fromJson(s, Goods.class);
                        List<Goods.DataBean> data = goods.getData();

                        li.clear();
                        li.addAll(data);
                        ga.notifyDataSetChanged();
                          gv.setAdapter(ga);

                    }

                    @Override
                    public void failed(Exception e) {

                    }
                });
            }
        });
    }
}
