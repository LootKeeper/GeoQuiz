package com.example.leschuk.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueBtn;
    private Button mFalseBtn;
    private ImageButton mPrevBtn;
    private ImageButton mNextBtn;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.mTrueBtn = findViewById(R.id.true_btn);
        this.mFalseBtn = findViewById(R.id.false_btn);

        this.mQuestionTextView = findViewById(R.id.question_text_view);
        this.mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        updateQuestion();

        this.mTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(true);

            }
        });
        this.mFalseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        this.mPrevBtn = findViewById(R.id.prev_btn);
        this.mPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevQuestion();
            }
        });

        this.mNextBtn = findViewById(R.id.next_btn);
        this.mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

    }

    private void nextQuestion(){
        this.updateQuestionIndex(1);
        this.updateQuestion();
    }

    private void prevQuestion(){
        this.updateQuestionIndex(-1);
        this.updateQuestion();
    }

    private void updateQuestionIndex(int value){
        mCurrentIndex = (mCurrentIndex + value) % mQuestionBank.length;
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = this.mQuestionBank[this.mCurrentIndex].isAnswerTrue();

        int messageResId;

        if(answerIsTrue == userPressedTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
