package com.example.testyourself;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz extends AppCompatActivity {

    TextView tv_question;
    Button b_true, b_false;
    Questions mQuestions;
    int questionsLength;
    ArrayList<Item> questionsList;
    int currentQuestion = 0;
    boolean winner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tv_question =findViewById(R.id.tv_question);
        b_true =findViewById(R.id.b_true);
        b_false =findViewById(R.id.b_false);
        mQuestions = new Questions();
        questionsLength = mQuestions.mQuestions.length;
        questionsList = new ArrayList<>();
        //save all the questions in the list
        for (int i=0; i<questionsLength; i++) {
            questionsList.add(new Item(mQuestions.getQuestion(i),mQuestions.getAnswer(i)));
        }
        //shuffle the questions
        Collections.shuffle(questionsList);
        //Start the game
        setQuestion(currentQuestion);

        b_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkQuestions(currentQuestion)) {
                    //Correct - the game continues
                    currentQuestion++;
                    if (currentQuestion<questionsLength) {
                        setQuestion(currentQuestion);
                    } else {
                        //Game over - winner
                        winner = true;
                        endGame();
                    }
                } else {
                    //Wrong - the game over
                    endGame();
                }
            }
        });

        b_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkQuestions(currentQuestion)){
                    currentQuestion++;
                    if(currentQuestion<questionsLength){
                        setQuestion(currentQuestion);
                    } else{
                        winner=false;
                        endGame();
                    }
                } else {
                    endGame();
                }

            }
        });
    }

    //Show question on the screen
    private void setQuestion(int number) {
        tv_question.setText(questionsList.get(number).getQuestion());
    }
    //check if the is right
    private boolean checkQuestions(int number) {
        String answer = questionsList.get(number).getAnswer();
        return  answer.equals("true");
    }

    //Game over
    private void endGame() {
        if (winner) {
            Toast.makeText(this, "Game Over! You win! ", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Game Qver! You lose", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}