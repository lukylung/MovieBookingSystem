package com.example.helloandroid;

import java.net.URL;
import java.net.URLDecoder;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException; 
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//import net.sf.json.JSONObject;
import android.os.Handler;
import android.app.Activity;
import android.util.*;
import android.os.StrictMode; 
import android.os.Bundle;
import android.os.Message;
import android.content.Intent;  
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
	private TextView accounttv,pdwtv,pdw2tv,phonenumtv;
	public User user=new User();
	private TextView hellotv;
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0) {
            	hellotv.setText("注册成功");  
            }else if (msg.what == 1) {
            	hellotv.setText("数据库异常");
            }else if(msg.what == 2) {
            	hellotv.setText("服务器异常");
            } else if(msg.what == 4) {
            	hellotv.setText("该用户名已被注册");
            }
        }  
    };
	
	private void getData() {
		user.setusername(accounttv.getText().toString());
		user.setpassword(pdwtv.getText().toString());
		user.setphonenum(phonenumtv.getText().toString());
		
	}
	private void sendData() {
		getData();
		String ss="http://192.168.191.1:8080/register";
		URL url=null;
		HttpURLConnection conn=null;
		//HttpPost httpRequst = new HttpPost(uriAPI);//创建HttpPost对象  
		/*try{
			String encoderJson = URLEncoder.encode(user.toString(), "UTF-8");
		}catch(Exception e){
			Log.e("7", "Exception: "+Log.getStackTraceString(e));
		}*/
		
		boolean bb=true;
		try{
			url = new URL(ss);
		} catch (MalformedURLException e){
			Log.e("1", "Exception: "+Log.getStackTraceString(e));
		}
		try{
			conn = (HttpURLConnection)(url.openConnection());
		} catch(IOException e) {
			Log.e("2", "Exception: "+Log.getStackTraceString(e));
		}
		conn.setConnectTimeout(6* 1000);
		conn.setDoOutput(true);
		conn.setUseCaches(false);//不使用Cache
		
		try{
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Charset", "UTF-8");
		} catch(IOException e){
			Log.e("3", "Exception: "+Log.getStackTraceString(e));
		}
		
		/*try{
			String name=URLEncoder.encode("黄武艺","utf-8");
	        conn.setRequestProperty("NAME", name);
		} catch(IOException e){
			Log.e("8", "Exception: "+Log.getStackTraceString(e));
		}*/
		
		try{
			String requestString = user.toString();
			byte[] requestStringBytes = requestString.getBytes("UTF-8");
			conn.setRequestProperty("Content-length", "" + requestStringBytes.length);
			OutputStream outStream = conn.getOutputStream();
			outStream.write(requestStringBytes);
	        outStream.close();
		} catch (Exception e) {
			Log.e("4", "Exception: "+Log.getStackTraceString(e));
		}
		Message msg = new Message(); 
		try{
			if(conn.getResponseCode()==200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
    	        StringBuilder sb = new StringBuilder();
    	        while((line = reader.readLine())!=null){
    	            sb.append(line);
    	        }
    	        String reqBody = sb.toString();
    	        URLDecoder.decode(reqBody, "UTF-8");
    	        if(reqBody.equals("success")) {
    	        	msg.what = 0;  
    	        } else {
    	        	msg.what = 4;
    	        }
				//Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
			} else {
				msg.what = 1;
				//Toast.makeText(MainActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			msg.what = 2;
			Log.e("5", "Exception: "+Log.getStackTraceString(e));
		}
		handler.sendMessage(msg); 
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到按钮实例
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }*/
        Button hellobtn = (Button)findViewById(R.id.hellobutton);
        //设置监听按钮点击事件
        hellobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到textview实例
                hellotv = (TextView)findViewById(R.id.hellotextView);
                //弹出Toast提示按钮被点击了
                Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                accounttv = (TextView)findViewById(R.id.accountEdittext);
                pdwtv = (TextView)findViewById(R.id.pwdEdittext);
                pdw2tv = (TextView)findViewById(R.id.pwdEdittext_again);
                phonenumtv = (TextView)findViewById(R.id.phonenumEdittext);
                if(accounttv.getText().length()==0) {
                	hellotv.setText(R.string.interact_message);
                	return;
                } else if(pdwtv.getText().length()==0) {
                	hellotv.setText(R.string.interact_message2);
                	return;
                } else if(pdw2tv.getText().length()==0) {
                	hellotv.setText(R.string.interact_message3);
                	return;
                } else if(!pdw2tv.getText().toString().equals(pdwtv.getText().toString())) {
                	hellotv.setText(R.string.interact_message4);
                	return;
                } else if(phonenumtv.getText()=="") {
                	hellotv.setText(R.string.interact_message5);
                	return;
                } else if(pdwtv.getText().length() < 6) {
                	hellotv.setText(R.string.interact_message6);
                	return;
                } else if(accounttv.getText().length() < 5) {
                	hellotv.setText(R.string.interact_message7);
                	return;
                } else if(phonenumtv.getText().length() != 11) {
                	hellotv.setText(R.string.interact_message8);
                	return;
                } else if(!accounttv.getText().toString().matches("^[a-zA-Z0-9]*")) {
                	hellotv.setText(R.string.interact_message9);
                	return;
                }else if(!pdwtv.getText().toString().matches("^[a-zA-Z0-9]*")) {
                	hellotv.setText(R.string.interact_message10);
                	return;
                }else {
                	Thread thread=new Thread(new Runnable()  
        	        {  
        	            @Override  
        	            public void run()  
        	            {  
        	            	sendData();
        	            }  
        	        });
        	        thread.start(); 
                	return;
                }
            }
        });
        Button changeAct = (Button)findViewById(R.id.register_to_login);
        changeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出Toast提示按钮被点击了
            	Intent intent = new Intent();
                Toast.makeText(MainActivity.this,"跳转到登录界面",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                intent.setClassName(getApplicationContext(),"com.example.helloandroid.Activity2");
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });
        Button changeAct2 = (Button)findViewById(R.id.register_to_main);
        changeAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出Toast提示按钮被点击了
            	Intent intent = new Intent();
                Toast.makeText(MainActivity.this,"跳转到主界面",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                intent.setClassName(getApplicationContext(),"com.example.helloandroid.Activity3");
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });
        
    }
    
}