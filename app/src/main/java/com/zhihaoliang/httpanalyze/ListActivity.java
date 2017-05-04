package com.zhihaoliang.httpanalyze;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhihaoliang.httpanalyze.beans.ListBean;
import com.zhihaoliang.httpanalyze.beans.PortalBean;
import com.zhihaoliang.httpanalyze.beans.PropertyBean;
import com.zhihaoliang.httpanalyze.https.ApiService;
import com.zhihaoliang.httpanalyze.https.HttpApi;
import com.zhihaoliang.httpanalyze.util.DeviceUtils;
import com.zhihaoliang.httpanalyze.util.ParamUtils;
import com.zhihaoliang.util.dialog.MyProgressDialog;
import com.zhihaoliang.util.ui.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private MyProgressDialog mMyProgressDialog;

    private static final Gson GSON = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        final XRecyclerViewAdapter xRecyclerViewAdapter = new XRecyclerViewAdapter();
        xRecyclerView.setAdapter(xRecyclerViewAdapter);

        String url = getIntent().getStringExtra("url");
        final ApiService apiService = HttpApi.ApiTypeService(url, SimpleXmlConverterFactory.create());

        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                ListActivity.this.onRefresh(xRecyclerViewAdapter, apiService, xRecyclerView);
            }

            @Override
            public void onLoadMore() {

            }
        });
        onRefresh(xRecyclerViewAdapter, apiService, null);

        mMyProgressDialog = new MyProgressDialog(this, 50);
        mMyProgressDialog.setMsgText("联网中，请稍后...");
    }

    private void onRefresh(final XRecyclerViewAdapter xRecyclerViewAdapter, ApiService apiService, final XRecyclerView xRecyclerView) {
        Call<ListBean> call = apiService.initDate();
        call.enqueue(new Callback<ListBean>() {
            @Override
            public void onResponse(Call call, Response response) {
                mListBean = (ListBean) response.body();
                xRecyclerViewAdapter.notifyDataSetChanged();
                if (xRecyclerView != null) {
                    xRecyclerView.refreshComplete();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (xRecyclerView != null) {
                    xRecyclerView.refreshComplete();
                }
                Toast.makeText(ListActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class XRecyclerViewAdapter extends RecyclerView.Adapter<XRecyclerViewAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_layout, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
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
                        int postion = getAdapterPosition() - 1;
                        String url = getUrl(postion);
                        String methodName = mListBean.getPortal().get(postion).getName();
                        String dataName = mListBean.getPortal().get(postion).getNameData();
                        String dvcCode =getParams(mListBean.getPortal().get(postion).getDvcCode());
                        if(TextUtils.isEmpty(dvcCode)){
                            return;
                        }
                        String encryptKey = getParams(mListBean.getPortal().get(postion).getEncryptKey());
                        if(TextUtils.isEmpty(encryptKey)){
                            return;
                        }

                        String data = getReqData(postion, dataName);
                        Log.log(this, data);

                        HashMap<String, String> hashMap = ParamUtils.getParam(methodName, data,dvcCode,encryptKey);

                        mMyProgressDialog.show();
                        doConnact(url, hashMap, methodName);
                    }
                });
            }
        }
    }

    private String getParams(String param) {
        if (!param.startsWith("{$")) {
            return param;
        }

        param = param.replace("{$", "");
        param = param.replace("$}", "");

        if (param.toUpperCase().equals("IMSI")) {
            return DeviceUtils.getDeviceIMSI(this);
        }
        String[] params = param.split("\\.");

        if (!mHashMap.containsKey(params[0])) {
            Toast.makeText(this, "请先调用方法" + params[0], Toast.LENGTH_SHORT).show();
            return null;
        }

        String object = mHashMap.get(params[0]);
        try {
            JSONObject jsonObject = new JSONObject(object);
            for (int i = 1; i < params.length; i++) {
                if (i == params.length - 1){
                    return  jsonObject.getString(params[i]);
                }else{
                    jsonObject = jsonObject.getJSONObject(params[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Json 解析错误", Toast.LENGTH_SHORT).show();
       return null;
    }

    private String getReqData(int postion, String dataName) {
        ArrayList<PropertyBean> arrayList = mListBean.getPortal().get(postion).getPortal();
        HashMap<String, String> hashMap = new HashMap<>();
        for (PropertyBean propertyBean : arrayList) {
            hashMap.put(propertyBean.getName(), propertyBean.getValue());
        }

        StringBuffer data = new StringBuffer();
        data.append("{ \"methodName\":".replace("methodName", dataName));
        data.append(GSON.toJson(hashMap));
        data.append("}");

        return data.toString();
    }

    private String getUrl(int postion) {
        StringBuffer url = new StringBuffer();

        String baseUrl = mListBean.getBaseUrl();
        if (!TextUtils.isEmpty(baseUrl) && !"null".equals(baseUrl)) {
            url.append(baseUrl);
        }

        String suffixUrl = mListBean.getPortal().get(postion).getUrl();
        if (!TextUtils.isEmpty(suffixUrl) && !"null".equals(suffixUrl)) {
            suffixUrl = suffixUrl.toLowerCase();
            if (suffixUrl.startsWith("http://") || suffixUrl.startsWith("https://")) {
                return suffixUrl;
            }
            url.append(suffixUrl);
        }

        return url.toString();
    }

    private void doConnact(String url, HashMap<String, String> map, final String methodName) {
        int index = url.lastIndexOf("/");
        String baseUrl = url.substring(0, index + 1);
        url = url.substring(index + 1);
        final ApiService apiService = HttpApi.ApiTypeService(baseUrl);
        Call<String> call = apiService.connact(url, map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call call, Response response) {
                mMyProgressDialog.dismiss();
                String body = response.body().toString();
                Log.log(this, body);
                mHashMap.put(methodName, body);
                Toast.makeText(ListActivity.this, "链接完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mMyProgressDialog.dismiss();
                Toast.makeText(ListActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
