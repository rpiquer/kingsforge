package com.kingsforge.kingsforge.persistance;

import com.kingsforge.kingsforge.exceptions.UserException;

public interface CustomerRepository {
    public int getUser_id(String username) throws UserException;
    public boolean existUsername(String username);
    public void addUser(String username, String password, String email);
    public boolean existEmail(String email);
}
