package com.example.leschuk.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "com.example.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz_answer_shown";

    private static final String ANSWER_SHOWN_STATE = "IsAnswerShown";
    private boolean mAnswerIsTrue;
    private boolean mAnswerShownResult;

    private Button mShowAnswerBtn;
    private TextView mAnsewrTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null){
            this.mAnswerShownResult = savedInstanceState.getBoolean(this.ANSWER_SHOWN_STATE, false);
        }

        this.mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER, false);

        this.mAnsewrTextView = findViewById(R.id.answer_text_view);

        this.mShowAnswerBtn = findViewById(R.id.show_answer_btn);
        this.mShowAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnsewrTextView.setText(R.string.true_btn);
                } else {
                    mAnsewrTextView.setText(R.string.false_btn);
                }
                mAnswerShownResult = true;
                setAnswerShownResult(mAnswerShownResult);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    int cx = mShowAnswerBtn.getWidth() / 2;

                    int cy = mShowAnswerBtn.getHeight() / 2;
                    float radius = mShowAnswerBtn.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerBtn, cx, cy, radius, 0);

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerBtn.setVisibility(View.INVISIBLE);
                        }
                    });

                    anim.start();
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(this.ANSWER_SHOWN_STATE, mAnswerShownResult);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(this.EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent intent){
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
