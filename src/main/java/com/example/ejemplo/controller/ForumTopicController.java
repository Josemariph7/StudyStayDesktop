package com.example.ejemplo.controller;

import com.example.ejemplo.dao.ForumTopicDAO;
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
}
