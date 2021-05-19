package com.example.taskmasterapp;

public class Task {

    private String title;
    private String body;
    private String state;
//    = {"new", "assigned", "progress", "complete"};

    public Task() {
    }

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static void main(String[] args) {
//        Task task = new Task("ss","ss","sa");
    }

}
