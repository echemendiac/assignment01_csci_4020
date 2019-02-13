package com.example.chris.assignment01_csci_4020;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Misere extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3]; //grid of buttons
    private int[][] buttonArray= {
        {0,0,0},
        {0,0,0},
        {0,0,0}
    };
    ; // parallel array for buttons
    // 0 for not set
    //1 for x
    //2 for y

    private int turnCount = 0; // keeps track of how many times a turn is played
    private boolean player1Turn = true;
    private TextView turn_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misere_layout);
        turn_tv = findViewById(R.id.misereTurn_tv);;

        for (int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){
                //Below creates a new string that matches the button id
                //Then a variable for resource id
                String buttonID = "misereb_" + i +""+ j;

                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resourceID);
                buttons[i][j].setOnClickListener(this);
                //Set the background of each button to a dark background image
                buttons[i][j].setBackgroundResource(R.drawable.darkbackground);
            }
        }

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            Log.i("misere","instance is finally not null");

            if ((savedInstanceState != null) && (savedInstanceState.getSerializable("moves") != null)) {
                Log.i("misere","instance was not null and moves not null");
                buttonArray = (int[][]) savedInstanceState.getSerializable("moves");

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(buttonArray[i][j] == 1){
                            buttons[i][j].setBackgroundResource(R.drawable.xx);
                        }
                        else if(buttonArray[i][j] == 2){
                            buttons[i][j].setBackgroundResource(R.drawable.oo);
                        }
                    }
                }

            }
            if ((savedInstanceState != null) && (savedInstanceState.getSerializable("turn") != null)) {
                Log.i("misere","instance was not null and turn not null");
                player1Turn = (boolean) savedInstanceState.getSerializable("turn");
                if(player1Turn)
                    turn_tv.setText("Player 1's turn");
                else
                    turn_tv.setText("Player 2's turn");
            }
            if ((savedInstanceState != null) && (savedInstanceState.getSerializable("numTurns") != null)) {
                Log.i("misere","instance was not null and numTurns not null");
                turnCount = (int) savedInstanceState.getSerializable("numTurns");
            }
        }
        else{
            Log.i("misere","instance was null");
            turn_tv.setText("Player 1 turn");
        }
        //---- Set up Onclick Listners ----//

        //reset button
        findViewById(R.id.misereReset_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("misere","reset button was clicked");
                resetGame();
            }
        });

        //rules button
        findViewById(R.id.misereRules_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("misere","rules button was clicked");

                //Creating textView to be used with toast
                TextView tv = new TextView(getApplicationContext());
                tv.setText("Player 1 is X.\n"
                + "Player 2 is O.\n"
                + "Same rules as Tic Tac Toe\n"
                + "except object of game is not to get three in a row.\n"
                + "First person to get three in a row,\n"
                + "horzontally, veritically, or diagonal, loses.");
                tv.setTextSize(20);
                tv.setPadding(25,25,25,25);
                tv.setBackgroundColor(Color.WHITE);
                tv.setTextColor(Color.BLACK);

                //Toast created and set with textView so that the toast is custom
                Toast toast = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setView(tv);
                toast.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Log.i("misere","button was clicked");
        for (int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){

                //check which button got clicked
                //then depending on who's turn it is set the background for x or o
                //set the parallel array to match
                Button b = buttons[i][j];
                if (v == b) {
                    Log.i("misere","["+i+"]["+j+"]");
                    if (player1Turn) {
                        if(buttonArray[i][j] == 0) {
                            b.setBackgroundResource(R.drawable.xx);
                            buttonArray[i][j] = 1;
                        }
                        else{
                            turn_tv.setText("Space is already played");
                            return;
                        }
                    } else {
                        if(buttonArray[i][j] == 0) {
                            b.setBackgroundResource(R.drawable.oo);
                            buttonArray[i][j] = 2;
                        }
                        else{
                            turn_tv.setText("Space is already played");
                            return;
                        }
                    }
                }
            }
        }
        // checkwin is an algorithm for tic tac toe because this is misere
        // reverse the winner.
        turnCount++; //show that a turn has been taking

        //check if someone won after grid is filled game is a draw
        //if no one has won then switch turns
        if(checkWin()){
            Log.i("misere","someone won game");
            if(player1Turn) {
                Log.i("misere","Player 1 wins");
                player2Wins();
            }else {
                Log.i("misere","Player 2 wins");
                player1Wins();

            }
        }else if (turnCount == 9){
            playerDraw();
        }else{
            //switch turns
            player1Turn = !player1Turn;
            if(player1Turn)
                turn_tv.setText("Player 1's turn");
            else
                turn_tv.setText("Player 2's turn");
        }

    }

    /**
     * This function checks for a tic tac toe win
     * @precondition buttonArray must be initialized
     * @postcondition none
     * @return true or false if a win is detected
     */
    private boolean checkWin() {
        int[][] grid = new int[3][3];

        //basic 2D array copy
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = buttonArray[i][j];
                Log.i("misere","grid["+i+"]["+j+"] = "+grid[i][j]);
            }
        }
        

        for (int i = 0; i < 3; i++) {

            //checks rows win
            if (grid[i][0] == (grid[i][1])
                    && grid[i][0] == (grid[i][2])
                    && !(grid[i][0] == 0)) {
                return true;
            }
            //checks column win
            if (grid[0][i] == (grid[1][i])
                    && grid[0][i] == (grid[2][i])
                    && !(grid[0][i] == 0)) {
                return true;
            }
            //check diagonal top left corner
            if (grid[0][0] == (grid[1][1])
                    && grid[0][0] == (grid[2][2])
                    && !(grid[0][0] == 0)) {
                return true;
            }
            //check diagonal
            if (grid[0][2] == (grid[1][1])
                    && grid[0][2] == (grid[2][0])
                    && !(grid[0][2] == 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function gets called if player 1 wins
     * It resets the board and displays a toast
     */
    private void player1Wins(){
        Toast.makeText(this,"Player 1 wins!",Toast.LENGTH_LONG).show();
        resetGame();
    }

    /**
     * This function gets called if player 2 wins
     * It resets the board and displays a toast
     */
    private void player2Wins(){
        Toast.makeText(this,"Player 2 wins!",Toast.LENGTH_LONG).show();
        resetGame();
    }

    /**
     * This function gets called if no player wins
     * It resets the board and displays a toast
     */
    private void playerDraw(){
        Toast.makeText(this,"Draw",Toast.LENGTH_LONG).show();
        resetGame();
    }

    /**
     * This function resets the turn, game, and turn count
     */
    private void resetGame(){
        player1Turn = true;// set player 1 turn back

        //reset board
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setBackgroundResource(R.drawable.darkbackground);
                buttonArray[i][j] = 0;
            }
        }

        //reset number of turns
        turnCount =0;

        //reset text view
        turn_tv.setText("Player 1's turn");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("misere","in the on save instance");
        savedInstanceState.putSerializable("moves", buttonArray);
        savedInstanceState.putSerializable("turn", player1Turn);
        savedInstanceState.putSerializable("numTurns", turnCount);
        Log.i("misere","values saved");
    }
}

