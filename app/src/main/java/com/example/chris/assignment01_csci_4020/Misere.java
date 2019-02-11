package com.example.chris.assignment01_csci_4020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Misere extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misere_layout);

        //---- Set up Onclick Listners ----//
        findViewById(R.id.reset_b).setOnClickListener(this);

        for (int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){
                //Below creates a new string that matches the button id
                //Then a variable for resource id
                String buttonID = "misereb_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                findViewById(resourceID).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
