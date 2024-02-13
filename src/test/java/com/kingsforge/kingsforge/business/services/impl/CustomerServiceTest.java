package com.kingsforge.kingsforge.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kingsforge.kingsforge.exceptions.UserException;
import com.kingsforge.kingsforge.persistance.CheckingRepository;
import com.kingsforge.kingsforge.persistance.CustomerRepository;

import jakarta.servlet.http.HttpSession;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;
    @Mock
    private CheckingRepository security;

    @InjectMocks
    private CustomerServiceImpl service;

    HttpSession session;

    @BeforeEach
    void setup(){
        this.session = mock(HttpSession.class);
    }


    @DisplayName("Test login(username, password, session) allready logged")
    @Test
    public void givenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(true);

        assertThrows(
            UserException.class,
            () -> service.login("username","password", session),
            "Expected UserException exception, but it was not thrown."
        );
        
    }

    @DisplayName("Test login(username, password, session) username not found")
    @Test
    public void givenNotRegisteredUsername_ShouldThrow() throws RuntimeException {

        session.setAttribute("user_id", 5);

        when(repository.existUsername("grfgf")).thenReturn(false);

        assertThrows(
            UserException.class,
            () -> service.login("grfgf","password", session),
            "Expected UserException exception, but it was not thrown."
        );
    }

    @DisplayName("Test login(username, password, session) incorrect password")
    @Test
    public void givenNotCorrectPassword_ShouldThrow() throws RuntimeException {

        session.setAttribute("user_id", 5);

        when(repository.existUsername("grfgf")).thenReturn(true);

        when(security.verifyPassword("grfgf", "grfgf")).thenThrow(
            UserException.class);

        assertThrows(
            UserException.class,
            () -> service.login("grfgf","grfgf", session),
            "Expected RuntimeException exception, but it was not thrown."
        );
    }

    @DisplayName("Test login(username, password, session) working")
    @Test
    public void givenEverythingWorkingShouldReturnId() throws RuntimeException {

        session.setAttribute("user_id", 5);

        when(repository.existUsername("grfgf")).thenReturn(true);

        when(security.verifyPassword("grfgf", "grfgf")).thenReturn(
            true);

        when(repository.getUser_id("grfgf")).thenReturn(5);

        int actual = service.login("grfgf", "grfgf", session);

        assertEquals(actual, 5);
    }

    @DisplayName("Test register(username, password, email, session) allready logged")
    @Test
    public void registerGivenNullSession_ShouldThrow() throws RuntimeException {

        when(security.verifyLogged(session)).thenReturn(true);

        assertThrows(
            UserException.class,
            () -> service.register("username","password", "email", session),
            "Expected UserException exception, but it was not thrown."
        );
        
    }

    @DisplayName("Test register(username, password, session) username found")
    @Test
    public void registerGivenRegisteredUsername_ShouldThrow() throws RuntimeException {

        session.setAttribute("user_id", 5);

        when(repository.existUsername("grfgf")).thenReturn(true);

        assertThrows(
            UserException.class,
            () -> service.register("grfgf","password", "email", session),
            "Expected UserException exception, but it was not thrown."
        );
    }

    @DisplayName("Test register(username, password, session) email found")
    @Test
    public void registerGivenRegisteredEmail_ShouldThrow() throws RuntimeException {

        session.setAttribute("user_id", 5);

        when(repository.existEmail("grfgf")).thenReturn(true);

        assertThrows(
            UserException.class,
            () -> service.register("grfgf","password", "grfgf", session),
            "Expected UserException exception, but it was not thrown."
        );
    }

    @DisplayName("Test register(username, password, session) all working")
    @Test
    public void registerGivenEverythingWorking_ShouldReturnId() throws RuntimeException {

        int expected = 5;
        String username = "grfgf";
        String email = "email";
        String passwordImput = "normal";
        String encryptedPassword = "encrypted";

        session.setAttribute("user_id", expected);

        when(repository.existEmail(email)).thenReturn(false);

        when(repository.existUsername(username)).thenReturn(false);

        when(repository.getUser_id(username)).thenReturn(expected);

        when(security.encryptPassword(passwordImput)).thenReturn(encryptedPassword);

        int actual = service.register(username, passwordImput, email, session);

        assertAll(
            () -> verify(repository).addUser(username, encryptedPassword, email)
        );

        assertEquals(actual, expected);
    }
    
}