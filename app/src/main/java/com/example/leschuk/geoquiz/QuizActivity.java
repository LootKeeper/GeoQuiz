package com.example.leschuk.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWER_WAS_CHEATED = "answerWasCheated";
    private static final Integer REQUEST_CODE_CHEAT = 0;

    private Button mTrueBtn;
    private Button mFalseBtn;

    private ImageButton mPrevBtn;
    private ImageButton mNextBtn;

    private Button mChectBtn;

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
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(this.KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(this.KEY_ANSWER_WAS_CHEATED, false);
        }

        this.mChectBtn = findViewById(R.id.cheat_btn);
        this.mChectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(this.TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(this.TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(this.TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(this.TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanseState){
        super.onSaveInstanceState(savedInstanseState);
        Log.i(this.TAG, "onSaveInstanceState(Bundle) called");
        savedInstanseState.putInt(this.KEY_INDEX, this.mCurrentIndex);
        savedInstanseState.putBoolean(this.KEY_ANSWER_WAS_CHEATED, this.mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == this.REQUEST_CODE_CHEAT &&
                resultCode == RESULT_OK && data != null){
            this.mIsCheater = CheatActivity.wasAnswerShown(data);
            this.mQuestionBank[this.mCurrentIndex].setWasCheated(this.mIsCheater);
        }
    }

    private void nextQuestion(){
        if(this.updateQuestionIndex(1)) {
            this.updateQuestion();
            this.enableAnswerBtn();
        }
    }

    private void prevQuestion(){

        if(this.updateQuestionIndex(-1)) {
            this.updateQuestion();
            this.enableAnswerBtn();
        }
    }

    private void disableAnswerBtn(){
        this.mTrueBtn.setEnabled(false);
        this.mFalseBtn.setEnabled(false);
    }

    private void enableAnswerBtn(){
        this.mTrueBtn.setEnabled(true);
        this.mFalseBtn.setEnabled(true);
    }

    private boolean updateQuestionIndex(int value){
        boolean result = true;
        this.mIsCheater = false;

        if(mCurrentIndex >= (mQuestionBank.length-1)) {
            endQuiz();
        }

        mCurrentIndex = (mCurrentIndex + value) % mQuestionBank.length;

        return  result;
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = this.mQuestionBank[this.mCurrentIndex].isAnswerTrue();

        int messageResId;
        if (answerIsTrue == userPressedTrue) {
            messageResId = R.string.correct_toast;
            this.mQuestionBank[this.mCurrentIndex].setCorrect(true);
        } else {
            messageResId = R.string.incorrect_toast;
            this.mQuestionBank[this.mCurrentIndex].setCorrect(false);
        }

        if(mIsCheater)
        {
            messageResId = R.string.judgment_toast;
        }

        this.disableAnswerBtn();

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void endQuiz(){
        int correct = 0;

        for(Question q : this.mQuestionBank){
            if(q.isCorrect()) correct++;
        }
        String tempMsg = getString(R.string.result);
        String msg = String.format(tempMsg, correct, this.mQuestionBank.length);


        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
