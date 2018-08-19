package com.myapp.rishabhrawat.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PutQuestionActivity extends AppCompatActivity {

    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button setbtn;
    EditText question, option_A, option_B, option_C, option_D;
    RadioGroup answer;
    int num;
    String answer_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_question);

        setbtn = findViewById(R.id.button);
        question = findViewById(R.id.question);
        option_A = findViewById(R.id.optionA);
        option_B = findViewById(R.id.optionB);
        option_C = findViewById(R.id.optionC);
        option_D = findViewById(R.id.optionD);
        answer = findViewById(R.id.radioGroup);

        //setting up the sharePreference for question series
        sharedPreferences = getApplicationContext().getSharedPreferences("questionseries", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        num = sharedPreferences.getInt("question_NO", 1);

        answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton)
                    answer_text="a";
                else if(checkedId==R.id.radioButton2)
                    answer_text="b";
                else if(checkedId==R.id.radioButton3)
                    answer_text="c";
                else if(checkedId==R.id.radioButton4)
                    answer_text="d";
                else{}
            }
        });



        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(option_A.getText().toString())||TextUtils.isEmpty(option_B.getText().toString())||TextUtils.isEmpty(option_C.getText().toString())||
                        TextUtils.isEmpty(option_D.getText().toString())||TextUtils.isEmpty(question.getText().toString()))
                {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(PutQuestionActivity.this);
                    dialog.setMessage("Please Fill the All option");
                    dialog.setTitle("Warning!!!");
                    dialog.setIcon(R.drawable.ic_warning_black_24dp);
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                } else {
                    put_question_in_database();
                }
            }
        });

    }

    private void put_question_in_database() {
        reference = FirebaseDatabase.getInstance().getReference().child("questionsBank1").child("question" + num);
        QuestionModel model=new QuestionModel(question.getText().toString(),option_A.getText().toString(),option_B.getText().toString(),
                option_C.getText().toString(),option_D.getText().toString(),answer_text);
        reference.setValue(model);
        editor.putInt("question_NO",++num);
        editor.commit();
        Toast.makeText(this,"Put question "+num+" successfull",Toast.LENGTH_LONG).show();
        question.setText("");
        option_A.setText("");
        option_B.setText("");
        option_C.setText("");
        option_D.setText("");

    }
}
