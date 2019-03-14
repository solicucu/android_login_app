package com.example.cureu;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;
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

public class Sign extends Activity {

	private EditText username;
	private EditText name;
	private EditText password; 
    private EditText confirm; 
    private EditText age;
    private EditText phone;
	private Button sign;
	private Button cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		username = (EditText) this.findViewById(R.id.sign_username);
        password = (EditText) this.findViewById(R.id.sign_password);
        confirm  = (EditText)findViewById(R.id.sign_comfirm);
		name  = (EditText)findViewById(R.id.sign_name);
		age  = (EditText)findViewById(R.id.sign_age);
		phone  = (EditText)findViewById(R.id.sign_phone);
        sign  = (Button)findViewById(R.id.bt_sign);
		cancel  = (Button)findViewById(R.id.bt_cancel);
   	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign, menu);
		return true;
	}
	public void sign(View view) throws UnsupportedEncodingException{
		   String str_username  = username.getText().toString();
		   String str_name      = name.getText().toString();
		  // System.out.println(str_name);
		   str_name=EncodingUtils.getString(str_name.toString().getBytes(), "utf-8");
		 //  System.out.println(str_name);
		   String str_age    = age.getText().toString();
		   String str_phone = phone.getText().toString();
		   String str_password  = password.getText().toString();
		   String str_confirm  = confirm.getText().toString();
		     
		   //检查username
		   String mess = "";
	       if(str_username.length() < 5 || str_username.length() > 10)
	            mess += " 用户名长度需5-10位 ";
	        String pattern1 = "^[a-zA-Z][a-zA-Z\\d_]*";
	        String pattern2 = ".*[A-Z].*";
	        boolean match1 = Pattern.matches(pattern1, str_username);
	        boolean match2 = Pattern.matches(pattern2, str_username);
	        if(!match1) {
	            mess += " 用户名需要以英文字母开头，由英文字母数字和_组成 ";
	        }
	        if(!match2) {
	            mess += " 用户名必须包含至少一个大写字母 ";
	        }
	        
	        if(!mess.isEmpty()) {
	        	Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
	        }
	        
		   //检查姓名
		   if(str_name.isEmpty()){
			   	mess +="姓名不能为空";
			   	Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
		   }
		   String pattern3 = "[a-zA-Z\u4E00-\u9FA5]*";
		   boolean match3 = Pattern.matches(pattern3, str_name);
		   if(!match3){
			    mess+="姓名不能有非法字符";
			    Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
		   }
		   //username.setText(str_name);
		   //str_name = new String(str_name.getBytes("GBK"), "UTF-8");
		   //检查密码
		    mess = "";
	        if(str_password.length() < 6 || str_password.length() > 12 ||str_confirm.length()>12||str_confirm.length()<6)
	            mess += " 密码长度需6-12位 ";
	        if(!str_password.equals(str_confirm)){
	        	mess +=" 输入不一致 ";
	        }
	        String pattern6 = "[a-zA-Z\\d_]*";
	        boolean match6 = Pattern.matches(pattern6, str_password);
	        if(!match6) {
	            mess += " 密码应由英文字母数字和_组成 ";
	        }
	        if(!mess.isEmpty()) {
	        	Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
	        	password.setText(null);
	        	confirm.setText(null);
			    return ;
	        }
		   //检查age
		   if(str_age.isEmpty()){
			    mess+=" 年龄为空 ";
			    Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
		   }
		   String pattern4 = "^([1-9]\\d|\\d)$";
		   boolean match4 = Pattern.matches(pattern4, str_age);
		   if(!match4){
			    mess+=" 年龄只能为0-99的整数 ";
			    Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
		   }
		   
		   //检查电话
		   if(str_phone.isEmpty()){
			    mess+=" 电话为空 ";
			    Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			    return ;
		   }
		   String pattern5 = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$";
		   boolean match5 = Pattern.matches(pattern5, str_phone);
		   if(!match5){
			   mess+=" 输入正确的电话号码 ";
			   Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
			   return ;
		   }
		   
		   new SignUpProcess().execute(str_username, str_password, str_name, str_age, str_phone);
	}
	private class SignUpProcess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String name = params[2];
            String age = params[3];
            String phone = params[4];
            String result = "";
           // System.out.println(name);
            //String s_url = "http://175.10.205.132:8080/background/Sign?username="+username+"&password="+password+"&name="+name+"&age="+age+"&phone="+phone;
           
            //String s_url = "http://10.63.28.235:8080/background/Sign?username="+username+"&password="+password+"&name="+name+"&age="+age+"&phone="+phone;
           
			String s_url = null;
			try {
				s_url = "http://qqx.im:8080/background/Sign?username="+username+"&password="+password+"&name="+URLEncoder.encode(name,"utf-8")
				+"&age="+age+"&phone="+phone;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(s_url);
            try {
            	
                URL url = new URL(s_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is, "UTF-8");
                int temp;
                while((temp=reader.read()) != -1) {
                    result += (char)temp;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject result_json = new JSONObject(result);
                if(result_json.has("error")) {
                    String error_code;
                    error_code = result_json.getString("error");
                    showerr(error_code);
                    if(error_code.equals("用户名已被注册")){
                    	username.setText(null);
                    }
                    if(error_code.equals("姓名已被注册")){
                    	name.setText(null);
                    }
                } else {
                    SignInSuccess(result_json);
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
	
	public void showerr(String error_code){
		 Toast.makeText(this,error_code,Toast.LENGTH_SHORT).show();
	}
	
	private void SignInSuccess(JSONObject info) {
        Toast.makeText(this,"注册成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MyPage.class);
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
	
    public void cancel(View v){
		Intent intent = new Intent(Sign.this,Login.class);
		startActivity(intent);
	}
}
