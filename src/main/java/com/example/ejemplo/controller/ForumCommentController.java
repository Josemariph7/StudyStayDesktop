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

import com.example.ejemplo.dao.ForumCommentDAO;
import com.example.ejemplo.model.ForumComment;

import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de comentarios en el foro.
 */
public class ForumCommentController {

    private final ForumCommentDAO forumCommentDAO;

    public ForumCommentController() {
        this.forumCommentDAO = new ForumCommentDAO();
    }

    /**
     * Obtiene todos los comentarios de un tema específico del foro.
     * @param topicId del tema del foro
     * @return Lista de comentarios del tema del foro
     */
    public List<ForumComment> getCommentsByTopic(Long topicId) {
        return forumCommentDAO.getCommentsByTopic(topicId);
    }

    /**
     * Crea un nuevo comentario en el foro.
     * @param comment El comentario a crear
     * @return true si se creó correctamente, false en caso contrario
     */
    public boolean createComment(ForumComment comment) {
        return forumCommentDAO.create(comment);
    }

    /**
     * Elimina un comentario del foro por su ID.
     * @param commentId ID del comentario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteComment(Long commentId) {
        return forumCommentDAO.delete(commentId);
    }

    /**
     * Actualiza un comentario existente en el foro.
     * @param comment El comentario a actualizar
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean updateComment(ForumComment comment) {
        return forumCommentDAO.update(comment);
    }

    public List<ForumComment> getAllComments() {
        return forumCommentDAO.getAll();
    }
}
