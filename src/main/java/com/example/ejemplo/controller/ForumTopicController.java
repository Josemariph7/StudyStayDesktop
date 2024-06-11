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

import com.example.ejemplo.dao.ForumTopicDAO;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;

import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de temas en el foro.
 */
public class ForumTopicController {

    private final ForumTopicDAO forumTopicDAO;

    public ForumTopicController() {
        this.forumTopicDAO = new ForumTopicDAO();
    }

    /**
     * Obtiene todos los temas del foro.
     * @return Lista de todos los temas del foro
     */
    public List<ForumTopic> getAllTopics() {
        return forumTopicDAO.getAll();
    }

    /**
     * Crea un nuevo tema en el foro.
     * @param topic El tema a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean createTopic(ForumTopic topic) {
        return forumTopicDAO.create(topic);
    }

    /**
     * Elimina un tema del foro por su ID.
     * @param topicId ID del tema a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteTopic(Long topicId) {
        return forumTopicDAO.delete(topicId);
    }

    /**
     * Actualiza un tema existente en el foro.
     * @param topic El tema a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean updateTopic(ForumTopic topic) {
        return forumTopicDAO.update(topic);
    }

    public ForumTopic getTopic(Long topicId) {
        return forumTopicDAO.getById(topicId);
    }

    public List<ForumComment> getComments(Long topicId) {
        return forumTopicDAO.getComments(topicId);
    }
}
