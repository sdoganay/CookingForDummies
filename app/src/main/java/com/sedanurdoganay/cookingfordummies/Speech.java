package com.sedanurdoganay.cookingfordummies;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by bengisukose on 5/10/16.
 */
public class Speech implements TextToSpeech.OnInitListener {
    String  text;
    Context con;
    Button playButton;
    static TextToSpeech tts;

    public Speech ( String text, Context con, Button playButton){
        text = this.text;
        con = this.con;playButton = this.playButton;
        //tts = new TextToSpeech(con,this);
        final Button finalPlayButton = playButton;
       /* playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (v == playButton)
                    speakOut();
            }
        });*/




    }
    public void instalTTS(){
        tts = new TextToSpeech(con, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        playButton.setEnabled(true);
                        speakOut();
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });
    }

   /* @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                playButton.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }*/
    public void speakOut(){
        String text1 = text.toString();

        tts.speak(text1, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {

    }
}
