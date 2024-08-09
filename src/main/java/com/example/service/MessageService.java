package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    
    @Autowired
    private AccountRepository accountRepository;

    public Message postMessage(Message message) {

        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return null;// Return null if validation fails
        }
        return messageRepository.save(message);// Save and return the new message
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

  
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

 
    public int deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

  
    public int updateMessage(Integer messageId, String messageText) {

        if (messageText.isBlank() || messageText.length() > 255) {
            return 0;// Return 0 if validation fails
        }
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

  
    public List<Message> getMessagesByUserId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public Message createMessage(Message message) throws IllegalArgumentException {

       
        if (message.getMessageText() == null || message.getMessageText().isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be blank");
        }

        
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text must be under 255 characters");
        }

      
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User not found");
        }

        
        return messageRepository.save(message);
    }
    
}