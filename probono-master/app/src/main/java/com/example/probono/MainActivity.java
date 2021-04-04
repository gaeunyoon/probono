package com.example.probono;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.VisualVoicemailService;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     final ImageButton Ob = (ImageButton) findViewById(R.id.Ob);
     Ob.setOnClickListener((view)->{
         Snackbar.make(view, "주문하기", Snackbar.LENGTH_LONG)
                 .setAction("Action",null).show();

         VoiceTask voiceTask = new VoiceTask();
         voiceTask.execute();

     });


    }
    //구글 어시스턴트
    public class VoiceTask extends AsyncTask<String, Integer, String>{
        String str=null;


        protected String dolrBackground(String... params) {
        try {
            getVoice();
        } catch (Exception e) {
        }
        return str;
    }



        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String result){
        try{

        }catch (Exception e){
            Log.d("onActivityResult","getImageURL exception");

        }
    }
    }
    private void getVoice() {
        Intent intent = new Intent();
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        String language = "ko_KR";

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        startActivityForResult(intent, 2);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String str = results.get(0);
            Toast.makeText(getBaseContext(), str ,Toast.LENGTH_SHORT).show();

            TextView tv = findViewById(R.id.txvoice);
            tv.setText(str);
        }

    }

}
