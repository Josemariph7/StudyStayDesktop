package com.example.ejemplo.controller;

import com.example.ejemplo.dao.MessageDAO;
import com.example.ejemplo.model.Message;

import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de mensajes en la base de datos.
 */
public class MessageController {

    private final MessageDAO messageDAO;

    public MessageController() {
        this.messageDAO = new MessageDAO();
    }

    /**
     * Obtiene todos los mensajes de una conversación.
     *
     * @param conversationId ID de la conversación
     * @return Lista de mensajes de la conversación
     */
    public List<Message> getMessagesByConversation(Long conversationId) {
        return messageDAO.getMessagesByConversation(conversationId);
    }

    /**
     * Crea un nuevo mensaje en la base de datos.
     *
     * @param message El mensaje a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    /**
     * Elimina un mensaje de la base de datos.
     *
     * @param messageId ID del mensaje a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteMessage(Long messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    /**
     * Modifica un mensaje en la base de datos.
     *
     * @param message El mensaje modificado
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modifyMessage(Message message) {
        return messageDAO.modifyMessage(message);
    }

    /**
     * Obtiene todos los mensajes de la base de datos.
     *
     * @return Lista de todos los mensajes
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
