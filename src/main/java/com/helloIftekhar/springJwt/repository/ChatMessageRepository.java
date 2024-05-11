package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}