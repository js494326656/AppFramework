package com.jsware.swiperefresh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.jsware.R;
import com.jsware.swiperefresh.adapter.SwipeRefreshAdapter;
import com.jsware.swiperefresh.helper.SwipeRefreshAdapterHelper;
import com.utils.datahelper.RxCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SwipeRefreshFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SwipeRefreshFragment extends Fragment {
    @Bind(R.id.list_data)
    RecyclerView listData;
    @Bind(R.id.swipeRefreshLayout)
    PullRefreshLayout swipeRefreshLayout;

    ItemTouchHelper touchHelper;

    SwipeRefreshAdapter adapter;
    List<String> data = new ArrayList<>();

    public SwipeRefreshFragment() {
        // Required empty public constructor
    }

    public static SwipeRefreshFragment newInstance() {
        SwipeRefreshFragment fragment = new SwipeRefreshFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_refresh, container, false);
        ButterKnife.bind(this, view);
        initViews();
        testData();
        return view;
    }

    private void testData(){
        List<String> testArr = new ArrayList<>();
        for (int i=0;i<10;i++) {
            testArr.add("test" + i);
        }
        refreshUI(testArr);
    }

    private void initViews() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO: 2016/3/9 刷新数据
            RxCounter.counter(1, 0, 1, TimeUnit.MINUTES).doOnCompleted(() -> {
                swipeRefreshLayout.setRefreshing(false);
                testData();
            }).subscribe();
        });
        listData.setHasFixedSize(true);
        listData.setLayoutManager(new LinearLayoutManager(getActivity()));
        touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                data.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        touchHelper.attachToRecyclerView(listData);
    }

    private void refreshUI(List<String> resultData) {
        data.clear();
        data.addAll(resultData);
        if (adapter == null) {
            adapter = new SwipeRefreshAdapter<String>(getActivity(),R.layout.item_swipe_list,data) {
                @Override
                public void convert(SwipeRefreshAdapterHelper helper, String item) {
                    helper.setText(R.id.tv_name, item);
                }
            };
            listData.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
