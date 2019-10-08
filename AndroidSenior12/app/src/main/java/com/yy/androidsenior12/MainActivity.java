package com.yy.androidsenior12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private EditText editText2;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
    }

    public void getSync(View view) {
        //第一步 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二部 构建Request对象
        Request request = new Request.Builder()
                .url("http://192.168.0.161:8080/AndroidSenior12_JavaWeb/")
                .get()
                .build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步：同步get请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response  response = call.execute();//阻塞，必须子线程执行
                    ResponseBody body = response.body();
                    if (body != null){
                        String result = body.string();
                        Log.i("result",result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getAsync(View view) {
        //第一步 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二部 构建Request对象
        Request request = new Request.Builder()
                .url("http://192.168.0.161:8080/AndroidSenior12_JavaWeb/")
                .get()
                .build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步：同步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String result = body.string();
                    Log.i("result", result);
                }
            }
        });

    }

    public void getAsyncParam(View view) {
        String username = editText.getText().toString();
        String password = editText2.getText().toString();
        if (username.isEmpty()){
            editText.setError("用户名不能为空");
            return;
        }
        if (password.isEmpty()){
            editText2.setError("密码不能为空");
            return;
        }
        //第一步 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二部 构建Request对象
        Request request = new Request.Builder()
                .url("http://192.168.0.161:8080/AndroidSenior12_JavaWeb/login.action" +
                        "?username=" + username + "&password=" + password)
                .build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步：异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String result = body.string();
                    Log.i("result", result);
                }
            }
        });
    }

    public void postAsync(View view) {
        String username = editText.getText().toString();
        String password = editText2.getText().toString();
        if (username.isEmpty()){
            editText.setError("用户名不能为空");
            return;
        }
        if (password.isEmpty()){
            editText2.setError("密码不能为空");
            return;
        }
        //第一步 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二部创建RequestBody
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        //创建request
        Request request = new Request.Builder()
                .url("http://192.168.0.161:8080/AndroidSenior12_JavaWeb/")
                .get()
                .build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步：异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String result = body.string();
                    Log.i("result", result);
                }
            }
        });
    }

    public void myOkHttp(View view) {
        String username = editText.getText().toString();
        String password = editText2.getText().toString();
        if (username.isEmpty()){
            editText.setError("用户名不能为空");
            return;
        }
        if (password.isEmpty()){
            editText2.setError("密码不能为空");
            return;
        }


    }
}
