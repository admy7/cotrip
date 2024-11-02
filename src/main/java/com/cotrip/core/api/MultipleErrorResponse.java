package com.cotrip.core.api;

import java.util.List;

public class MultipleErrorResponse {
    private int status;
    private List<String> messages;

    public MultipleErrorResponse(int status, List<String> messages) {
        this.status = status;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }
}
