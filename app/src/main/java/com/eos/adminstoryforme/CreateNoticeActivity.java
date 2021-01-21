package com.eos.adminstoryforme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateNoticeActivity extends AppCompatActivity {

    private EditText title;
    private EditText contents;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);

        title = findViewById(R.id.edit_title);
        contents = findViewById(R.id.edit_text);
        button = findViewById(R.id.btn_submit);

        OkHttpClient client = new OkHttpClient();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestBody requestBody = new FormBody.Builder()
                        .add("title", title.getText().toString())
                        .add("contents", contents.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .post(requestBody)
                        .url("http://52.79.183.8:5000/notice/upload")
                        .build();



                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Toast.makeText(CreateNoticeActivity.this, "완료", Toast.LENGTH_SHORT).show();
                finish();


            }
        });

    }
}