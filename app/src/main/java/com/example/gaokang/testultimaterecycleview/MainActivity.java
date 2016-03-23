package com.example.gaokang.testultimaterecycleview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UltimateRecyclerView recycleview;
    private List<String> titles;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        titles = new ArrayList<>();
        adapter = new MyAdapter(this, titles);
        recycleview.setAdapter(adapter);
        for (int i = 0; i < 20; i++) {
            titles.add("this is title:" + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        recycleview = (UltimateRecyclerView) findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.enableDefaultSwipeRefresh(true);
//        recycleview.enable

        recycleview.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "触发了下拉刷新", Toast.LENGTH_SHORT).show();
            }
        });

        recycleview.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                Toast.makeText(MainActivity.this, "触发了加载更多", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
