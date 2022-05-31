package com.errands.Model;

public class MyMessage {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String oppositeuser_id;
    private int type;
    private String content;
    private String time;

    public MyMessage() {
    }

    public MyMessage(String oppositeuser_id, int type, String content, String time) {
        this.oppositeuser_id = oppositeuser_id;
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public String getOppositeuser_id() {
        return oppositeuser_id;
    }

    public void setOppositeuser_id(String oppositeuser_id) {
        this.oppositeuser_id = oppositeuser_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
