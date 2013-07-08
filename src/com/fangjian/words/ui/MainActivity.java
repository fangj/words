package com.fangjian.words.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.fangjian.words.R;
import com.fangjian.words.services.WordsService;

public class MainActivity extends Activity {

    private Button btn;
    private Activity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = this;
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean started = WordsService.isStarted();
                if(started){
                    stopWordsService();
                }else{
                    startWordsService();
                }
                setButtonText();
                me.finish();
            }
        });
    }

    private void setButtonText() {
        String text = WordsService.isStarted() ? "STOP" : "START";
        btn.setText(text);
    }

    private void startWordsService() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), WordsService.class);
        startService(intent);
    }

    private void stopWordsService() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), WordsService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        setButtonText();
        super.onResume();
    }
}
