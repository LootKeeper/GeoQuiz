package com.example.leschuk.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER = "com.example.geoquiz.answer_is_true";

    private boolean mAnswerIsTrue;

    private Button mShowAnswerBtn;
    private TextView mAnsewrTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        this.mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER, false);

        this.mAnsewrTextView = findViewById(R.id.answer_text_view);

        this.mShowAnswerBtn = findViewById(R.id.show_answer_btn);
        this.mShowAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnsewrTextView.setText(R.string.true_btn);
                }else{
                    mAnsewrTextView.setText(R.string.false_btn);
                }
            }
        });
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, answerIsTrue);
        return intent;
    }
}
