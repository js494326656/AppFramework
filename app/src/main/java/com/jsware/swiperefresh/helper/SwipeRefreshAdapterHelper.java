package com.jsware.swiperefresh.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jsware.swiperefresh.adapter.SwipeRefreshAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by 1 on 2016/3/9.
 */
public class SwipeRefreshAdapterHelper {

    /** Views indexed with their IDs */
    private SparseArray<View> views = new SparseArray<>();

    private View convertView;
    private Context context;

    public SwipeRefreshAdapterHelper(Context context,View convertView){
        this.context = context;
        this.convertView = convertView;
    }

    public <T extends View> T getView(int viewId)
    {
        return retrieveView(viewId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T retrieveView(int viewId)
    {
        View view = views.get(viewId);
        if (view == null)
        {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public SwipeRefreshAdapterHelper setText(int viewId, String value)
    {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    public SwipeRefreshAdapterHelper setImageResource(int viewId, int imageResId)
    {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public SwipeRefreshAdapterHelper setBackgroundColor(int viewId, int color)
    {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public SwipeRefreshAdapterHelper setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public SwipeRefreshAdapterHelper setTextColor(int viewId, int textColor)
    {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public SwipeRefreshAdapterHelper setTextColorRes(int viewId, int textColorRes)
    {
        TextView view = retrieveView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    public SwipeRefreshAdapterHelper setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public SwipeRefreshAdapterHelper setImageUrl(int viewId, String imageUrl)
    {
        ImageView view = retrieveView(viewId);
        Picasso.with(context).load(imageUrl).into(view);
        return this;
    }

    public SwipeRefreshAdapterHelper setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public SwipeRefreshAdapterHelper setVisible(int viewId, boolean visible)
    {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public SwipeRefreshAdapterHelper setProgress(int viewId, int progress)
    {
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return this;
    }

    public SwipeRefreshAdapterHelper setProgress(int viewId, int progress, int max)
    {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public SwipeRefreshAdapterHelper setMax(int viewId, int max)
    {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    public SwipeRefreshAdapterHelper setTag(int viewId, Object tag)
    {
        View view = retrieveView(viewId);
        view.setTag(tag);
        return this;
    }

    public SwipeRefreshAdapterHelper setTag(int viewId, int key, Object tag)
    {
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public SwipeRefreshAdapterHelper setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    public SwipeRefreshAdapterHelper setOnClickListener(int viewId,
                                                View.OnClickListener listener)
    {
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public SwipeRefreshAdapterHelper setOnTouchListener(int viewId,
                                                View.OnTouchListener listener)
    {
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public SwipeRefreshAdapterHelper setOnLongClickListener(int viewId,
                                                    View.OnLongClickListener listener)
    {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}
