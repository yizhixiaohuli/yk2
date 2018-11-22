package com.example.yk2.Adapter;

import android.support.v4.app.FragmentActivity;
import android.text.BoringLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yk2.AddView;
import com.example.yk2.Bean.User;
import com.example.yk2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2018/11/22
 * author:别的小朋友(别的小朋友)
 * function:
 */
public class Madapter extends BaseExpandableListAdapter{
    List<User.DataBean> data;
    public Madapter(List<User.DataBean> data) {
            this.data=data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView==null){
            groupHolder=new GroupHolder();
            convertView = View.inflate(parent.getContext(), R.layout.group, null);
           groupHolder.ck_group=convertView.findViewById(R.id.ck_group);
            groupHolder.tv_group=convertView.findViewById(R.id.tv_group);
            convertView.setTag(groupHolder);

        }else{
            groupHolder= (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_group.setText(data.get(groupPosition).getSellerName());

        //商家是否选中
        boolean currentShopChecked = isCurrentShopChecked(groupPosition);
        groupHolder.ck_group.setChecked(currentShopChecked);
        groupHolder.ck_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.ischeckShop(groupPosition);
                }
            }
        });
        return convertView;
    }

    class GroupHolder{
       TextView tv_group;
       CheckBox ck_group;
    }
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView==null){
            childHolder=new ChildHolder();
            convertView = View.inflate(parent.getContext(), R.layout.child, null);
            childHolder.tv_title=convertView.findViewById(R.id.tv_title);
            childHolder.tv_price=convertView.findViewById(R.id.tv_price);
            childHolder.iv_child=convertView.findViewById(R.id.iv_child);
            childHolder.ck_child=convertView.findViewById(R.id.ck_child);
            childHolder.addview=convertView.findViewById(R.id.addview);
            convertView.setTag(childHolder);

        }else{
            childHolder= (ChildHolder) convertView.getTag();
        }
        childHolder.tv_title.setText(data.get(groupPosition).getList().get(childPosition).getTitle());
        String imgages[]=data.get(groupPosition).getList().get(childPosition).getImages().split("!");
        ImageLoader.getInstance().displayImage(imgages[0],childHolder.iv_child);
        childHolder.tv_price.setText("￥"+data.get(groupPosition).getList().get(childPosition).getPrice());

        //商品是否选中
        childHolder.ck_child.setChecked(data.get(groupPosition).getList().get(childPosition).getSelected()==1);
        childHolder.ck_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.ischeckGoods(groupPosition,childPosition);
                }
            }
        });

        //选中的商品数量
        childHolder.addview.setNumber(data.get(groupPosition).getList().get(childPosition).getNum());
        childHolder.addview.setAddViewNumber(new AddView.AddViewNumber() {
            @Override
            public void viewnumber(int number) {
                if (mOnClickListener!=null){
                    mOnClickListener.ischeckNum(groupPosition,childPosition,number);
                }
            }
        });
        return convertView;
    }
    class ChildHolder{
        CheckBox ck_child;
        TextView tv_price;
        TextView tv_title;
        ImageView iv_child;
        AddView addview;
    }


                //商家是否全部选中
    public boolean isCurrentShopChecked(int groupPosition){
        List<User.DataBean.ListBean> list = data.get(groupPosition).getList();
        for (User.DataBean.ListBean listBean : list) {
            if (listBean.getSelected()==0){
                return false;
            }
        }
        return true;
    }

                //商品是否全部选中  全选按钮
    public boolean isCurrentGoodsChecked(){
        for(int i=0;i<data.size();i++){
            User.DataBean dataBean = data.get(i);
            List<User.DataBean.ListBean> list = dataBean.getList();
            for (int j=0;j<list.size();j++){
                if (list.get(j).getSelected()==0){
                    return false;
                }
            }
        }
            return true;
    }

    //选中 商品数量

    public int isCheckedeGoodsNum(){
        int allnum=0;
        for (int i=0;i<data.size();i++){
            List<User.DataBean.ListBean> list = data.get(i).getList();
            for (int j=0;j<list.size();j++){
                if (list.get(j).getSelected()==1){
                    int num=list.get(j).getNum();
                    allnum+=num;
                }
            }
        }
        return allnum;
    }

    //商品价格 选中的

    public float isCheckedGoodsPrice(){
        float allprice=0;
        for (int i=0;i<data.size();i++){
            List<User.DataBean.ListBean> list = data.get(i).getList();
            for (int j=0;j<list.size();j++){
                if (list.get(j).getSelected()==1){
                    int num=list.get(j).getNum();
                    float price=list.get(j).getPrice();
                    allprice+=num*price;
                }
            }
        }
        return allprice;
    }
    //改变商家状态
    public void changeShop(int groupPosition, boolean ischecked){
        List<User.DataBean.ListBean> list = data.get(groupPosition).getList();
        for (int i=0;i<list.size();i++){
            list.get(i).setSelected(ischecked?1:0);
        }
    }
    //改变商品状态
    public void changGoods(int groupPosition,int childPosition){
        User.DataBean.ListBean listBean = data.get(groupPosition).getList().get(childPosition);
        listBean.setSelected(listBean.getSelected()==0?1:0);
    }


    //点击全选时改变所有商品的状态

    public void changeAllGoods(boolean isChecked){
        for (int i=0;i<data.size();i++){
            List<User.DataBean.ListBean> list = data.get(i).getList();
            for (int j=0;j<list.size();j++){
                list.get(j).setSelected(isChecked?1:0);
            }
        }
    }
        //加减器的数量
    public void changeGoodsNum(int groupPosition,int childPosition,int number){
        data.get(groupPosition).getList().get(childPosition).setNum(number);
    }
    //接口回调
    public interface OnClickListener{
        //商家
        void ischeckShop(int groupPosition);
        //商品
        void ischeckGoods(int groupPosition,int childPosition);
        //加减
        void ischeckNum(int groupPosition,int childPosition,int number);
        }

    public OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }




















    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
