package com.example.gaokang.testultimaterecycleview;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REFRESH = 0;
    public static final int LOADMORE = 1;

    private int count = 0;
    private UltimateRecyclerView recycleview;
    private List<String> titles;
    private MyAdapter adapter;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    recyclerView_Refresh();
                    recycleview.setRefreshing(false);
                    break;
                case LOADMORE:
                    recyclerView_LoadMore();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        titles = new ArrayList<>();
        for (count = 0; count < 20; count++) {
            titles.add("this is title:" + count);
        }
        adapter = new MyAdapter(this, titles);
        recycleview.setAdapter(adapter);
//        adapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null, false));
    }

    private void initView() {
        recycleview = (UltimateRecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        /*把下面这句代码注释之后,没有loadMoreView,但是可以正常的触发下拉刷新和上拉加载更多
        如果有这句代码的话,在打开app后无法进行上拉加载更多的回调,但是如果下拉刷新之后就可以上拉加载更多了.此时loadMoreView也是显示的*/
        recycleview.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null, false));

        recycleview.reenableLoadmore();
        recycleview.enableDefaultSwipeRefresh(true);

        Log.e("loadmore", "当前recycleview上拉加载更多的状态为:" + recycleview.isLoadMoreEnabled());
        //刷新监听
        recycleview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("onRefresh", "触发了下拉刷新");
                Log.e("loadmore", "当前recycleview上拉加载更多的状态为:" + recycleview.isLoadMoreEnabled());
                handler.sendEmptyMessageDelayed(REFRESH, 3000);
            }
        });

        //加载更多监听
        recycleview.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                Log.e("loadmore", "触发了上拉加载更多");
                handler.sendEmptyMessageDelayed(LOADMORE, 1000);
            }
        });

    }


    //刷新操作
    private void recyclerView_Refresh() {
        titles.clear();
        for (count = 0; count < 20; count++) {
            titles.add("this is a new title after refresh:" + count);
        }
        adapter.notifyDataSetChanged();
    }

    //加载更多操作
    private void recyclerView_LoadMore() {
        int max_num = count + 20;
        for (; count < max_num; count++) {
            titles.add("this is a new title after load more:" + count);
        }
        adapter.notifyDataSetChanged();
    }

}
