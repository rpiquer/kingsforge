package com.kingsforge.kingsforge.business.services;

import com.kingsforge.kingsforge.exceptions.UserException;

import jakarta.servlet.http.HttpSession;

public interface CustomerService {

    public int login(String username, String password, HttpSession session) throws UserException;
    public int register(String username, String password, String email, HttpSession session) throws UserException;

}
