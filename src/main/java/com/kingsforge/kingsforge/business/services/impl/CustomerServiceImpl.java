package com.kingsforge.kingsforge.business.services.impl;

import com.kingsforge.kingsforge.business.services.CustomerService;
import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CustomerRepository;
import com.kingsforge.kingsforge.persistance.CheckingRepository;
import com.kingsforge.kingsforge.persistance.impl.CustomerRepositoryImpl;
import com.kingsforge.kingsforge.persistance.impl.CheckingRepositoryImpl;

import jakarta.servlet.http.HttpSession;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository repository = new CustomerRepositoryImpl();
    private CheckingRepository security = new CheckingRepositoryImpl();

    public int login(String username, String password, HttpSession session) throws UserException {

        if (security.verifyLogged(session)) {
            throw new UserException("Ya estas loggeado");
        }
        if (!repository.existUsername(username)) {
            throw new UserException("El nombre de usuario no existe");
        }
        security.verifyPassword(password, username);
        return repository.getUser_id(username);
    }

    public int register(String username, String password, String email, HttpSession session) throws UserException {

        if (security.verifyLogged(session)) {
            throw new UserException("Ya estas loggeado");
        }
        if (repository.existEmail(email)) {
            throw new UserException("El email ya existe");
        }
        if (repository.existUsername(username)) {
            throw new UserException("El nombre de usuario ya existe");
        }
        repository.addUser(username, security.encryptPassword(password), email);
        return repository.getUser_id(username);
    }
}
