package com.example.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends Activity {
	 /**
     * Called when the activity is first created.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Button changeAct = (Button)findViewById(R.id.login_to_register);
        changeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //����Toast��ʾ��ť�������
            	Intent intent = new Intent();
                Toast.makeText(Activity2.this,"��ת��ע�����",Toast.LENGTH_SHORT).show();
                //��ȡstrings.xml�����interact_message��Ϣ��д��textview��
                intent.setClassName(getApplicationContext(),"com.example.helloandroid.MainActivity");
                Activity2.this.startActivity(intent);
                Activity2.this.finish();
            }
        });
	}
}
