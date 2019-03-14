package com.example.cureu;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	 private EditText username;
	 private EditText password;
	 private TextView err;

	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) this.findViewById(R.id.login_username);
        password = (EditText) this.findViewById(R.id.login_password);
        err = (TextView) this.findViewById(R.id.err);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    public void sign(View v){
    	 Intent intent = new Intent(this, Sign.class);
    	 startActivity(intent);
    }
    public void login(View v) throws IOException{
    	  String user = username.getText().toString();
	      String pass = password.getText().toString();
	      if(user.equals("")||pass.equals("")){
	    	  System.out.println("user or pass is null");
	    	  err.setText("用户名或密码为空");
	    	  err.setVisibility(View.VISIBLE);
	      }
	      else{
	    	  err.setText(null);
	    	  err.setVisibility(View.INVISIBLE);
	    	  Toast.makeText(this,"正在登录",Toast.LENGTH_SHORT).show();
	    	  new SignInProcess().execute(user,pass);
	    	  
	      }
//    	Intent intent = new Intent(Login.this,Sign.class);
//    	startActivity(intent);
    }
    //一定要开一个新的线程
    private class SignInProcess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
     	  
            String username = params[0];
            String password = params[1];
            String result = "";
            //String s_url = "http://qqx.im:8080/background/NewLogin?username="+username+"&password="+password;
            String s_url = "http://175.10.104.223:8080/MyApp/signin?username="+username+"&password="+password;
           // String s_url = "http://10.63.28.235:8080/background/NewLogin?username="+username+"&password="+password;
            
            try {
                URL url = new URL(s_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is=conn.getInputStream();;
                InputStreamReader reader = new InputStreamReader(is, "UTF-8");
                int temp;
                while((temp=reader.read()) != -1) {
                    result += (char)temp;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            System.out.println("成功获得后台结果");
            return result;
        }

 	@Override
        protected void onPostExecute(String result) {
            try {
                JSONObject result_json = new JSONObject(result);
                //判断后台是否返回错误
                if(result_json.has("error")) {
                    String error_code;
                    error_code = result_json.getString("error");
                    err.setText(error_code);
                    err.setVisibility(View.VISIBLE);
                    password.setText(null);
                } else {
                   
                   SignInSuccess(result_json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //成功验证，设置跳转页面
    private void SignInSuccess(JSONObject info) {
        Intent intent = new Intent(this,MyPage.class);
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        try {
            intent.putExtra("username", info.getString("username"));
            intent.putExtra("name", info.getString("name"));
            intent.putExtra("age", info.getInt("age"));
            intent.putExtra("phone", info.getString("phone"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

 }

