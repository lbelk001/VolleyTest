package edu.mssu.cis385.volleytest.model;

public class subject {
    private String subject;
    private long updateTime;

    public String getSubject(){
        return subject;
    }

    public long getUpdateTime(){
        return updateTime;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setUpdateTime(long updateTime){
        this.updateTime = updateTime;
    }
}
