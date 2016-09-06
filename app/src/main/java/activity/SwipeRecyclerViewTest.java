package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.RecycleViewAdapter;
import lly.com.swiperecyclerview.R;
import util.DividerItemDecoration;
import wight.SwipeRecyclerView;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SwipeRecyclerViewTest extends Activity{

    private SwipeRecyclerView swipeRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiperecycleviewtest);
        swipeRecyclerView = (SwipeRecyclerView)findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout = swipeRecyclerView.getSwipeRefreshLayout();
        recyclerView = swipeRecyclerView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        initData();
        recycleViewAdapter = new RecycleViewAdapter(this,list);
        recyclerView.setAdapter(recycleViewAdapter);
        swipeRecyclerView.setOnSwipeRecyclerViewListener(new SwipeRecyclerView.OnSwipeRecyclerViewListener() {
            @Override
            public void onRefresh() {
                list.clear();
                initData();
                recycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadNext() {
                initData();
                recycleViewAdapter.notifyDataSetChanged();
                swipeRecyclerView.onLoadFinish();
            }
        });
    }



    private void initData() {
        for(int i = 0;i < 10;i++){
            String a = i + "";
            list.add(a);
        }
    }
}
