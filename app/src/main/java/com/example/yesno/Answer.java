package com.example.yesno;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("forced")
    @Expose
    private boolean forced;

    @SerializedName("image")
    @Expose
    private String image;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", forced=" + forced +
                ", image='" + image + '\'' +
                '}';
    }
}