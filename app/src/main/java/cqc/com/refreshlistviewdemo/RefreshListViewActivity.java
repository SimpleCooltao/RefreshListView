package cqc.com.refreshlistviewdemo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RefreshListViewActivity extends AppCompatActivity implements RefreshListView.OnRefreshListener {

    private List<String> textList;
    private MyAdapter adapter;
    private RefreshListView rListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_listview);

        rListView = (RefreshListView) findViewById(R.id.refreshlistview);
        textList = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            textList.add("这是一条ListView的数据" + i);
        }
        adapter = new MyAdapter();
        rListView.setAdapter(adapter);
        rListView.setOnRefreshListener(this);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView textView = new TextView(RefreshListViewActivity.this);
            textView.setText(textList.get(position));
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18.0f);
            return textView;
        }

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                for (int i = 0; i < 2; i++) {
                    textList.add(0, "这是下拉刷新出来的数据" + i);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                rListView.hideHeaderView();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(5000);

                textList.add("这是加载更多出来的数据1");
                textList.add("这是加载更多出来的数据2");
                textList.add("这是加载更多出来的数据3");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();

                // 控制脚布局隐藏
                rListView.hideFooterView();
            }
        }.execute(new Void[]{});
    }

}