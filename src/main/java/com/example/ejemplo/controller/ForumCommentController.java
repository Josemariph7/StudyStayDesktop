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
