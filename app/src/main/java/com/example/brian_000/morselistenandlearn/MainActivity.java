package com.example.brian_000.morselistenandlearn;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//custom jar file AndroidMorse
import java.util.ArrayList;
import java.util.List;

import androidmorse.AndroidMorse;


public class MainActivity extends ActionBarActivity {
    AndroidMorse aMorse = new AndroidMorse(18, true, 13, "Morse Test w7dk");
    double mAccuracy = 100;
    int mAttempts = 0;
    int mCorrectGuess = 0;
    private Button btnSubmit;
    private Spinner spinnerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        List<String> list = new ArrayList<String>();
        list.add("Level 1");
        list.add("Level 2");
        list.add("Level 3");
        list.add("Level 4");
        list.add("Level 5");
        list.add("Level 6");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        addListenerOnButton();
        addListenerReplayButton();

        spinnerLevel.setAdapter(dataAdapter);

        Button btnGuess1 = (Button) (findViewById(R.id.buttonGuess1));
        Button btnGuess2 = (Button) (findViewById(R.id.buttonGuess2));
        Button btnGuess3 = (Button) (findViewById(R.id.buttonGuess3));
        Button btnGuess4 = (Button) (findViewById(R.id.buttonGuess4));
        Button btnGuess5 = (Button) (findViewById(R.id.buttonGuess5));
        Button btnGuess6 = (Button) (findViewById(R.id.buttonGuess6));

        Button btnReplay = (Button) (findViewById(R.id.buttonReplay));


        btnGuess1.setText("A");
        btnGuess2.setText("B");
        btnGuess3.setText("C");
        btnGuess4.setText("D");
        btnGuess5.setText("E");
        btnGuess6.setText("F");


        TextView charDisplay = (TextView) (findViewById(R.id.viewCharPlaying));
        charDisplay.setText("A");
        TextView accuracyDisplay = (TextView) (findViewById(R.id.textAccuracy));
        accuracyDisplay.setText(String.format("%s%%", String.valueOf(mAccuracy)));
        TextView attemptsDisplay = (TextView) (findViewById(R.id.textAttempts));
        attemptsDisplay.setText(String.valueOf(mAttempts));

//============
//Code to play wave
        byte playByte[] = aMorse.morseWaveByteArray;
        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                playByte.length, AudioTrack.MODE_STATIC);
        int write = at.write(playByte, 44, (playByte.length - 44));
        at.play();
// end of code to play wave
//==============

        return true;
    }

    public void addListenerOnSpinnerItemSelection() {

        spinnerLevel.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //get the selected dropdown list value
public void addListenerReplayButton(){

    Button btnReplay = (Button)findViewById(R.id.buttonReplay);
   btnReplay.setOnClickListener(new View.OnClickListener(){

       @Override
   public void onClick(View v){

           //TODO Replay audio code here
           byte playByte[] = aMorse.morseWaveByteArray;
           AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                   AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                   playByte.length, AudioTrack.MODE_STATIC);
           int write = at.write(playByte, 44, (playByte.length - 44));
           if (write < 0){
               Toast.makeText(MainActivity.this,
                       "Error playing wave : " +
                               "\n" + String.valueOf(write),
                       Toast.LENGTH_LONG).show();

           }
           at.play();


       }
   });

}
    public void addListenerOnButton() {

        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,
                        "On Button Click : " +
                                "\n" + String.valueOf(spinnerLevel.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
            }

        });


    }
}


