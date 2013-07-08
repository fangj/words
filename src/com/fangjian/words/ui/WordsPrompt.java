package com.fangjian.words.ui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import com.fangjian.words.R;
import com.fangjian.words.lib.DictItem;
import com.fangjian.words.lib.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fangjian on 13-7-6.
 */
public class WordsPrompt extends Activity {
    final Activity me=this ;
    private TextView tvWord;
    private TextView tvExplain;
    private Parser test;
    private ArrayList<DictItem> dictItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_prompt);
        //dissmiss the dialog in 5s.
        startTimer();
        //click btn to do something
        //Button btn= (Button) findViewById(R.id.button);
        //bindBtn(btn);
        //prepare text
        tvWord= (TextView) findViewById(R.id.textWord);
        tvExplain=(TextView)findViewById(R.id.textExplain);


        //getprop
        test=new Parser(this);
        try {
            //Properties prop=getProp("d.ifo");
            Properties prop=test.getprop("d.ifo");
           String wordcount=prop.getProperty("wordcount");
            //tvExplain.setText(wordcount);
            dictItems=test.readDictIdx("d.idx");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set text
        setText();

    }

    private  Properties getProp(String filename) throws IOException {
        //read file
        AssetManager assets=getAssets();
        InputStream is=assets.open(filename);
        Properties prop = new Properties();
        prop.load(is);
        return prop;
        //prop.list(System.out);
    }
/*
    private void bindBtn(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                me.finish();
            }
        });
        //this.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;

    }
    */
    private  void startTimer(){

        Timer timer = new Timer( );

        TimerTask task = new TimerTask( ) {
            public void run ( ) {
                me.finish();
            }
        };
        timer.schedule(task,5000);
    }

    private void setText(){
        //tvWord.setText("Hello");
//        tvExplain.setText("world");
        int index=(int)(Math.random()*dictItems.size());
        DictItem di=dictItems.get(index);
        tvWord.setText(di.getWord());
        try {
            String content=test.getContent("d.dict",di.getOffset(),di.getLength());
            tvExplain.setText(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
