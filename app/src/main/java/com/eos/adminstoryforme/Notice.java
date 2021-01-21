package com.eos.adminstoryforme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Notice extends AppCompatActivity {

    private TextView notice_tv_title;
    private TextView notice_tv_date;
    private TextView notice_tv_context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Intent intent = getIntent();

        notice_tv_title = findViewById(R.id.notice_tv_title);
        notice_tv_date = findViewById(R.id.notice_tv_date);
        notice_tv_context = findViewById(R.id.notice_tv_context);

        notice_tv_title.setText(intent.getStringExtra("title"));
        notice_tv_date.setText(intent.getStringExtra("date"));
        notice_tv_context.setText(intent.getStringExtra("context"));

    }


}