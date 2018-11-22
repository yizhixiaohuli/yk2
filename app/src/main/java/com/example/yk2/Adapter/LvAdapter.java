package com.example.yk2.Adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yk2.Bean.News;
import com.example.yk2.Bean.User;
import com.example.yk2.R;

import java.util.ArrayList;

/**
 * date:2018/11/22
 * author:别的小朋友(别的小朋友)
 * function:
 */
public class LvAdapter extends BaseAdapter{
    FragmentActivity activity;
    ArrayList<News.DataBean> list;


    public LvAdapter(FragmentActivity activity, ArrayList<News.DataBean> list) {
        this.activity=activity;
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=View.inflate(activity, R.layout.list,null);
            viewHold.tv=convertView.findViewById(R.id.tv);
            convertView.setTag(viewHold);
        }else{
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.tv.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHold{
        TextView tv;
    }
}
