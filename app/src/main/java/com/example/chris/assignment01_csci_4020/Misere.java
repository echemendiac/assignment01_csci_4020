package com.example.chris.assignment01_csci_4020;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
                Button b = findViewById(resourceID);
                b.setOnClickListener(this);
                //Set the background of each button to a dark background image
                b.setBackgroundResource(R.drawable.darkbackground);
            }
        }
    }

    @Override
    public void onClick(View v) {
        for (int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){
                //Below creates a new string that matches the button id
                //Then a variable for resource id
                String buttonID = "misereb_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                // get the clicked button and create a resembling object
                Button b = findViewById(resourceID);

                //check which button got clicked
                //then depending on who's turn it is set the background for x or o
                //set the parallel array to match
                if (v.getId() == resourceID) {
                    if (player1Turn) {
                        b.setBackgroundResource(R.drawable.xx);
                        buttonArray[i][j] = 1;
                    } else
                        b.setBackgroundResource(R.drawable.oo);
                    buttonArray[i][j] = 2;
                }
            }
        }
        // checkwin is an algorithm for tic tac toe because this is misere
        // reverse the winner.
        turnCount++; //show that a turn has been taking

        //check if someone won after grid is filled game is a draw
        //if no one has won then switch turns
        if(checkWin()){
            if(player1Turn) {
                player2Wins();
            }else {
                player1Wins();
            }
        }else if (turnCount == 9){
            playerDraw();
        }else{
            //switch turns
            player1Turn = !player1Turn;
        }

    }

    /**
     * This function checks for a tic tac toe win
     * @precondition buttonArray must be initialized
     * @postcondition none
     * @return true or false if a win is detected
     */
    private boolean checkWin() {
        int[][] field = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttonArray[i][j];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == (field[i][1])
                    && field[i][0] == (field[i][2])
                    && !(field[i][0] == 0)) {
                return true;
            }
            if (field[0][i] == (field[1][i])
                    && field[0][i] == (field[2][i])
                    && !(field[0][i] == 0)) {
                return true;
            }
            if (field[0][0] == (field[1][1])
                    && field[0][0] == (field[2][2])
                    && !(field[0][0] == 0)) {
                return true;
            }
            if (field[0][2] == (field[1][1])
                    && field[0][2] == (field[2][0])
                    && !(field[0][2] == 0)) {
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
    }
}

