package com.example.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout;
import android.widget.GridView;;

public class Activity3 extends Activity {
	 /**
     * Called when the activity is first created.
     */
	private TestAPI Moviedata;
	private TextView tv;
	private GridView gridview;
	private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0) {
            	TextView textView;
            	if(!Moviedata.movieList.isEmpty()) {
            		for (int i = 0; i < Moviedata.movieList.size(); i++) {
            			textView = new TextView(getApplicationContext());
            			textView.setText(Moviedata.movieList.get(i).nm);
            			//textView.set
            			//gridview.addView(textView);
                	}
            	} else {
            		
            	}
            }else if (msg.what == 1) {
            	
            	
            }else if(msg.what == 2) {
            } else if(msg.what == 4) {
            }
        }  
    };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        gridview = (GridView) this.findViewById(R.id.movie_table);
        Button changeAct = (Button)findViewById(R.id.main_to_register);
        changeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出Toast提示按钮被点击了
            	Intent intent = new Intent();
                Toast.makeText(Activity3.this,"跳转到注册界面",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                intent.setClassName(getApplicationContext(),"com.example.helloandroid.MainActivity");
                Activity3.this.startActivity(intent);
                Activity3.this.finish();
            }
        });
        changeAct = (Button)findViewById(R.id.main_to_login);
        changeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出Toast提示按钮被点击了
            	Intent intent = new Intent();
                Toast.makeText(Activity3.this,"跳转到登录界面",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                intent.setClassName(getApplicationContext(),"com.example.helloandroid.Activity2");
                Activity3.this.startActivity(intent);
                Activity3.this.finish();
            }
        });
        Thread thread=new Thread(new Runnable()  
        {  
            @Override  
            public void run()  
            {  
            	Message msg = new Message(); 
            	Moviedata.analyzeMovieDataFromAPI(Moviedata.getURLContent("http://m.maoyan.com/movie/list.json?type=hot&offset=0&limit=1000"));
            	msg.what=0;
                handler.sendMessage(msg); 
            }  
        });
        thread.start(); 
        
	}
}
