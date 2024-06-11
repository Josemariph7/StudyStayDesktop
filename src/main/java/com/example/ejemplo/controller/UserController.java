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
