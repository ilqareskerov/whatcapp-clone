package com.company.whatcapp.request;

public class SingleChatRequest {
    private long userId;

    public SingleChatRequest() {
    }

    public SingleChatRequest(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
