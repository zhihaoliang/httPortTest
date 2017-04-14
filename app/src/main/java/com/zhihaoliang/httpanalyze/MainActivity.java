package com.zhihaoliang.httpanalyze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edittxt);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);

        final SharedPreferences user = getSharedPreferences("user_info", 0);
        String url = user.getString("url", null);
        if (!TextUtils.isEmpty(url)) {
            editText.setText(url);
            if (url.startsWith("https://")) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

        }

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(MainActivity.this, "请输入连接地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                url = url.toLowerCase();

                if ((!url.startsWith("http://")) && (!url.startsWith("https://"))) {
                    if (checkBox.isChecked()) {
                        url = "https://" + url;
                    } else {
                        url = "http://" + url;
                    }
                }

                if (!url.endsWith("/")) {
                    url += "/";
                }

                user.edit().putString("url", url).commit();
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}
