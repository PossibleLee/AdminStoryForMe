package com.eos.adminstoryforme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client;
    Gson gson;
    Callback callback;

    RecyclerView rvMain;
    Button btnChange;
    TextView tvNothing;

    Button btn_notice;
    Button btn_notice_list;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rvMain);
        btnChange = findViewById(R.id.btnMainChange);
        tvNothing = findViewById(R.id.tvNothing);
        btn_notice = findViewById(R.id.button);
        btn_notice_list = findViewById(R.id.button2);

        client = new OkHttpClient();
        gson = new Gson();

        callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ArrayList<WritingData> dataArrayList = gson.fromJson(response.body().string(), new TypeToken<ArrayList<WritingData>>() {}.getType());
                adapter = new MainAdapter(MainActivity.this, dataArrayList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dataArrayList.size() == 0) {
                            tvNothing.setVisibility(View.VISIBLE);
                            rvMain.setVisibility(View.GONE);
                            rvMain.setAdapter(adapter);
                            rvMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        } else {
                            tvNothing.setVisibility(View.GONE);
                            rvMain.setVisibility(View.VISIBLE);
                            rvMain.setAdapter(adapter);
                            rvMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }
                });
            }
        };

        btnChange.setOnClickListener(v -> {
            if(btnChange.getText().toString().equals("게시 허용된 글 보기")) {
                btnChange.setText("게시 비허용된 글 보기");

                RequestBody requestBody2 = new FormBody.Builder()
                        .add("permission", Integer.toString(1))
                        .build();

                Request request2 = new Request.Builder()
                        .post(requestBody2)
                        .url(getString(R.string.url) + "admin/all")
                        .build();

                client.newCall(request2).enqueue(callback);
            } else {
                btnChange.setText("게시 허용된 글 보기");

                RequestBody requestBody2 = new FormBody.Builder()
                        .add("permission", Integer.toString(0))
                        .build();

                Request request2 = new Request.Builder()
                        .post(requestBody2)
                        .url(getString(R.string.url) + "admin/all")
                        .build();

                client.newCall(request2).enqueue(callback);
            }
        });

        btn_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNoticeActivity.class);
                startActivity(intent);

            }
        });
        btn_notice_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeList.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return false;
//            }
//
//        });
        return true;
    }

    // 뒤로가기인듯
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            //select back button
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onResume() {
        super.onResume();

        RequestBody requestBody = new FormBody.Builder()
                .add("permission", Integer.toString(0))
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(getString(R.string.url) + "admin/all")
                .build();

        client.newCall(request).enqueue(callback);

        btnChange.setText("게시 허용된 글 보기");
    }
}