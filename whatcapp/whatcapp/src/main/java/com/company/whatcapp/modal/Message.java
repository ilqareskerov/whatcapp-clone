package com.company.whatcapp.modal;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    private User sender;
    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;

    public Message() {
    }

    public Message(Long id, String content, LocalDateTime timestamp, User sender, Chat chat) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
