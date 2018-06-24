package com.myapp.rishabhrawat.quizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    ArrayList<QuestionModel> questionModelArrayList;
    DatabaseReference reference;
    TextView timer,question;
    RadioGroup option_group;
    RadioButton optionA,optionB,optionC,optionD;
    Button start,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=findViewById(R.id.timer);
        question=findViewById(R.id.question_main);
        option_group=findViewById(R.id.option_group_main);
        optionA=findViewById(R.id.option_A_main);
        optionB=findViewById(R.id.option_B_main);
        optionC=findViewById(R.id.option_C_main);
        optionD=findViewById(R.id.option_D_main);
        start=findViewById(R.id.button3);
        next=findViewById(R.id.button2);

        next.setVisibility(View.INVISIBLE);
        questionModelArrayList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference().child("questionsBank1");
        //this call back run only a single time
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    QuestionModel question = snapshot.getValue(QuestionModel.class);
                    questionModelArrayList.add(question);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                Setting_the_question();
                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("Time Remaining : "+String.valueOf(millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        timer.setText("Time Finish");
                    }
                }.start();

            }
        });


    }

    private void Setting_the_question() {

        question.setText(questionModelArrayList.get(0).question);
        optionA.setText(questionModelArrayList.get(0).optionA);
        optionB.setText(questionModelArrayList.get(0).optionB);
        optionC.setText(questionModelArrayList.get(0).optionC);
        optionD.setText(questionModelArrayList.get(0).optionD);
    }
}
