package com.wenance.challenger.resources;

import java.util.ArrayList;
import java.util.List;

public class ResponseError {
    private List<String> messages = new ArrayList<>();

    public ResponseError() {
    }

    public ResponseError(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
