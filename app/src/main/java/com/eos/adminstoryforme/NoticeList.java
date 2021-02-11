package com.eos.adminstoryforme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticeList extends AppCompatActivity {
    OkHttpClient client;
    Gson gson;
    Callback callback;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        client = new OkHttpClient();
        gson = new Gson();
        rv = findViewById(R.id.nl_recy);



        callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<Notice_data> dataArrayList = gson.fromJson(response.body().string(), new TypeToken<ArrayList<Notice_data>>() {}.getType());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dataArrayList.size() == 0) {
                            Toast.makeText(NoticeList.this, "공지 없음", Toast.LENGTH_SHORT).show();
                        } else {

                            NoticeListAdapter adapter = new NoticeListAdapter(getApplicationContext(), dataArrayList);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }
                });
            }
        };

        RequestBody requestBody2 = new FormBody.Builder()
                .build();

        Request request2 = new Request.Builder()
                .post(requestBody2)
                .url(getString(R.string.url)+ "notice")
                .build();

        client.newCall(request2).enqueue(callback);



    }
}