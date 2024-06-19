package com.teste.itau.service;

import com.teste.itau.model.User;
import com.teste.itau.repository.UserRepository;
import com.teste.itau.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John Doe", "john.doe@example.com", "Description 1"));
        users.add(new User(2L, "Jane Doe", "jane.doe@example.com", "Description 2"));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers_Exception() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database is down"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getAllUsers();
        });

        assertEquals("Database is down", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_UserExists() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@example.com", "Description 1");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.getUserById(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_Exception() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database is down"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("Database is down", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        User user = new User(null, "John Doe", "john.doe@example.com", "Description 1");
        User savedUser = new User(1L, "John Doe", "john.doe@example.com", "Description 1");

        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateUser_Exception() {
        User user = new User(null, "John Doe", "john.doe@example.com", "Description 1");

        when(userRepository.save(user)).thenThrow(new RuntimeException("Database is down"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Database is down", exception.getMessage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser_UserExists() {
        Long userId = 1L;
        User existingUser = new User(userId, "John Doe", "john.doe@example.com", "Description 1");
        User userDetails = new User(null, "John Updated", null, "Updated Description");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(userId, userDetails);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("Updated Description", result.getDescription());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUser_UserDoesNotExist() {
        Long userId = 1L;
        User userDetails = new User(null, "John Updated", null, "Updated Description");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.updateUser(userId, userDetails);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_Exception() {
        Long userId = 1L;
        User userDetails = new User(null, "John Updated", null, "Updated Description");

        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database is down"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, userDetails);
        });

        assertEquals("Database is down", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_Exception() {
        Long userId = 1L;

        doThrow(new RuntimeException("Database is down")).when(userRepository).deleteById(userId);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Database is down", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }
}
