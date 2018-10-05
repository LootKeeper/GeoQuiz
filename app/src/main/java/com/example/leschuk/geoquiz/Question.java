package com.example.leschuk.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public boolean isCorrect() {
        return mIsCorrect;
    }

    public void setCorrect(boolean correct) {
        mIsCorrect = correct;
    }

    private boolean mIsCorrect;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue){
        this.mAnswerTrue = answerTrue;
        this.mTextResId = textResId;
    }
}
