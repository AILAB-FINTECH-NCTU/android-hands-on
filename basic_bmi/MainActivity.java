package com.johnny12150.basic_bmi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    // 宣告全域
    Button BT_calculate;
    EditText h;
    EditText w;
    TextView TV_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BT_calculate  = (Button)findViewById(R.id.BT_calculate);
        h = (EditText)findViewById(R.id.TF_height);
        w = (EditText)findViewById(R.id.TF_weight);
        TV_result = (TextView)findViewById(R.id.TV_result);

        BT_calculate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                //判斷條件 身高 跟 體重 都有輸入值才執行
                if ( !("".equals(h.getText().toString()) || "".equals(w.getText().toString())) )
                {
                    float fh = Float.parseFloat(h.getEditableText().toString());      // 取得 身高輸入值
                    float fw = Float.parseFloat(w.getEditableText().toString());     // 取得 體重輸入值

                    fh = fh/100; // 計算BMI
                    fh = fh*fh;  // 計算BMI

                    NumberFormat nf = NumberFormat.getInstance();   // 數字格式
                    nf.setMaximumFractionDigits(2);                 // 限制小數第二位
                    // 計算BMI
                    TV_result.setText(nf.format(fw/fh) +"");           // 顯示BMI計算結果

                }
            }
        });
    }
}


