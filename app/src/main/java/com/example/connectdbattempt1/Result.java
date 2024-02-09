package com.example.connectdbattempt1;

import java.util.Date;

public class Result {
    private int id;
    private int result;
    private String lowFreq;
    private String highFreq;
    private Date date;
    public Result() {}

    public Result(int result, String lowFreq, String highFreq) {
        this.result = result;
        this.lowFreq = lowFreq;
        this.highFreq = highFreq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getLowFreq() {
        return lowFreq;
    }

    public void setLowFreq(String lowFreq) {
        this.lowFreq = lowFreq;
    }

    public String getHighFreq() {
        return highFreq;
    }

    public void setHighFreq(String highFreq) {
        this.highFreq = highFreq;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Adapted from ChatGPT https://chat.openai.com/share/f9054797-fd41-467a-b757-3486719d65ab
    //Can't remember what site or forum recommended this. Not my original idea.
    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", result=" + result +
                ", lowFreq='" + lowFreq + '\'' +
                ", highFreq='" + highFreq + '\'' +
                ", date=" + date +
                '}';
    }
}

