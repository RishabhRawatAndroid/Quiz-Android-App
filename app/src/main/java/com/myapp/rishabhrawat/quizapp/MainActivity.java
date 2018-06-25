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
    int ques_no=0;
    static String ans_key;
    int total_ans_correct=0;

    CountDownTimer timercounter;
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
        timer.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        option_group.setVisibility(View.INVISIBLE);

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
                timer.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);
                option_group.setVisibility(View.VISIBLE);

               Setting_the_question(ques_no);
                new CountDownTimer(21000*questionModelArrayList.size(), 1000) {

                    public void onTick(long millisUntilFinished) {
                       // timer.setText("Time Remaining : "+String.valueOf(millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                      //  timer.setText("Time Finish");
                        startnewActivity();
                    }
                }.start();

            }
        });

        option_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.option_A_main)
                    ans_key = "a";
                else if (checkedId == R.id.option_B_main)
                    ans_key = "b";
                else if (checkedId == R.id.option_C_main)
                    ans_key = "c";
                else if (checkedId == R.id.option_D_main)
                    ans_key = "d";
                else
                    ans_key = " ";
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionModelArrayList.size()-1>ques_no) {
                    checkingans();
                    ques_no++;
                    Setting_the_question(ques_no);
                    option_group.clearCheck();
                }
                else {
                    startnewActivity();
                }
            }
        });

    }

    private void Setting_the_question(int ques) {
        question.setText(questionModelArrayList.get(ques).question);
        optionA.setText(questionModelArrayList.get(ques).optionA);
        optionB.setText(questionModelArrayList.get(ques).optionB);
        optionC.setText(questionModelArrayList.get(ques).optionC);
        optionD.setText(questionModelArrayList.get(ques).optionD);

        if(timercounter!=null)
            timercounter.cancel();

        timercounter=new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Remaining : "+String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timer.setText("Time Finish");
                //startnewActivity();
                if(ques_no<questionModelArrayList.size()-1)
                    Setting_the_question(++ques_no);
            }
        }.start();
    }

    private void checkingans()
    {
        if(ans_key.equals(questionModelArrayList.get(ques_no).answer)) {
         total_ans_correct++;
         Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show();
     }
     else
     {
         Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
     }// we can add also the -ve marking like total_ans_correct--;

    }

    private void startnewActivity()
    {
        Intent intent=new Intent(MainActivity.this,FinalScore.class);
        intent.putExtra("correct_ans",total_ans_correct);
        startActivity(intent);
    }
}
