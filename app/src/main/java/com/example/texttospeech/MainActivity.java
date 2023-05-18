package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    TextView tv1;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        tv1 = findViewById(R.id.tv1);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS)
                    tts.setLanguage(Locale.KOREAN);
                else {
                    Toast.makeText(getApplicationContext(), "초기화 실패", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                if (s.equals("idTTS"))
                    tv1.setText("입력내용을 음성변환중...");
                if (s.equals("idSmartIT"))
                    tv1.setText("스마트 IT 소개중 ...");
            }
            @Override
            public void onDone(String s) {
                tv1.setText("성공적으로 변환완료...");
            }
            @Override
            public void onError(String s) {
                tv1.setText("음성 출력 실패...");
            }
        });

        Button btn1 = findViewById(R.id.btnTTS);
        Button btn2 = findViewById(R.id.btnSmartIT);
        Button btn3 = findViewById(R.id.btnFinish);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et1.getText().toString();
                tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, "idTTS");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = getString(R.string.smartIT);
                et1.setText(str);
                tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, "idSmartIT");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
                tts.shutdown();
                finish();
            }
        });
    }
}