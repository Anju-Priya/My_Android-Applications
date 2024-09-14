package com.example.testyourself;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BeTheVoice extends AppCompatActivity {

    TextView typing,quiz,brainriddle;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_the_voice);

        getSupportActionBar().setTitle("BeTheVoice");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typing = findViewById(R.id.Type);


        typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(BeTheVoice.this, "All The Best", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BeTheVoice.this, Typing.class);
                startActivity(intent);


            }
        });

        quiz = findViewById(R.id.QuIz);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BeTheVoice.this, "All The Best", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BeTheVoice.this, Quiz.class);
                startActivity(intent);
            }
        });

        brainriddle = findViewById(R.id.brainriddle);

        brainriddle.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BeTheVoice.this, "All The Best", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BeTheVoice.this,BrainRiddle.class);
                startActivity(intent);

            }
        }));


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    }


