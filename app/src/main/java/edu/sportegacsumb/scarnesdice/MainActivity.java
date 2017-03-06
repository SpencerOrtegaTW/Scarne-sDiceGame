package edu.sportegacsumb.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.view.View;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int WIN = 100;
    int SCORE_MAX = 20;
    int userScore = 0;
    int computerScore = 0;
    int uTurn = 0;
    int cTurn = 0;
    TextView scoreText;
    ImageView image;
    Button roll, hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = (TextView)findViewById(R.id.scoreText);
        image = (ImageView) findViewById(R.id.diceImage);

        roll = (Button) findViewById(R.id.rollBtn);
        hold = (Button) findViewById(R.id.holdBtn);
    }

    public void disableBttns(){
        // hides roll and hold button
        roll.setVisibility(View.INVISIBLE);
        hold.setVisibility(View.INVISIBLE);

    }
    public void enableBttns(){
        // shows roll and hold button
        roll.setVisibility(View.VISIBLE);
        hold.setVisibility(View.VISIBLE);

    }
    // user presses hold score
    public void holdScore(View view){

        if(uTurn != 0){
            userScore += uTurn;
            uTurn = 0;
            scoreText.setText("Your score: "+ userScore +"\nComputer score: " + computerScore + "\nComputer turn: ");
            image.setImageResource(R.mipmap.ic_launcher);
            disableBttns();
            computerTurn();
        }
    }
    // return id of drawable picture
    public int dicePic(int i){
        int pic = 0;
        if(i == 1)
        {
            pic = R.drawable.dice1;
        }
        else if(i == 2)
        {
            pic = R.drawable.dice2;
        }
        else if(i == 3)
        {
            pic = R.drawable.dice3;
        }
        else if(i == 4)
        {
            pic = R.drawable.dice4;
        }
        else if(i == 5)
        {
            pic = R.drawable.dice5;
        }
        else if(i == 6)
        {
            pic = R.drawable.dice6;
        }
        return pic;
    }
    // user rolls
    public void rollDie(View view){

        // user presses roll!

        // generate random number from 1 - 6
        Random rand = new Random();
        int i = rand.nextInt(6) + 1;

        // output dice image
        image.setImageResource(dicePic(i));

        // user rolled 1
        if (i == 1) {
            // reset user temp score
            uTurn = 0;
            // output new results
            scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nUser roll: 1 :(");
            image.setImageResource(R.mipmap.ic_launcher);

            // disable buttons
            disableBttns();
            // run computers turn
            computerTurn();
        }

        // user rolled valid score
        else {
            uTurn += i;
            if(uTurn + userScore >= WIN){
                // user has won
                disableBttns();
                scoreText.setText("Your score: "+ (uTurn+userScore) + "\nComputer score: " + computerScore +"\nUser has won!");
                image.setImageResource(R.mipmap.ic_launcher);

            }
            else{
                // user can still roll
                scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nUser roll: " + uTurn);
            }

        }
    }
    // computer rolls
    public void computerTurn() {
        // handler to delay process
        final Handler handler = new Handler();

        final Runnable run = new Runnable() {
            public void run() {

                // generate random number from 1 - 6
                Random rand = new Random();
                int i = rand.nextInt(6) + 1;

                // display drawable
                image.setImageResource(dicePic(i));

                if (i == 1) {
                    // computer rolled 1
                    cTurn = 0;

                    // output results
                    scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nComputer roll: 1 :(");

                    image.setImageResource(R.mipmap.ic_launcher);


                    // enable buttons for user
                    enableBttns();
                }
                else {
                    // computer rolled valid
                    cTurn += i;

                    // computer has won
                    if(cTurn + computerScore >= WIN) {
                        // output results
                        computerScore += cTurn;
                        scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nComputer has won!");
                        image.setImageResource(R.mipmap.ic_launcher);

                    }

                    // can not hold score yet: keep rolling till max
                    else if(cTurn < SCORE_MAX){

                        // delay
                        // then run again
                        scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nComputer roll: " + cTurn);
                        handler.postDelayed(this,1000);
                    }
                    // hold score
                    else{

                        // add score
                        computerScore += cTurn;
                        cTurn = 0;

                        // output result
                        scoreText.setText("Your score: "+ userScore + "\nComputer score: " + computerScore +"\nUser roll: ");
                        image.setImageResource(R.mipmap.ic_launcher);

                        // users turn
                        enableBttns();
                    }
                }
            }
        };
        handler.postDelayed(run,1000);

    }
    // reset game play
    public void resetGame(View views){

        userScore = 0;
        computerScore = 0;
        uTurn = 0;
        cTurn = 0;
        scoreText.setText("Lets start!");
        enableBttns();
        image.setImageResource(R.mipmap.ic_launcher);
    }

}
