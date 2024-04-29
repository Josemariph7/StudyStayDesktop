package com.example.ejemplo.controller;

import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.User;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de usuarios.
 */
public class UserController implements CRUD<User> {

    // Instancia del DAO para interactuar con la base de datos de usuarios
    private final UserDAO userDAO = new UserDAO();

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de usuarios
     */
    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    @Override
    public User getById(Long id) {
        return UserDAO.getById(id);
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * @param user Usuario a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    @Override
    public boolean create(User user) {
        return userDAO.create(user);
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * @param user Usuario a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    @Override
    public boolean update(User user) {
        return userDAO.update(user);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    @Override
    public boolean delete(Long id) {
        return userDAO.delete(id);
    }

    /**
     * Encuentra un usuario por su dirección de correo electrónico.
     * @param email Dirección de correo electrónico del usuario
     * @return Usuario encontrado
     */
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    /**
     * Autentica un usuario utilizando su dirección de correo electrónico y contraseña.
     * @param email Dirección de correo electrónico del usuario
     * @param password Contraseña del usuario
     * @return true si las credenciales son válidas, false de lo contrario
     */
    public boolean authenticate(String email, String password) {
        return userDAO.authenticate(email, password);
    }
}
