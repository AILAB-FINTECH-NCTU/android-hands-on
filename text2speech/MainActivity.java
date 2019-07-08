package com.johnny12150.text2speech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech tts;
    Button b1;
    EditText et1;
    String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        et1 = findViewById(R.id.editText);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                s1 = et1.getText().toString();
                et1.setText("");
                tts = new TextToSpeech(MainActivity.this, ttsInitListener);
            }
        });

    }

    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                // 如果該語言資料不見了或沒有支援則無法使用
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
                    // 語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                    tts.setPitch((float) 0.5);
                    // 速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
                    tts.setSpeechRate((float) 0.5);
                    // 設定要說的內容文字
                    tts.speak(s1, TextToSpeech.QUEUE_FLUSH, null);
                }
            } else {
                Toast.makeText(MainActivity.this, "Initialization Failed!", Toast.LENGTH_LONG).show();
            }
        }
    };

}
