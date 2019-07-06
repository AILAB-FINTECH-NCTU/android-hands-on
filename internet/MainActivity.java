package com.johnny12150.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Message;

import nctu.fintech.appmate.Table;
import nctu.fintech.appmate.Tuple;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Table mTable;
    EditText et1;
    TextView tv1;
    String s1;
    Tuple tuple_add;
    String word2send;

    private static final int COMPLETED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.B1);
        et1 = findViewById(R.id.ET1);
        tv1 = findViewById(R.id.TV1);
        mTable = new Table("http://140.113.73.128:8000/api", "test", "panda", "apclab0939825885");
        b1.setOnClickListener(b1cl);


        Timer timer01 =new Timer();
        timer01.schedule(task, 0,3000);
    }

    private View.OnClickListener b1cl = new View.OnClickListener(){
        public void onClick(View v) {
            et1.setText("");
            Thread t1 = new Thread(r1);
            t1.start();
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv1.setText(s1);
            }
        }
    };

    private TimerTask task = new TimerTask(){
        public void run() {
            Thread t2 = new Thread(r2);
            // 執行t2，撈歷史聊天紀錄
            t2.start();
        }
    };

    // 輸入的值送到DB
    private Runnable r1 = new Runnable(){
        public void run()
        {
            tuple_add = new Tuple();
            word2send = et1.getText().toString();
            // 清除剛剛的輸入，貌似是導致閃退的原因
            // et1.setText("");
            tuple_add.put("test", word2send);
            try {
                mTable.add(tuple_add);
            }catch (IOException e) {
                Log.e("Net", "Fail to put");
            }
        }
    };

    // 從資料庫取值
    private Runnable r2 = new Runnable()
    {
        public void run()
        {
            try {
                Tuple[] tuple_get = mTable.get();
                s1 = "";
                for(int i=0; i<tuple_get.length; i++){
                    String tempString = tuple_get[i].get("test");
                    if(tempString!=null && !tempString.equals("")) {
                        s1 = s1 + tempString + "\n";
                    }
                }

            }catch (IOException e){
                Log.e("Net", "Fail to get");
            }

            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };

}


