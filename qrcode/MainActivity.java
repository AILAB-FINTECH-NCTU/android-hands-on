package com.johnny12150.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txt1;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (TextView) findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(scan);

    }

    //設定OnClickListener
    private Button.OnClickListener scan = new Button.OnClickListener() {
        public void onClick(View v) {
            // 連結ZXING的API，開啟條碼掃描器
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                // 設定參數，兩種條碼都讀
            intent.putExtra("SCAN_MODE", "SCAN_MODE");
            //只判斷QRCode
            //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            //只判斷二維條碼
            //intent.putExtra("SCAN_MODE", "PRODUCT_MODE");

            //因為要回傳掃描結果所以要使用startActivityForResult
            // 要求回傳1
            startActivityForResult(intent, 1);
        }
    };

    //設定onActivityResult
    //startActivityForResult會將值傳回到onActivity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //requestCode在startActivityForResult傳入參數時決定的，如果成功的話會傳回相同的值
        if (requestCode == 1) {
            //成功回傳值
            if (resultCode == RESULT_OK) {
                //ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                //ZXing回傳的格式
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //將掃描的結果顯示在textview元件
                txt1.setText(contents);
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

}
