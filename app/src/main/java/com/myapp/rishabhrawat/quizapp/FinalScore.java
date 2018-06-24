package com.myapp.rishabhrawat.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinalScore extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        textView=findViewById(R.id.textView);

        Intent intent=getIntent();
        textView.setText("Total score is "+     intent.getIntExtra("correct_ans",0));
    }
}
