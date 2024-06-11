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
