package com.company.whatcapp.repository;

import com.company.whatcapp.modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m join m.chat c WHERE c.id = :chatId")
    public List<Message> findByChatId(@Param("chatId") Long chatId);
}
