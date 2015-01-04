package com.example.brian_000.morselistenandlearn;

/**
 * Created by brian_000 on 1/1/2015.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class CustomOnItemSelectedListener implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {


        Toast.makeText(parent.getContext(),
                "Changing To\n" + parent.getItemAtPosition(pos).toString() + "Position : "+ pos,
                Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}