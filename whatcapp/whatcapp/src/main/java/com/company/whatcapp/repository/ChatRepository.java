package com.company.whatcapp.repository;

import com.company.whatcapp.modal.Chat;
import com.company.whatcapp.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    @Query("SELECT c FROM Chat c join c.users u WHERE  u.id = :userId")
    public List<Chat> findChatByUserId(@Param("userId") Long userId);
    @Query("SELECT c FROM Chat c WHERE c.isGroup = false and :user member of c.users and :reqUser member of c.users")
    public Chat findSingleChatByUserIds(@Param("user") User user,@Param("reqUser") User reqUser);
}
