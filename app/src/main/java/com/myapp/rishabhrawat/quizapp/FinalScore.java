package com.myapp.rishabhrawat.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FinalScore extends AppCompatActivity {

    DatabaseReference reference;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        textView=findViewById(R.id.textView);

        reference= FirebaseDatabase.getInstance().getReference().child("questionsBank1").child("UserScores");
        Intent intent=getIntent();
        textView.setText("Total score is "+     intent.getIntExtra("correct_ans",0));
        String random=reference.push().getKey(); //at place of random id we can put the gmail id
        Map<String,Object> map=new HashMap<>();
        map.put("score",intent.getIntExtra("correct_ans",0));
        reference.child(random).setValue(map);



    }
}
