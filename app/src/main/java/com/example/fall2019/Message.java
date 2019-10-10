package com.example.fall2019;

public class Message {
    private String mess;
    private Boolean isSend;
    private long id;

    Message(Long id, String mess, Boolean isSend) {
        this.mess=mess;
        this.isSend=isSend;
        this.id=id;
    }

    public String getMessage() {
        return mess;
    }

    public boolean isSend() {
        return isSend;
    }
    @Override
    public String toString() {
        return mess;
    }
}
