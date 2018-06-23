package com.myapp.rishabhrawat.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    DatabaseReference reference1;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference1= FirebaseDatabase.getInstance().getReference().child("questionsBank2").child("question1");
        QuestionModel model1=new QuestionModel("Which of these class is superclass of String and StringBuffer class?",
                "java.util",
                "java.lang",
                "ArrayList",
                "None of the mentioned",
                "b");
        reference1.setValue(model1);

        reference2= FirebaseDatabase.getInstance().getReference().child("questionsBank2").child("question2");
        QuestionModel model2=new QuestionModel("Which of these method of class String is used to obtain length of String object?",
                "get()",
                "Sizeof()",
                "lengthof()",
                "length()",
                "c");
        reference2.setValue(model2);

        Toast.makeText(this, "Inserted data", Toast.LENGTH_SHORT).show();

    }
}
