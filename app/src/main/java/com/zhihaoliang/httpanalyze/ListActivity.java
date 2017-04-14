package com.zhihaoliang.httpanalyze;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhihaoliang.httpanalyze.beans.ListBean;
import com.zhihaoliang.httpanalyze.beans.PortalBean;
import com.zhihaoliang.httpanalyze.https.ApiService;
import com.zhihaoliang.httpanalyze.https.HttpApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by haoliang on 2017/3/31.
 * email:zhihaoliang07@163.com
 */

public class ListActivity extends AppCompatActivity {

    private ListBean mListBean;

    private HashMap<String, String> mHashMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        final XRecyclerViewAdapter xRecyclerViewAdapter = new XRecyclerViewAdapter();
        xRecyclerView.setAdapter(xRecyclerViewAdapter);

        String url = getIntent().getStringExtra("url");
        ApiService apiService = HttpApi.ApiTypeService(url, SimpleXmlConverterFactory.create());

        Call<ListBean> call = apiService.initDate();
        call.enqueue(new Callback<ListBean>() {
            @Override
            public void onResponse(Call call, Response response) {
                mListBean = (ListBean) response.body();
                xRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(ListActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class XRecyclerViewAdapter extends RecyclerView.Adapter<XRecyclerViewAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_layout, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PortalBean portalBean = mListBean.getPortal().get(position);

            String name = portalBean.getName();
            String dis = portalBean.getDis();

            holder.mTextView.setText(name + " : " + dis);
        }

        @Override
        public int getItemCount() {
            if (mListBean == null) {
                return 0;
            }

            if (mListBean.getPortal() == null) {
                return 0;
            }

            return mListBean.getPortal().size();
        }

        public class MyViewHolder extends XRecyclerView.ViewHolder {
            private TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }
}
