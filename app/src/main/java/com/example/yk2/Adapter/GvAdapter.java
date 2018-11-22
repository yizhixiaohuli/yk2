package com.example.yk2.Adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yk2.Bean.Goods;
import com.example.yk2.Bean.News;
import com.example.yk2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * date:2018/11/22
 * author:别的小朋友(别的小朋友)
 * function:
 */
public class GvAdapter extends BaseAdapter {
    FragmentActivity activity;
    ArrayList<Goods.DataBean> li;


    public GvAdapter(FragmentActivity activity, ArrayList<Goods.DataBean> li) {
        this.activity=activity;
        this.li=li;
    }


    @Override
    public int getCount() {
        return li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
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
            convertView=View.inflate(activity, R.layout.grid,null);
            viewHold.tv_grid=convertView.findViewById(R.id.tv_grid);
            viewHold.iv_grid=convertView.findViewById(R.id.iv_grid);
            convertView.setTag(viewHold);
        }else{
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.tv_grid.setText(li.get(position).getName());
        ImageLoader.getInstance().displayImage(li.get(position).getList().get(position).getIcon(),viewHold.iv_grid);
        return convertView;
    }
    class ViewHold{
        TextView tv_grid;
        ImageView iv_grid;
    }
}
