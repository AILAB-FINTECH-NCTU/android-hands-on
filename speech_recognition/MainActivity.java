package com.johnny12150.speech_recognition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //設定辨識語言(這邊設定的是繁體中文)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-TW");
        //設定語音辨識視窗的內容
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
        startActivityForResult(intent, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用來儲存最後的辨識結果
        String firstMatch;
        if (requestCode == 1 && resultCode == RESULT_OK) {
        //取出多個辨識結果並儲存在String的ArrayList中
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            firstMatch = (String) result.get(0);
        } else {
            firstMatch = "無法辨識";
        }

        //開啟對話方塊
        //set.title:設定標題
        //setMessage:設定顯示訊息 這裡會顯示辨識的結果
        new AlertDialog.Builder(MainActivity.this).setTitle("語音辨識結果")
                .setIcon(android.R.drawable.ic_menu_search)
                .setMessage(firstMatch.toString())
                .setPositiveButton("OK", null).show();
    }

}
