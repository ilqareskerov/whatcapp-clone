package com.company.whatcapp.request;

public class SendMessageRequest {
    private Long userId;
    private Long chatId;
    private String content;

    public SendMessageRequest() {
    }

    public SendMessageRequest(Long userId, Long chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
