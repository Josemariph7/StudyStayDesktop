package com.example.ejemplo.test;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.Message;
import org.junit.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ConversationTest {

    private Conversation conversation;
    private Conversation anotherConversation;

    @Before
    public void setUp() {
        conversation = new Conversation(1L, 1L, 2L, Arrays.asList(new Message(1L, 1L, 1L, 2L, "Hello", null)));
        anotherConversation = new Conversation(2L, 1L, 3L, Arrays.asList(new Message(2L, 2L, 1L, 3L, "Hi", null)));
    }

    @Test
    public void testGetAndSetConversationId() {
        conversation.setConversationId(2L);
        assertEquals(Optional.of(2L), Optional.ofNullable(conversation.getConversationId()));
    }

    @Test
    public void testGetAndSetUser1Id() {
        conversation.setUser1Id(3L);
        assertEquals(Optional.of(3L), Optional.ofNullable(conversation.getUser1Id()));
    }

    @Test
    public void testGetAndSetUser2Id() {
        conversation.setUser2Id(4L);
        assertEquals(Optional.of(4L), Optional.ofNullable(conversation.getUser2Id()));
    }

    @Test
    public void testGetAndSetMessages() {
        List<Message> messages = Arrays.asList(new Message(3L, 3L, 3L, 4L, "Hey", null));
        conversation.setMessages(messages);
        assertEquals(messages, conversation.getMessages());
    }

    @Test
    public void testEquals() {
        Conversation anotherConversationCopy = new Conversation(2L, 1L, 3L, Arrays.asList(new Message(2L, 2L, 1L, 3L, "Hi", null)));
        assertEquals(anotherConversation, anotherConversationCopy);
        assertNotEquals(conversation, anotherConversation);
    }

    @Test
    public void testHashCode() {
        Conversation anotherConversationCopy = new Conversation(2L, 1L, 3L, Arrays.asList(new Message(2L, 2L, 1L, 3L, "Hi", null)));
        assertEquals(anotherConversation.hashCode(), anotherConversationCopy.hashCode());
        assertNotEquals(conversation.hashCode(), anotherConversation.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Conversation{conversationId=2, user1Id=1, user2Id=3, messages=[Message{messageId=2, conversationId=2, senderId=1, receiverId=3, content='Hi', dateTime=null}]}";
        assertEquals(expected, anotherConversation.toString());
    }
}
