/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

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
