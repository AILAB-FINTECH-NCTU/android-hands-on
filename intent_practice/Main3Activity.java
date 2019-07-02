package com.johnny12150.intent_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class Main3Activity extends AppCompatActivity {
    private Intent intent;
    private  Bundle bundle;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        intent = this.getIntent();
        bundle = intent.getExtras();
        et = (EditText)findViewById((R.id.p3_et));
        et.setText(bundle.getString("inputs3"));
        Button bt = (Button)findViewById((R.id.p3_bt));
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bundle.putString("outputs3", et.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                Main3Activity.this.finish();
            }
        });
    }
}
