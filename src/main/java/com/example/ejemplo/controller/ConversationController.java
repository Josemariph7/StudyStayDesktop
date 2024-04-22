package com.example.ejemplo.controller;

import com.example.ejemplo.dao.ConversationDAO;
import com.example.ejemplo.model.Conversation;

import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de conversaciones.
 */
public class ConversationController {

    private final ConversationDAO conversationDAO;

    public ConversationController() {
        this.conversationDAO = new ConversationDAO();
    }

    /**
     * Obtiene todas las conversaciones.
     * @return Lista de todas las conversaciones
     */
    public List<Conversation> getAllConversations() {
        return conversationDAO.getAllConversations();
    }

    /**
     * Crea una nueva conversación.
     * @param conversation La conversación a crear
     * @return true si la conversación se creó con éxito, false en caso contrario
     */
    public boolean createConversation(Conversation conversation) {
        return conversationDAO.createConversation(conversation);
    }

    /**
     * Elimina una conversación por su ID.
     * @param conversationId ID de la conversación a eliminar
     * @return true si la conversación se eliminó con éxito, false en caso contrario
     */
    public boolean deleteConversation(Long conversationId) {
        return conversationDAO.deleteConversation(conversationId);
    }

    /**
     * Actualiza una conversación.
     * @param conversation La conversación a actualizar
     * @return true si la conversación se actualizó con éxito, false en caso contrario
     */
    public boolean updateConversation(Conversation conversation) {
        return conversationDAO.updateConversation(conversation);
    }
}
