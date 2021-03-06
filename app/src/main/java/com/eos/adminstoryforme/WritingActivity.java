package com.eos.adminstoryforme;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;

public class WritingActivity extends AppCompatActivity {

    int author_id;
    WebView webView;
    private WebSettings wvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        TextView tvTitle = findViewById(R.id.tlWTitle);
        Button btnPermission = findViewById(R.id.btnPermission);
        webView = findViewById(R.id.wvWriting);
        webView.setWebViewClient(new WebViewClient());
        wvs = webView.getSettings();
        wvs.setJavaScriptCanOpenWindowsAutomatically(true);
        wvs.setSupportMultipleWindows(false);
        wvs.setLoadWithOverviewMode(false);
        wvs.setUseWideViewPort(false);
        wvs.setSupportZoom(false);
        wvs.setBuiltInZoomControls(false);
        wvs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvs.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wvs.setDomStorageEnabled(true);
        wvs.setTextZoom(100);
        wvs.setJavaScriptEnabled(true);
        wvs.setAllowFileAccess(true);
        wvs.setAllowFileAccessFromFileURLs(true);
        wvs.setAllowUniversalAccessFromFileURLs(true);

        OkHttpClient client = new OkHttpClient();


        btnPermission.setOnClickListener(v -> {
            int permissionParam;
            Intent intent = getIntent();
            int writing_id = intent.getIntExtra("writing_id", 0);
            if(intent.getStringExtra("title").length() > 15)
                tvTitle.setText(intent.getStringExtra("title").substring(0, 14)+"...");
            else
                tvTitle.setText(intent.getStringExtra("title"));

            if(writing_id != 0) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", Integer.toString(writing_id))
                        .build();

                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(getString(R.string.url) + "writing/contents")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String contents = jsonObject.getString("contents");
                            author_id = jsonObject.getInt("author_id");
                            int permission = jsonObject.getInt("permission");
                            //webView.setHtml(contents, new HtmlResImageGetter(WritingActivity.this));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadData(contents, "text/html", "UTF-8");
                                    if(permission == 1) {
                                        btnPermission.setText("이 글 게시 취소하기");
                                    } else {
                                        btnPermission.setText("이 글 게시 허용하기");
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            if(btnPermission.getText().toString().equals("이 글 게시 취소하기")) {
                permissionParam = 0;
            } else {
                permissionParam = 1;
            }

            RequestBody requestBody = new FormBody.Builder()
                    .add("writing_id", Integer.toString(writing_id))
                    .add("permission", Integer.toString(permissionParam))
                    .add("author_id", Integer.toString(author_id))
                    .build();

            Request request = new Request.Builder()
                    .post(requestBody)
                    .url(getString(R.string.url) + "admin/change_permission")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "오류 발생. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(permissionParam == 0) {
                                Toast.makeText(getApplicationContext(), "이 글은 게시되지 않습니다.", Toast.LENGTH_SHORT).show();
                                btnPermission.setText("이 글 게시 허용하기");
                            } else {
                                Toast.makeText(getApplicationContext(), "이 글은 게시 허용되었습니다.", Toast.LENGTH_SHORT).show();
                                btnPermission.setText("이 글 게시 취소하기");
                            }
                        }
                    });
                }
            });
        });
    }
}