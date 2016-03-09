package com.jsware.swiperefresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsware.R;
import com.jsware.swiperefresh.helper.SwipeRefreshAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 1 on 2016/3/9.
 */
public abstract class SwipeRefreshAdapter<T> extends RecyclerView.Adapter {

    List<T> data = new ArrayList<>();
    Context context = null;
    int resId = 0;

    public SwipeRefreshAdapter(Context context,int layoutId,List<T> data){
        this.data = data;
        this.context = context;
        resId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ItemViewHolder(view,new SwipeRefreshAdapterHelper(context,view));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert(((ItemViewHolder)holder).helper,data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    abstract public void convert(SwipeRefreshAdapterHelper helper,T item);

    class ItemViewHolder extends RecyclerView.ViewHolder{
        SwipeRefreshAdapterHelper helper;
        View root = null;
        public ItemViewHolder(View itemView,SwipeRefreshAdapterHelper helper) {
            super(itemView);
            root = itemView;
            this.helper = helper;
        }
    }
}
