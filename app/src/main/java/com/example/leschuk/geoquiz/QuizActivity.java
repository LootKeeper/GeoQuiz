package com.example.leschuk.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueBtn;
    private Button mFalseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.mTrueBtn = findViewById(R.id.true_btn);
        this.mFalseBtn = findViewById(R.id.false_btn);

        this.mTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast toast = Toast.makeText(
                        QuizActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT);

               toast.setGravity(Gravity.TOP, 0,0);
               toast.show();

            }
        });
        this.mFalseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(
                        QuizActivity.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        });


    }
}
