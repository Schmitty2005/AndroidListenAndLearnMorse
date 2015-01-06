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
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import androidmorse.AndroidMorse;


public class MainActivity extends ActionBarActivity {

    int mWPM = 42;
    int mFarnsWPM = 12;
    boolean mFarnsSpacingEnabled = false;
    AndroidMorse aMorse = new AndroidMorse(mWPM, mFarnsSpacingEnabled, mFarnsWPM, "!");

    int mAccuracy = 100;
    private final static  String KEY_ACCURACY = "accuracy";

    int mAccuracyThreshold = 93;
    int mAttempts = 1;
    private final static String KEY_ATTEMPTS = "attempts";
    int mAttemptMAX = 30;

    int mCorrectGuess = 1;
    private final static String KEY_CORRECT = "correct";

    int mCurrentLevel = 0;
    private final static String KEY_LEVEL = "level";

    int mLevelMax = 9;
    int mLevelThreshold = 35; //number of attempts before level change

    String mPlayString = aMorse.levelSets.get(1);
    String mPlayLevelString = mPlayString.toLowerCase();
    //int mCurrentLevel = 1;
    private Spinner spinnerLevel;

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_ACCURACY, mAccuracy);
        savedInstanceState.putInt(KEY_ATTEMPTS, mAttempts);
        savedInstanceState.putInt(KEY_CORRECT, mCorrectGuess);
        savedInstanceState.putInt(KEY_LEVEL, mCurrentLevel);
    }

    public class CustomOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {


            Toast.makeText(parent.getContext(),
                    "Changing To\n" + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_LONG).show();
            mCurrentLevel = pos;
           setLevel();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);

        if(savedInstanceState != null){
            mAccuracy = savedInstanceState.getInt(KEY_ACCURACY, 100);
            mAttempts = savedInstanceState.getInt(KEY_ATTEMPTS, 1);
            mCurrentLevel = savedInstanceState.getInt(KEY_LEVEL, 0);
            mCorrectGuess = savedInstanceState.getInt(KEY_CORRECT, 0);
        }

        List<String> list = new ArrayList<>();
        list.add("Level 1");
        list.add("Level 2");
        list.add("Level 3");
        list.add("Level 4");
        list.add("Review 1-4");
        list.add("Level 5");
        list.add("Level 6");
        list.add("Level 7");
        list.add("Morse Master");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        //addListenerOnButton();
        addListenerReplayButton();

        spinnerLevel.setAdapter(dataAdapter);


        shuffleSetButtons();

        //Button btnReplay = (Button) (findViewById(R.id.buttonReplay));


        //set display character to random
        TextView charDisplay = (TextView) (findViewById(R.id.viewCharPlaying));
        double random = Math.random() * mPlayLevelString.length();
        char mChar = mPlayLevelString.charAt((int) random);
        mChar = Character.toUpperCase(mChar);
        charDisplay.setText(Character.toString(mChar));


        TextView accuracyDisplay = (TextView) (findViewById(R.id.textAccuracy));
        accuracyDisplay.setText(String.format("%s%%", String.valueOf(mAccuracy)));
        TextView attemptsDisplay = (TextView) (findViewById(R.id.textAttempts));
        attemptsDisplay.setText(String.valueOf(mAttempts));

        // mAccuracy = mCorrectGuess/mAttempts;
        //TODO add routine to ad color to accuracy.  Green from >94% Orange for >80% and < 94% and red for <79%

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



/*
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
*/
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
    public void addListenerReplayButton() {

        Button btnReplay = (Button) findViewById(R.id.buttonReplay);
        btnReplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView replayView = (TextView) findViewById(R.id.viewCharPlaying);
                CharSequence mChrSeq = replayView.getText();
                String mString = mChrSeq.toString();
                AndroidMorse aMorse;
                aMorse = new AndroidMorse(mWPM, mFarnsSpacingEnabled, mFarnsWPM, mString);
                //TODO Replay audio code here
                byte playByte[] = aMorse.morseWaveByteArray;
                AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                        AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                        playByte.length, AudioTrack.MODE_STATIC);
                int write = at.write(playByte, 44, (playByte.length - 44));
                if (write < 0) {
                    Toast.makeText(MainActivity.this,
                            "Error playing wave : " +
                                    "\n" + String.valueOf(write),
                            Toast.LENGTH_LONG).show();

                }
                at.setPlaybackHeadPosition(0);
                at.play();


            }
        });

    }


    public void onClickGuess(View v) {


        //final String TAG = "onClickGuess has ben called";


        TextView mAnswerView = (TextView) findViewById(R.id.viewCharPlaying);

        CharSequence mAnswerSeq = mAnswerView.getText();
        String mAnswerString = mAnswerSeq.toString();
        mAnswerString = mAnswerString.toUpperCase();

        Character mAnswer = mAnswerString.charAt(0);

        Button buttonPressed = (Button) findViewById(v.getId());
        CharSequence mGuessedChar = buttonPressed.getText();
        Character mGuessed = mGuessedChar.charAt(0);


        //TODO add wrong notification
        if (mGuessed == mAnswer) {
            mAttempts++;
            mCorrectGuess++;
            if (mAttempts > mLevelThreshold && mAccuracy > 95) {
                mCurrentLevel++;
                setLevel();
            }
            //TODO add some sort of notification for correct answer given

            double random = Math.random() * mPlayLevelString.length();
            char mChar = mPlayLevelString.charAt((int) random);
            mChar = Character.toUpperCase(mChar);
            mAnswerView.setText(Character.toString(mChar));

            //TODO code for playLevelStrings longer than 6 characters

        } else mAttempts++;


        mAccuracy = (int) (((double) mCorrectGuess / mAttempts) * 100);

        TextView accuracyDisplay = (TextView) (findViewById(R.id.textAccuracy));
        accuracyDisplay.setText(String.format("%s%%", String.valueOf(mAccuracy)));
        TextView attemptsDisplay = (TextView) (findViewById(R.id.textAttempts));
        attemptsDisplay.setText(String.valueOf(mAttempts));

        //Set colors for visual clue of accuracy -NOTE: May change to icon!
        if (mAccuracy > 94) {
            accuracyDisplay.setTextColor(getResources().getColor(R.color.GREEN));
        }
        if (mAccuracy < 94 && mAccuracy > 75) {
            accuracyDisplay.setTextColor(getResources().getColor(R.color.ORANGE));
        }
        if (mAccuracy < 74) {
            accuracyDisplay.setTextColor(getResources().getColor(R.color.RED));
        }

        //increase level variable after threshold;


        //replay changed character
        replayView();

        //Turn of Display if user is doing well

        if (mAttempts > mAttemptMAX && mAccuracy > mAccuracyThreshold)
        //mAnswerView.setEnabled(false);
        {
            mAnswerView.setVisibility(View.INVISIBLE);
        }
        if (mAccuracy < mAccuracyThreshold) {
            mAnswerView.setVisibility(View.VISIBLE);
        }


    }

    public void replayView() {
        TextView replayView = (TextView) findViewById(R.id.viewCharPlaying);
        CharSequence mChrSeq = replayView.getText();
        String mString = mChrSeq.toString();
        AndroidMorse aMorse;
        aMorse = new AndroidMorse(mWPM, mFarnsSpacingEnabled, mFarnsWPM, mString);
        //TODO Replay audio code here
        byte playByte[] = aMorse.morseWaveByteArray;
        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                playByte.length, AudioTrack.MODE_STATIC);
        int write = at.write(playByte, 44, (playByte.length - 44));
        if (write < 0) {
            Toast.makeText(MainActivity.this,
                    "Error playing wave : " +
                            "\n" + String.valueOf(write),
                    Toast.LENGTH_LONG).show();

        }
        at.setPlaybackHeadPosition(0);
        at.play();
    }

    private void shuffleSetButtons() {
        mPlayLevelString = shuffle(mPlayLevelString);
        //define buttons from UI
        Button btnGuess1 = (Button) (findViewById(R.id.buttonGuess1));
        Button btnGuess2 = (Button) (findViewById(R.id.buttonGuess2));
        Button btnGuess3 = (Button) (findViewById(R.id.buttonGuess3));
        Button btnGuess4 = (Button) (findViewById(R.id.buttonGuess4));
        Button btnGuess5 = (Button) (findViewById(R.id.buttonGuess5));
        Button btnGuess6 = (Button) (findViewById(R.id.buttonGuess6));
        //Set View Buttons to characters
        btnGuess1.setText(Character.toString(mPlayLevelString.charAt(0)));
        btnGuess2.setText(Character.toString(mPlayLevelString.charAt(1)));
        btnGuess3.setText(Character.toString(mPlayLevelString.charAt(2)));
        btnGuess4.setText(Character.toString(mPlayLevelString.charAt(3)));
        btnGuess5.setText(Character.toString(mPlayLevelString.charAt(4)));
        btnGuess6.setText(Character.toString(mPlayLevelString.charAt(5)));


    }

    public String shuffle(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        String caseConvert = output.toString();
        input = caseConvert.toUpperCase();

        return input;
        // System.out.println(output.toString());
    }

    public void setLevel() {
        //TODO this is causing a problem when rotated.....mAttempts is automatically set to one
        //each time!
        mAttempts = 1;
        mCorrectGuess = 1;
        //mCurrentLevel++;
        this.spinnerLevel.setSelection(mCurrentLevel);

        String convert = aMorse.levelSets.get(mCurrentLevel+1);
        mPlayLevelString = convert.toLowerCase();

        if (mCurrentLevel == 4) {
            //TODO code for level 4 play string levels 1 - 4 combined
        }

        if (mCurrentLevel == 8) {
            //TODO code for review of levels 5-8
        }
        if (mCurrentLevel == mLevelMax) {
            //TODO code for max level reached
        }
        //TODO add code for level 4 review and level 5-8 review with level 9 "morse master" all characters!
       // String interString = aMorse.levelSets.get(mCurrentLevel);
       // mPlayLevelString = interString.toLowerCase();

        TextView mAnswerView = (TextView) findViewById(R.id.viewCharPlaying);
        double random = Math.random() * mPlayLevelString.length();
        char mChar = mPlayLevelString.charAt((int) random);
        mChar = Character.toUpperCase(mChar);
        mAnswerView.setText(Character.toString(mChar));

        shuffleSetButtons();


    }

}


