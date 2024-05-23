package com.example.ejemplo.test;

import com.example.ejemplo.model.Message;
import org.junit.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;
    private Message anotherMessage;

    @Before
    public void setUp() {
        message = new Message();
        anotherMessage = new Message(1L, 1L, 2L, 3L, "Hello", LocalDateTime.of(2023, 5, 23, 12, 30));
    }

    @Test
    public void testGetAndSetMessageId() {
        message.setMessageId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(message.getMessageId()));
    }

    @Test
    public void testGetAndSetConversationId() {
        message.setConversationId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(message.getConversationId()));
    }

    @Test
    public void testGetAndSetSenderId() {
        message.setSenderId(2L);
        assertEquals(Optional.of(2L), Optional.ofNullable(message.getSenderId()));
    }

    @Test
    public void testGetAndSetReceiverId() {
        message.setReceiverId(3L);
        assertEquals(Optional.of(3L), Optional.ofNullable(message.getReceiverId()));
    }

    @Test
    public void testGetAndSetContent() {
        message.setContent("Hello");
        assertEquals("Hello", message.getContent());
    }

    @Test
    public void testGetAndSetDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 23, 12, 30);
        message.setDateTime(dateTime);
        assertEquals(dateTime, message.getDateTime());
    }

    @Test
    public void testEquals() {
        Message anotherMessageCopy = new Message(1L, 1L, 2L, 3L, "Hello", LocalDateTime.of(2023, 5, 23, 12, 30));
        assertEquals(anotherMessage, anotherMessageCopy);
        assertNotEquals(message, anotherMessage);
    }

    @Test
    public void testHashCode() {
        Message anotherMessageCopy = new Message(1L, 1L, 2L, 3L, "Hello", LocalDateTime.of(2023, 5, 23, 12, 30));
        assertEquals(anotherMessage.hashCode(), anotherMessageCopy.hashCode());
        assertNotEquals(message.hashCode(), anotherMessage.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Message{messageId=1, conversationId=1, senderId=2, receiverId=3, content='Hello', dateTime=2023-05-23T12:30}";
        assertEquals(expected, anotherMessage.toString());
    }
}
